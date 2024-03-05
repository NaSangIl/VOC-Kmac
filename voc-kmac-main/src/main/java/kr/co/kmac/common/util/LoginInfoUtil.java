package kr.co.kmac.common.util;

import kr.co.kmac.common.constants.CommonConstants;
import kr.co.kmac.system.dto.UserDto;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 로그인 정보를 다루는 유틸 클래스
 *
 * @ClassName LoginInfoUtil.java
 * @Description 로그인 정보를 다루는 유틸 클래스
 * @author mjkim
 * @since 2023. 9. 18.
 */
public class LoginInfoUtil {
    /**
     * 로그인 여부
     * @return
     */
    public static boolean isLogin(HttpServletRequest request) {

        boolean isLogin     = false;
        HttpSession session = request.getSession(false);

        if(ObjectUtils.isEmpty(session)) {
            isLogin = false;
        } else {
            UserDto.LoginInfo loginInfo = (UserDto.LoginInfo)session.getAttribute(CommonConstants.LOGIN_USER_INFO);
            if(ObjectUtils.isNotEmpty(loginInfo)) {
                isLogin = true;
            }
        }

        return isLogin;
    }

    /**
     * 로그인 사용자 정보 객체 리턴
     * @return loginInfo
     */
    public static UserDto.LoginInfo getLoginUserInfo(HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        UserDto.LoginInfo loginInfo = (UserDto.LoginInfo) session.getAttribute(CommonConstants.LOGIN_USER_INFO);
        if(StringUtils.isNotEmpty(loginInfo.getUserAuthCodes())) {
            String[] userAuthCodeList = loginInfo.getUserAuthCodes().split("\\,");
            loginInfo.setUserAuthCodeList(userAuthCodeList);
        }

        return loginInfo;
    }

    /**
     * 시스템관리자 여부
     * @return true/false
     */
    public static boolean isSystemAdmin(UserDto.LoginInfo loginInfo) {

    	boolean sysAdmin = false;

        if(!org.springframework.util.ObjectUtils.isEmpty(loginInfo) && StringUtils.isNotEmpty(loginInfo.getUserAuthCodes())) {
            String[] userAuthCodeList = loginInfo.getUserAuthCodes().split("\\,");

            if(!"COM001".equals(loginInfo.getCompanyCd())) {
            	return false;
            }
            sysAdmin = Arrays.stream(userAuthCodeList).anyMatch(value -> value.equals("000") || value.equals("100"));
        }

        return sysAdmin;
    }
}
