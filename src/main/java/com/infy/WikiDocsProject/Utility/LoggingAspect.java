package com.infy.WikiDocsProject.Utility;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
public class LoggingAspect {


    @AfterThrowing(pointcut = "execution(* com.infy.WikiDocsProject.Service.*Impl.*(..))", throwing = "exception")
    public void logExceptionFromService(Exception exception) throws Exception {
        log(exception);
        
    }


    private void log(Object obj) {
//        log.debug();
//        Logger logger = LogManager.getLogger(this.getClass());
//        if(exception.getMessage()!=null &&
//                (exception.getMessage().contains("Service") || exception.getMessage().contains("Validator"))){
//            logger.error(exception.getMessage());
//        }
//        else{
//            logger.error(exception.getMessage(), exception);
//        }
    }

}
