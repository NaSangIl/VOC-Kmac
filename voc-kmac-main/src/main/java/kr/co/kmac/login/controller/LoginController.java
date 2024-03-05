package kr.co.kmac.login.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.kmac.common.constants.CommonConstants;
import kr.co.kmac.common.util.ResponseUtil;
import kr.co.kmac.coreframework.common.response.ResponseObject;
import kr.co.kmac.login.service.LoginService;
import kr.co.kmac.system.dto.UserDto;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Tag(name = "LoginController", description = "login")
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    /**
     * @Description 로그인 처리
     * @author mjkim
     * @since 2023. 9. 18.
     */
    @Operation(summary = "login", description = "login")
    @PostMapping("/login")
    public ResponseObject<UserDto.LoginInfo> loginCheck(HttpServletRequest req, @RequestBody UserDto.LoginRequest param) {
        UserDto.LoginInfo loginInfo = loginService.loginCheck(param);

        if (ObjectUtils.isNotEmpty(loginInfo)) {
            HttpSession session = req.getSession();
            // 세션 세팅
            session.setAttribute(CommonConstants.LOGIN_USER_INFO, loginInfo);
        }
        return ResponseUtil.getResponse(req, loginInfo);
    }
}

