package com.emad.simplerestapp.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * write log with using aop
 */
@Aspect
@Component
public class LoggerAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static final String BEFORE_CLASS = "before %s of class %s ";
    public static final String AFTER_CLASS = "after %s of class %s ";
    public static final String START_RW_CLASS = "start of r/w from source %s of class %s ";
    public static final String END_RW_CLASS = "end of r/w from source %s of class %s ";
    public static final String SAVED_ON_DB = "%s was saved on db";

    @Before(value = "execution(* com.emad.simplerestapp.service.impl.*.*(..))")
    public void beforeServiceImplMethod(JoinPoint joinPoint) {
        String className = getClassName(joinPoint);
        logger.info(generateStr(LoggerAspect.BEFORE_CLASS, joinPoint.getSignature().getName(), className));
    }

    @After(value = "execution(* com.emad.simplerestapp.service.impl.*.*(..))")
    public void afterServiceImplMethod(JoinPoint joinPoint) {
        String className = getClassName(joinPoint);
        logger.info(generateStr(LoggerAspect.AFTER_CLASS, joinPoint.getSignature().getName(), className));
    }

    @Before(value = "execution(* com.emad.simplerestapp.service.initializer.*.*(..))")
    public void beforeWriterMethod(JoinPoint joinPoint) {
        String className = getClassName(joinPoint);
        String sourceName = getSourceName(className);
        logger.info(generateStr(LoggerAspect.START_RW_CLASS, sourceName, className));
    }

    @After(value = "execution(* com.emad.simplerestapp.service.initializer.*.*(..))")
    public void afterWriterMethod(JoinPoint joinPoint) {
        String className = getClassName(joinPoint);
        String sourceName = getSourceName(className);
        logger.info(generateStr(LoggerAspect.END_RW_CLASS, sourceName, className));
        logger.info(generateStr(sourceName));
    }

    private String generateStr(String sourceName) {
        return String.format(SAVED_ON_DB, sourceName);
    }

    private String generateStr(String endRwClass, String sourceName, String className) {
        return String.format(endRwClass, sourceName
                , getMethodName(className));
    }

    private String getMethodName(String className) {
        return className.substring(className.lastIndexOf(".") + 1);
    }

    private String getSourceName(String className) {
        return getMethodName(className).replace("Writer", "").toLowerCase() + "s";
    }

    private String getClassName(JoinPoint joinPoint) {
        return joinPoint.getTarget().getClass().getName();
    }

}
