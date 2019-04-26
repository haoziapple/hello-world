package com.github.haozi.learnspring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author wanghao
 * @Description
 * @date 2019-04-25 20:20
 */
@Aspect
@Component
public class DemoAspect {

    @Around("@annotation(demo)")
    public Object around(ProceedingJoinPoint joinPoint, Demo demo) throws Throwable {
        System.out.println(joinPoint.getSignature().toString());
        Object result = joinPoint.proceed();
        return result;
    }
}
