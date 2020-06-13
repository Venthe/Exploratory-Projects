package eu.venthe.aoptransaction;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Aspect
@Component
@Slf4j
public class LogStartStopAspect {
    @Around("@annotation(LogStartStop)")
    public Object logStartStop(ProceedingJoinPoint joinPoint) throws Throwable {
        final String methodName = joinPoint.getSignature().getName();
        final String uuid = LogExecutionTimeAspect.getInvocationId(joinPoint);

        log.info(MessageFormat.format("{0} - Starting. UUID={1}", methodName, uuid));
        Object proceed = joinPoint.proceed();
        log.info(MessageFormat.format("{0} - Ending. UUID={1}", methodName, uuid));

        return proceed;
    }
}
