package com.infy.WikiDocsProject.Utility;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.text.DateFormat;


@Component
@Aspect
public class LoggingAspect {

    static Logger logger = LogManager.getLogger(LoggingAspect.class.getName());


//    @Around("execution(* com.infy.WikiDocsProject.Service.*.*(..))")
//    public void logAroundService(ProceedingJoinPoint joinPoint) throws Throwable {
//        //before
//        logger.info("Before Method: " + joinPoint.getSignature().getName());
//        long start = System.currentTimeMillis();
//        Object value = null;
//        try {
//            value = joinPoint.proceed();
//        } finally {
//
//        }
//        long total = System.currentTimeMillis() - start;
//        //after
//        logger.info("After Method: " + joinPoint.getSignature().getName());
//        logger.info("Execution took " + total + " milliseconds");
//    }
//
    @Around("execution(* com.infy.WikiDocsProject.API.*.*(..))")
    public Object logAroundAPI(ProceedingJoinPoint joinPoint) throws Throwable {
        //before
        logger.info("Before Method: " + joinPoint.getSignature().getName());
        long start = System.currentTimeMillis();
        Object value = null;
        try {
            value = joinPoint.proceed();
        } finally {

        }
        long total = System.currentTimeMillis() - start;
        //after
        logger.info("After Method: " + joinPoint.getSignature().getName());
        logger.info("Total execution time: " + total + " milliseconds");
        value = "Value returned from advice : " + value; // modifying the return value from Joinpoint
        return (value); // returning the modified value to client
    }

    @AfterThrowing(pointcut="execution(* com.infy.WikiDocsProject.Service.*.*(..))", throwing = "ex")
    public void logServiceExcetions(JoinPoint joinPoint, Exception ex)
    {
        logger.error("Exception occured inside " + joinPoint.getSignature().getName());
        logger.info("Message: " + ex.getMessage());
        long time = System.currentTimeMillis();
        logger.info("Report timestamp: " + DateFormat.getDateTimeInstance().format(time));
    }

    @AfterThrowing(pointcut="execution(* com.infy.WikiDocsProject.API.*.*(..))", throwing = "ex")
    public void logAPIExcetions(JoinPoint joinPoint, Exception ex)
    {
        logger.error("Exception occured inside " + joinPoint.getSignature().getName());
        logger.error("Message: " + ex.getMessage());
        long time = System.currentTimeMillis();
        logger.error("Report timestamp: " + DateFormat.getDateTimeInstance().format(time));
    }

}
