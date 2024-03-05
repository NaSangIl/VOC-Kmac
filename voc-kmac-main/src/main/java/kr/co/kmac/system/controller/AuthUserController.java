package kr.co.kmac.system.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.kmac.common.util.LoginInfoUtil;
import kr.co.kmac.common.util.ResponseUtil;
import kr.co.kmac.coreframework.common.response.ResponseObject;
import kr.co.kmac.coreframework.controller.BaseController;
import kr.co.kmac.system.dto.AuthUserDto;
import kr.co.kmac.system.dto.UserDto;
import kr.co.kmac.system.service.AuthUserService;

/**
 * 권한사용자매핑정보 Controller
 *
 * @ClassName AuthUserController.java
 * @Description 권한
 * @author mjkim
 * @since 2023. 9. 18.
 */
@Tag(name = "AuthUserController", description = "권한사용자매핑 API")
@RestController
@RequestMapping("/kmacvoc/v1/authuser")
public class AuthUserController extends BaseController {
	@Autowired
	private AuthUserService service;

	/**
	 * 권한사용자 목록 조회
	 *
	 * @param param 권한 검색 조건 포함 객체
	 * @return 결과 행 및 페이지 정보를 포함한 PaginatedResponse 객체
	 */
	@Operation(summary = "GET 권한 목록 조회", description = "권한 목록 조회")
	@GetMapping("/list")
	public ResponseObject<AuthUserDto.ListResponse> getAuthUserList(HttpServletRequest req, AuthUserDto.Request param) {
		UserDto.LoginInfo loginInfo = LoginInfoUtil.getLoginUserInfo(req);
		if (!LoginInfoUtil.isSystemAdmin(loginInfo) || StringUtils.isBlank(param.getCompanyCd())) {
			param.setCompanyCd(loginInfo.getCompanyCd());
		}
		return ResponseUtil.getResponse(req, service.getAuthUserList(param));
	}

	/**
	 * 권한사용자 등록
	 *
	 * @param param 등록할 권한 객체
	 * @return 등록결과 객체
	 */
	@Operation(summary = "권한 등록", description = "권한 정보를 등록한다.")
	@PostMapping("/add")
	public ResponseObject insertAuthUser(HttpServletRequest req, @RequestBody AuthUserDto.Info param) {
		UserDto.LoginInfo loginInfo = LoginInfoUtil.getLoginUserInfo(req);
		if (!LoginInfoUtil.isSystemAdmin(loginInfo) || StringUtils.isBlank(param.getCompanyCd())) {
			param.setCompanyCd(loginInfo.getCompanyCd());
		}
		return ResponseUtil.getResponse(req, service.insertAuthUser(param));
	}

	/**
	 * end 권한사용자 삭제
	 *
	 * @param param 삭제할 권한 객체
	 */
	@Operation(summary = "권한 삭제", description = "권한정보를 삭제한다.")
	@PostMapping("/remove")
	public ResponseObject deleteAuthUser(HttpServletRequest req, @RequestBody AuthUserDto.Info param) {
		UserDto.LoginInfo loginInfo = LoginInfoUtil.getLoginUserInfo(req);
		if (!LoginInfoUtil.isSystemAdmin(loginInfo) || StringUtils.isBlank(param.getCompanyCd())) {
			param.setCompanyCd(loginInfo.getCompanyCd());
		}
		return ResponseUtil.getResponse(req, service.deleteAuthUser(param));
	}
}
