package com.demo.skiapp.config.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Before(value = "@within(com.demo.skiapp.config.aop.LogExecution)")
    public void before(JoinPoint joinPoint) {
        log.info(String.format("Called method: %s() with args: %s", joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs())));
    }
}
