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

    @Pointcut("execution(public * com.yoyomo.infra.aws.usecase.DistributeUsecase.createRecord(..))")
    private void logForDistributeMethod() {
    }

    @Around("logForDistributeMethod()")
    public Object logDistributeUsecaseExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        Object[] methodArgs = joinPoint.getArgs();

        String subDomain = methodArgs.length > 0 ? methodArgs[0].toString() : "N/A";

        log.info("🚀 Starting execution of {} with subDomain: {}", methodName, subDomain);

        Object result;
        try {
            result = joinPoint.proceed();
            log.info("✅ Finished execution of {} with subDomain: {}", methodName, subDomain);
        } catch (Exception e) {
            log.error("❌ Error executing {} with subDomain: {}: {}", methodName, subDomain, e.getMessage(), e);
            throw e;
        }

        return result;
    }

    @Pointcut("execution(* com.yoyomo.infra.aws.s3.service.S3Service.upload(..)) && args(subDomain)")
    private void logForS3Upload(String subDomain) {
    }

    @Around("logForS3Upload(subDomain)")
    public Object logS3UploadExecution(ProceedingJoinPoint joinPoint, String subDomain) throws Throwable {
        log.info("📂 Uploading to S3 for subdomain: {}", subDomain);

        try {
            Object result = joinPoint.proceed();
            log.info("✅ Successfully uploaded to S3 for subdomain: {}", subDomain);
            return result;
        } catch (Exception e) {
            log.error("❌ Failed to upload for subdomain: {} - Error: {}", subDomain, e.getMessage(), e);
            throw e;
        }
    }
}
