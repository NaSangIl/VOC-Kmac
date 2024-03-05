package kr.co.kmac.common.interceptor;

import kr.co.kmac.common.constants.CommonConstants;
import kr.co.kmac.common.util.LoginInfoUtil;
import kr.co.kmac.system.dto.MenuDto;
import kr.co.kmac.system.dto.UserDto;
import kr.co.kmac.system.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 권한 관련 처리를 위한 인터셉터 클래스
 *
 * @ClassName  AuthorizationInterceptor.java
 * @Description 인터셉터
 * @author mjkim
 * @since 2023. 9. 18.
 *
 */
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private MenuService service;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 로그인 상태일 때
        if(LoginInfoUtil.isLogin(request)) {
            // 해당 uri에 로그인 사용자의 접근 권한이 없을 때
            if( !isAuth(request) ) {
                response.sendRedirect("/common/authError");
                return false;
            }
        } else {
            response.sendRedirect(CommonConstants.URL_LOGIN);
            return false;
        }

        return super.preHandle(request, response, handler);
    }

    /**
     * 현재 uri가 로그인 사용자가 접근할 수 있는 권한이 있는지 판단
     * @param request
     * @return
     */
    private boolean isAuth(HttpServletRequest request) {

        String requestUri = request.getRequestURI();
        System.out.println("requestUri==>"+requestUri);
        //String uriSubPath = getUriSubPath(requestUri);

        MenuDto.AuthRequest param = MenuDto.AuthRequest.builder().build();
        UserDto.LoginInfo loginInfo = LoginInfoUtil.getLoginUserInfo(request);
        param.setCompanyCd(loginInfo.getCompanyCd());
        param.setUserAuthCodeList(loginInfo.getUserAuthCodeList());
        param.setMenuUrl(requestUri);
        List<MenuDto.Info> menuList = service.getAuthMenuList(param);

        // 권한이 있을 때
        if(menuList != null && menuList.size() >0) {
            return true;
        }

        return false;
    }
}
