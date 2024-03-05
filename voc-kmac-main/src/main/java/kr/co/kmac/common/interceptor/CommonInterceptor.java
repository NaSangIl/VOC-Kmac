package kr.co.kmac.common.interceptor;

import kr.co.kmac.common.constants.CommonConstants;
import kr.co.kmac.common.util.LoginInfoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 공통 행위 처리를 위한 인터셉터 클래스
 * 
 * @ClassName  CommonInterceptor.java
 * @Description 인터셉터
 * @author mjkim
 * @since 2023. 9. 18.
 *
 */
@Component
public class CommonInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(CommonInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        
        String requestUri = request.getRequestURI();
        
        // 로그인 상태가 아닌 경우
        if(!LoginInfoUtil.isLogin(request)) {
            response.sendRedirect(CommonConstants.URL_LOGIN);
            return false;
        }
        
        return super.preHandle(request, response, handler);
    }

}