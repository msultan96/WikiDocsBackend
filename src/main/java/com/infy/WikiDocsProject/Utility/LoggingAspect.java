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


/**
 * Spring AOP LoggingAspect Class using Log4J
 */
@Aspect
@Component
public class LoggingAspect {

    static Logger log = LogManager.getLogger(LoggingAspect.class);

    /**
     * Pointcut value for all the API classes
     */
    @Pointcut(value="execution(* com.infy.WikiDocsProject.API.*.*(..))")
    public void apiPointcut(){

    }

    /**
     * Pointcut value for all the Service Implementation classes
     */
    @Pointcut(value="execution(* com.infy.WikiDocsProject.Service.*.*(..))")
    public void servicePointcut(){

    }

    /**
     * @Around advice used for generic logging around all pointcuts
     * @param proceedingJoinPoint
     * @return Object
     * @throws Throwable
     */
    @Around("(apiPointcut() || servicePointcut())")
    public Object applicationLogger(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        ObjectMapper objectMapper = new ObjectMapper();

        String className = proceedingJoinPoint.getTarget().toString();
        String methodName = proceedingJoinPoint.getSignature().getName();
        Object[] argsObject = proceedingJoinPoint.getArgs();

        //masks sensitive login/registration info
        handleRawPassword(methodName, argsObject);

        //logs generic info before pointcut/method execution
        log.info("\n" + className + "\n\t" + methodName +"\n(" + objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(argsObject) + ")" +
                "\n\t" + "invoked");

        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        //executes the method
        Object response = proceedingJoinPoint.proceed();
        stopWatch.stop();

        //logs generic info, including the report time, after the poincut/method execution
        log.info("\n" + className + "\n\t" + methodName +"\n(" + objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(argsObject) + ")" +
                "\n\t" + "took " + stopWatch.getTotalTimeMillis() + " ms" +
                "\n\t" + "response: \n" + objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(response));
        return response;
    }

    /**
     * handleRawPassword used to mask sensitive login/registration info
     * @param methodName
     * @param argsObject
     * @throws CloneNotSupportedException
     */
    private void handleRawPassword(String methodName, Object[] argsObject) throws CloneNotSupportedException {
        if(methodName.equals("loginUser")){
            //clones argsObject so that actual value of argsObject is not changed
            AuthBody authBody = ((AuthBody)argsObject[0]).clone();
            authBody.setPassword("**********");
            argsObject[0] = authBody;
        }

        if(methodName.equals("register")){
            //clones argsObject so that actual value of argsObject is not changed
            User user = ((User)argsObject[0]).clone();
            user.setPassword("**********");
            argsObject[0] = user;
        }
    }

    /**
     * @AfterThrowing advice used to log exceptions that occured during the execution of Service classes
     * @param exception
     */
    @AfterThrowing(pointcut="servicePointcut()", throwing="exception")
    public void logExceptionFromService(Exception exception){
        exceptionLogger(exception);
    }

    /**
     * @AfterThrowing advice used to log exceptions that occured during the execution of API classes
     * @param exception
     */
    @AfterThrowing(pointcut="apiPointcut()", throwing="exception")
    public void logExceptionFromAPI(Exception exception){
        exceptionLogger(exception);
    }

    /**
     * exceptionLogger, logs any exceptions that occurred during the execution of the application
     * @param exception
     */
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
