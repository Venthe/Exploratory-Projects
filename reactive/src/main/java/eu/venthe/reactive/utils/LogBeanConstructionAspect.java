package eu.venthe.reactive.utils;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Aspect
@Slf4j
@Component
public class LogBeanConstructionAspect {
    @Around("@annotation(LogBeanConstruction)")
    public Object logConstruction(ProceedingJoinPoint joinPoint) throws Throwable {
        final Object proceed = joinPoint.proceed();
        log.info(MessageFormat.format("Bean {0} created.", joinPoint.getSignature().getName()));
        return proceed;
    }
}
