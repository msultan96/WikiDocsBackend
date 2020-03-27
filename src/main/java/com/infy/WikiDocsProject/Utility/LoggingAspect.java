package com.infy.WikiDocsProject.Utility;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;


@Component
@Aspect
public class LoggingAspect {

    static Logger logger = LogManager.getLogger(LoggingAspect.class.getName());

//
//    @Around("execution(* com.infy.WikiDocsProject.Service.*.*(..))")
//    public void logAroundService(ProceedingJoinPoint joinPoint) throws Throwable{
//        //before
//        logger.info("Before Method: " + joinPoint.getSignature().getName());
//        try {
//            joinPoint.proceed();
//        } finally {
//            logger.info("After Execution of: " + joinPoint.getSignature().getName());
//        }
//        logger.info("After Method: " + joinPoint.getSignature().getName());
//    }
//
//    @Around("execution(* com.infy.WikiDocsProject.Configuration.*.*(..))")
//    public void logAroundConfig(ProceedingJoinPoint joinPoint) throws Throwable{
//        //before
//        logger.info("Before Method: " + joinPoint.getSignature().getName());
//        try {
//            joinPoint.proceed();
//        } finally {
//            logger.info("After Execution of: " + joinPoint.getSignature().getName());
//        }
//        logger.info("After Method: " + joinPoint.getSignature().getName());
//    }
//
//    @Around("execution(* com.infy.WikiDocsProject.API.*.*(..))")
//    public void logAroundAPI(ProceedingJoinPoint joinPoint) throws Throwable {
//        //before
//        logger.info("Before Method: " + joinPoint.getSignature().getName());
//        try {
//            joinPoint.proceed();
//        } finally {
//            logger.info("After Execution of: " + joinPoint.getSignature().getName());
//        }
//        logger.info("After Method: " + joinPoint.getSignature().getName());
//    }

}
