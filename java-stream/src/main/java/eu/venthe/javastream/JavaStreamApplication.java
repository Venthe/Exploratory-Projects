package eu.venthe.javastream;

import eu.venthe.javastream.stream.types.Either;
import eu.venthe.javastream.stream.types.Pair;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.text.MessageFormat;
import java.util.stream.IntStream;

@SpringBootApplication
@Slf4j
public class JavaStreamApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaStreamApplication.class, args);
    }

    @Bean
    public void testEither() {
        log.debug("testEither");
        log.debug(MessageFormat.format("{0}", Either.right("Right")));
        log.debug(MessageFormat.format("{0}", Either.left("Left")));
    }

    @Bean
    public void testPair() {
        log.debug("testPair");
        log.debug(MessageFormat.format("{0}", Pair.of(null, null)));
        log.debug(MessageFormat.format("{0}", Pair.of(null, "Right")));
        log.debug(MessageFormat.format("{0}", Pair.of("Left", "Right")));
        log.debug(MessageFormat.format("{0}", Pair.of("Left", null)));
    }

    @Bean
    public void testStream() {
        log.debug("testStream");
        IntStream.range(0, 100)
                .boxed()
                .map(Either.liftWithValue(JavaStreamApplication::testExceptionFunction))
                .peek(JavaStreamApplication::logEither)
                .forEach(Either.consume(
                        JavaStreamApplication::logException,
                        JavaStreamApplication::useCorrectValue
                        )
                );
    }

    private static void logEither(Either<Pair<Exception, Integer>, Integer> e) {
        log.debug(MessageFormat.format("Debug logging either: {0}", e.toString()));
    }

    private static <T> void logException(Pair<Exception, T> exception) {
        log.debug(MessageFormat.format("There has been an error with {0}", exception.getSecond()),
                exception.getFirst());
    }

    private static void useCorrectValue(Integer value) {
        log.debug(MessageFormat.format("Correct value: {0}", value));
    }

    private static Integer testExceptionFunction(Integer i) throws Exception {
        if (i % 2 == 0) {
            return i;
        }
        throw new Exception("Sample error message");
    }
}
