package kr.co.kmac.coreframework.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {
	private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);
	
	/**
	 * Service 로 끝나는 클래스의 모든 메소드에 대한 pointcut
	 */
	@Pointcut("execution(* kr.co.kmac..*Service.*(..))")
	public void serviceLogPointcut() {
		// 포인트컷 설정
	}
	
	@Before("serviceLogPointcut()")
	public void beforeLogging(JoinPoint jp) {
		logger.debug("Aspect before service - {} / {}()", jp.getSignature().getDeclaringTypeName(), jp.getSignature().getName());
	}
	
	@Around("serviceLogPointcut()")
	public Object logging(ProceedingJoinPoint pjp) throws Throwable {
		logger.debug("Aspect start service - {} / {}()", pjp.getSignature().getDeclaringTypeName(), pjp.getSignature().getName());
		
		Object result = pjp.proceed();
		
		logger.debug("Aspect finished service - {} / {}()", pjp.getSignature().getDeclaringTypeName(), pjp.getSignature().getName());
		
		return result;
	}
}
