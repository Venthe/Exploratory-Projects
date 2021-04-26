package eu.venthe.sleuth;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.sleuth.CurrentTraceContext;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.cloud.sleuth.zipkin2.RestTemplateSender;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.nio.charset.StandardCharsets.UTF_8;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles({"test", "logback"})
@ExtendWith({MockitoExtension.class, SpringExtension.class})
class ConsoleLogTest {
    @Value("${spring.application.name}")
    String applicationName;
    @Autowired
    Tracer tracer;
    @Autowired
    CurrentTraceContext currentTraceContext;
    @MockBean
    RestTemplateSender restTemplateSender;

    ByteArrayOutputStream byteArrayOutputStream;
    PrintStream originalPrintStream;
    SoftAssertions softly;

    @BeforeEach
    void beforeEach() throws UnsupportedEncodingException {
        softly = new SoftAssertions();
        originalPrintStream = System.out;
        byteArrayOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(byteArrayOutputStream, true, UTF_8.name()));
    }

    @AfterEach
    void afterEach() {
        System.setOut(originalPrintStream);
        softly.assertAll();
    }

    @SneakyThrows
    @Test
    void logDataTest() {
        // given
        final Span GrandParentSpan = tracer.spanBuilder()
                .name("GrandParentSpanAndTraceId")
                .setNoParent()
                .start();
        final Span ParentSpan = tracer.nextSpan(GrandParentSpan);
        final Span childSpan = tracer.nextSpan(ParentSpan);
        childSpan.name("Child span");

        // when
        try (var scope = currentTraceContext.newScope(childSpan.context())) {
            log.error("message");
            originalPrintStream.println(getContextMap().toString());
        }

        // then
        softly.assertThat(byteArrayOutputStream.toString()).satisfies(value -> {
            originalPrintStream.println(value);

            final Matcher matcher = match(value, consoleLogPattern());
            softly.assertThatCode(() -> LocalDate.parse(matcher.group("year"))).doesNotThrowAnyException();
            softly.assertThatCode(() -> LocalTime.parse(matcher.group("time"))).doesNotThrowAnyException();
            // TODO: Move trim to regexp matching
            softly.assertThat(matcher.group("level").trim()).isEqualTo("ERROR");
            softly.assertThat(matcher.group("applicationName").trim()).isEqualTo(applicationName);
            softly.assertThat(matcher.group("traceId").trim()).isEqualTo(GrandParentSpan.context().traceId());
            softly.assertThat(matcher.group("spanId").trim()).isEqualTo(childSpan.context().spanId());
            softly.assertThat(matcher.group("parentId").trim()).isEqualTo(childSpan.context().parentId());
            softly.assertThat(matcher.group("pid").trim()).isEqualTo(Long.toString(ProcessHandle.current().pid()));
            softly.assertThat(matcher.group("thread").trim()).isEqualTo(Thread.currentThread().getName());
            softly.assertThat(matcher.group("className").trim()).isEqualTo(getClass().getName());
            softly.assertThat(matcher.group("message").trim()).isEqualTo("message");
        });
    }

    private Map<String, String> getContextMap() {
        return MDC.getMDCAdapter().getCopyOfContextMap();
    }

    private Matcher match(String value, Pattern pattern) {
        final Matcher matcher = pattern.matcher(value);
        final boolean isMatched = matcher.find();
        if (!isMatched) {
            throw new RuntimeException("REGEXP not matched");
        }
        return matcher;
    }

    private Pattern consoleLogPattern() {
        return Pattern.compile("^(?<year>[\\d]{4}-[\\d]{2}-[\\d]{2}) (?<time>[\\d]{2}:[\\d]{2}:[\\d]{2}.[\\d]+) (?<level>[\\w]+) \\[(?<applicationName>.+?(?=,)),(?<traceId>.+?(?=,)),(?<parentId>.+?(?=,)),(?<spanId>.+?(?=]))] (?<pid>[\\d]+) --- \\[(?<thread>.+?(?=[s]*]))] (?<className>.+?(?=:)):(?<message>.*)$");
    }
}
