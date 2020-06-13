package eu.venthe.aoptransaction;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.stream.Stream;

import static java.text.MessageFormat.format;

@Aspect
@Component
@Slf4j
public class LogExecutionTimeAspect {
    @Around("@annotation(logExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint, LogExecutionTime logExecutionTime) throws Throwable {
        long start = System.currentTimeMillis();

        Object proceed = joinPoint.proceed();

        long executionTime = System.currentTimeMillis() - start;

        final String uuid = getInvocationId(joinPoint);
        final String name = getInvocationIdentifier(joinPoint, logExecutionTime);
        log.info(format("{0}: executed in {1}ms. UUID={2}", name, executionTime, uuid));

        return proceed;
    }

    static String getInvocationId(ProceedingJoinPoint joinPoint) {
        return Stream.of(joinPoint.getArgs())
                .filter(a -> a instanceof UUID)
                .map(a -> (UUID) a)
                .map(UUID::toString)
                .findFirst()
                .orElse("");
    }

    private String getInvocationIdentifier(ProceedingJoinPoint joinPoint, LogExecutionTime logExecutionTime) {
        return logExecutionTime.value().isEmpty() ? joinPoint.getSignature().getName() : logExecutionTime.value();
    }
}
