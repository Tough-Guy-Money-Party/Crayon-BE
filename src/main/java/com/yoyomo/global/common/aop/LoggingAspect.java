package com.yoyomo.global.common.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Aspect
public class LoggingAspect {
    @Pointcut("execution(public * com.yoyomo.infra.aws.usecase.DistrubuteUsecase.*(..))")
    private void distrubuteUsecaseMethods() {
    }

    @Around("distrubuteUsecaseMethods()")
    public Object logDistrubuteUsecaseExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        Object[] methodArgs = joinPoint.getArgs();

        log.info("🚀 {} Starting execution of {}", methodArgs[0].toString(), methodName);

        long startTime = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long executionTime = System.currentTimeMillis() - startTime;
        log.info("✅ {} Finished execution {} in {} ms ", result, methodName, executionTime);

        return result;
    }
}
