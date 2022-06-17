package com.emad.simplerestapp.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggerAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before(value = "execution(* com.emad.simplerestapp.service.impl.*.*(..))")
    public void beforeMethod(JoinPoint joinPoint) {
        String className = joinPoint.getTarget().getClass().getName();
        logger.info(String.format("before %s from class %s ", joinPoint.getSignature().getName(), className.substring(className.lastIndexOf(".") + 1, className.length())));
    }

    @After(value = "execution(* com.emad.simplerestapp.service.impl.*.*(..))")
    public void afterMethod(JoinPoint joinPoint) {
        String className = joinPoint.getTarget().getClass().getName();
        logger.info(String.format("after %s from class %s ", joinPoint.getSignature().getName(), className.substring(className.lastIndexOf(".") + 1, className.length())));
    }

}
