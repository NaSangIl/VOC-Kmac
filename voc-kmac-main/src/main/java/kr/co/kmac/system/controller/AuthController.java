package kr.co.kmac.system.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.kmac.common.util.LoginInfoUtil;
import kr.co.kmac.common.util.ResponseUtil;
import kr.co.kmac.common.util.validation.PostMethod;
import kr.co.kmac.coreframework.common.response.ResponseObject;
import kr.co.kmac.coreframework.controller.BaseController;
import kr.co.kmac.system.dto.AuthDto;
import kr.co.kmac.system.dto.UserDto;
import kr.co.kmac.system.service.AuthService;

/**
 * 권한 관리/작업 Controller
 *
 * @ClassName AuthController.java
 * @Description 권한
 * @author mjkim
 * @since 2023. 9. 18.
 */
@Tag(name = "AuthController", description = "권한관리 API")
@RestController
@RequestMapping("/kmacvoc/v1/auth")
public class AuthController extends BaseController {
	@Autowired
	private AuthService service;

	/**
	 * 권한 목록 조회
	 *
	 * @param param 권한 검색 조건 포함 객체
	 * @return 결과 행 및 페이지 정보를 포함한 PaginatedResponse 객체
	 */
	@Operation(summary = "GET 권한 목록 조회", description = "권한 목록 조회")
	@GetMapping("/list")
	public ResponseObject<AuthDto.ListResponse> getAuthList(HttpServletRequest req, AuthDto.Request param) {
		UserDto.LoginInfo loginInfo = LoginInfoUtil.getLoginUserInfo(req);
		if (LoginInfoUtil.isSystemAdmin(loginInfo)) {
			param.setSysAdmin(true);
		}
		return ResponseUtil.getResponse(req, service.getAuthList(param));
	}

	/**
	 * 권한 상세 조회
	 *
	 * @param authSeq 권한 검색 조건 포함 객체
	 * @return 권한 객체
	 */
	@Operation(summary = "GET 권한 상세 조회", description = "authSeq로 권한 정보 1건 조회")
	@GetMapping("/{authSeq}")
	public ResponseObject<AuthDto.Info> getAuth(HttpServletRequest req, @Parameter(description = "권한일련번호") @PathVariable(name = "authSeq") int authSeq) {
		return ResponseUtil.getResponse(req, service.getAuth(authSeq));
	}

	/**
	 * 권한 등록
	 *
	 * @param param 등록할 권한 객체
	 * @return 등록결과 객체
	 */
	@Operation(summary = "권한 등록", description = "권한 정보를 등록한다.")
	@PostMapping("/add")
	public ResponseObject insertAuth(HttpServletRequest req, @RequestBody @Validated(PostMethod.class) AuthDto.Info param) {
		return ResponseUtil.getResponse(req, service.insertAuth(param));
	}

	/**
	 * 권한 수정
	 *
	 * @param param 수정할 권한 객체
	 * @return 수정결과 객체
	 */
	@Operation(summary = "권한 수정", description = "권한 정보를 수정한다.")
	@PostMapping("/modify")
	public ResponseObject updateAuth(HttpServletRequest req,
			@RequestBody @Validated(PostMethod.class) AuthDto.Info param) {
		return ResponseUtil.getResponse(req, service.updateAuth(param));
	}

	/**
	 * end 권한 삭제
	 *
	 * @param authSeq 삭제할 권한 key
	 */
	@Operation(summary = "권한 삭제", description = "권한정보를 삭제한다.")
	@PostMapping("/remove/{authSeq}")
	public ResponseObject deleteAuth(HttpServletRequest req,
			@Parameter(description = "권한일련번호") @PathVariable(name = "authSeq") int authSeq) {
		return ResponseUtil.getResponse(req, service.deleteAuth(authSeq));
	}
}
