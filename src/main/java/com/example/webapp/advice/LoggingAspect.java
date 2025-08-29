package com.example.webapp.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
	
	private Integer count=0;

//	@Around("execution(* com.example.webapp.controller.TimeRecorderController.showTimeRecorder(..))")
	public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
		long startTime = System.currentTimeMillis();
		Object result = joinPoint.proceed();
		long elapsedTime = System.currentTimeMillis() - startTime;
		System.out.println(++count+"回目実行時間:"+elapsedTime+"milliseconds");
		return result;
	}

}
