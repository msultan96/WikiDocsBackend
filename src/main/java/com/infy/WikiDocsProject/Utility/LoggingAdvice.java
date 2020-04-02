package com.infy.WikiDocsProject.Utility;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infy.WikiDocsProject.Model.AuthBody;
import com.infy.WikiDocsProject.Model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class LoggingAdvice {

    static Logger log = LogManager.getLogger(LoggingAdvice.class);

    @Pointcut(value="execution(* com.infy.WikiDocsProject.API.*.*(..))")
    public void apiPointcut(){

    }

    @Pointcut(value="execution(* com.infy.WikiDocsProject.Service.*.*(..))")
    public void servicePointcut(){

    }

    @Around("(apiPointcut() || servicePointcut())")
    public Object applicationLogger(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        ObjectMapper objectMapper = new ObjectMapper();

        String className = proceedingJoinPoint.getTarget().toString();
        String methodName = proceedingJoinPoint.getSignature().getName();
        Object[] argsObject = proceedingJoinPoint.getArgs();

        handleRawPassword(methodName, argsObject);

        log.info("\n" + className + "\n\t" + methodName +"\n(" + objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(argsObject) + ")" +
                "\n\t" + "invoked");

        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object response = proceedingJoinPoint.proceed();
        stopWatch.stop();

        log.info("\n" + className + "\n\t" + methodName +"\n(" + objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(argsObject) + ")" +
                "\n\t" + "took " + stopWatch.getTotalTimeMillis() + " ms" +
                "\n\t" + "response: \n" + objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(response));
        return response;
    }

    private void handleRawPassword(String methodName, Object[] argsObject) throws CloneNotSupportedException {
        if(methodName.equals("loginUser")){
            AuthBody authBody = ((AuthBody)argsObject[0]).clone();
            authBody.setPassword("**********");
            argsObject[0] = authBody;
        }

        if(methodName.equals("register")){
            User user = ((User)argsObject[0]).clone();
            user.setPassword("**********");
            argsObject[0] = user;
        }
    }

    @AfterThrowing(pointcut="servicePointcut()", throwing="exception")
    public void logExceptionFromService(Exception exception){
        exceptionLogger(exception);
    }

    @AfterThrowing(pointcut="apiPointcut()", throwing="exception")
    public void logExceptionFromAPI(Exception exception){
        exceptionLogger(exception);
    }

    private void exceptionLogger(Exception exception){
        if(exception.getMessage()!=null &&
                (exception.getMessage().contains("Service") || exception.getMessage().contains("API"))){
            log.error("\n\t" + exception.getMessage());
        }
        else{
            log.error("\n\t" + exception.getMessage(), exception);
        }
    }
}
