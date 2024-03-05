package kr.co.kmac.coreframework.aop;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @ClassName  ExceptionLogAspect.java
 * @Description Class description
 * @author mjkim
 * @since 2021. 5. 28.
 *
 */
@Aspect
@Component
public class ExceptionLogAspect {
	
	private static final Logger logger = LoggerFactory.getLogger(ExceptionLogAspect.class);
	
	@AfterThrowing(value="execution(* kr.co.kmac..*Controller.*(..))", throwing="exception")
	public void writeFailLog(JoinPoint jp, Exception exception) {
		/*
		try {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
			logger.error("request error uri {}", request.getRequestURI());
			logger.error("request error Referer {}", request.getHeader("Referer"));
			
			Enumeration<String> params = request.getParameterNames();
			while(params.hasMoreElements()) {
				String paramName = params.nextElement();
				String paramValue = request.getParameter(paramName);
				logger.error("request param {}:{}", paramName, paramValue);
			}
			
			if(request.getContentType() != null && request.getContentType().contains("multipart/form-data")) {
				return;
			}
			
			Object[] arg = jp.getArgs();
			
			if(arg != null && arg.length > 0) {
				for(Object o:arg) {
					logger.error("request body {}", o.toString());
				}
			}
		} catch(Exception e) {
			logger.error("");
		}*/
	}
}
