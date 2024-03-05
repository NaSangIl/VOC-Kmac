package kr.co.kmac.system.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
import kr.co.kmac.system.dto.CodeDto;
import kr.co.kmac.system.dto.UserDto;
import kr.co.kmac.system.service.UserService;

/**
 * 사용자 관리/작업 Controller
 *
 * @ClassName UserController.java
 * @Description 사용자
 * @author mjkim
 * @since 2023. 9. 18.
 */
@Tag(name = "UserController", description = "사용자관리 API")
@RestController
@RequestMapping("/kmacvoc/v1/user")
public class UserController extends BaseController
{
    @Autowired
    private UserService service;

    /**
     * 사용자 목록 조회
     *
     * @param param 사용자 검색 조건 포함 객체
     * @return 결과 행 및 페이지 정보를 포함한 PaginatedResponse 객체
     */
    @Operation(summary = "GET 사용자 목록 조회", description = "사용자 목록 조회")
    @GetMapping("/list")
    public ResponseObject<UserDto.ListResponse> getUserList(HttpServletRequest req, UserDto.Request param)
    {
    	UserDto.LoginInfo loginInfo = LoginInfoUtil.getLoginUserInfo(req);
    	if (!LoginInfoUtil.isSystemAdmin(loginInfo)) {
			param.setLoginCompanyCd(loginInfo.getCompanyCd());
		}
        return ResponseUtil.getResponse(req, service.getUserList(param));
    }

    /**
     * 처리자 목록[dropdown용] 조회
     *
     * @param companyCd 회사코드
     */
    @Operation(summary = "처리자 목록조회", description = "dropdown용 공통코드 목록을 조회한다.")
    @GetMapping("/act/list/{companyCd}")
    public ResponseObject<List<CodeDto.CommonCode>> getActUserList(HttpServletRequest req
            , @Parameter(description = "회사코드") @PathVariable(name = "companyCd") String companyCd)
    {
    	if(StringUtils.isBlank(companyCd)) {
    		UserDto.LoginInfo loginInfo = LoginInfoUtil.getLoginUserInfo(req);
    		companyCd = loginInfo.getCompanyCd();
    	}
        return ResponseUtil.getResponse(req, service.getActUserList(companyCd));
    }

    /**
     * 사용자 상세 조회
     *
     * @param userSeq 사용자 검색 조건 포함 객체
     * @return 사용자 객체
     */
    @Operation(summary = "GET 사용자 상세 조회", description = "userSeq로 사용자 정보 1건 조회")
    @GetMapping("/{userSeq}")
    public ResponseObject<UserDto.Info> getUser(HttpServletRequest req
            , @Parameter(description = "고객일련번호") @PathVariable(name = "userSeq") int userSeq)
    {
        return ResponseUtil.getResponse(req, service.getUser(userSeq));
    }


    /**
     * 사용자 등록
     *ss
     * @param param 등록할 사용자 객체
     * @return 등록결과 객체
     */
    @Operation(summary = "사용자 등록", description = "사용자 정보를 등록한다.")
    @PostMapping(value="/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseObject insertUser(HttpServletRequest req, @RequestBody @Validated(PostMethod.class) UserDto.Info param)
    {
        return ResponseUtil.getResponse(req, service.insertUser(param));
    }

    /**
     * 사용자 수정
     *
     * @param param 수정할 사용자 객체
     * @return 수정결과 객체
     */
    @Operation(summary = "사용자 수정", description = "사용자 정보를 수정한다.")
    @PostMapping("/modify")
    public ResponseObject updateUser(HttpServletRequest req, @RequestBody UserDto.Info param)
    {
        return ResponseUtil.getResponse(req, service.updateUser(param));
    }

    /**
     * 사용자 비밀번호 수정
     *
     * @param param 수정할 사용자 객체
     * @return 수정결과 객체
     */
    @Operation(summary = "사용자 비밀번호 수정", description = "사용자 비밀번호 정보를 수정한다.")
    @PostMapping("/modify/pw")
    public ResponseObject updateUserPw(HttpServletRequest req, @RequestBody UserDto.Info param)
    {
        return ResponseUtil.getResponse(req, service.updateUserPw(param));
    }

    /**end
     * 사용자 삭제
     *
     * @param userSeq 삭제할 사용자 key
     */
    @Operation(summary = "VOC 삭제", description = "VOC정보를 삭제한다.")
    @PostMapping("/remove/{userSeq}")
    public ResponseObject deleteUser(HttpServletRequest req
            , @Parameter(description = "고객일련번호") @PathVariable(name = "userSeq") int userSeq)
    {
        return ResponseUtil.getResponse(req, service.deleteUser(userSeq));
    }

    /**
     * ID 중복체크
     *
     * @param param
     * @return
     */
    @Operation(summary = "사용자 ID 중복체크", description = "사용자 ID 중복체크 조회한다.")
    @PostMapping("/userIdForCheck")
    public ResponseObject userIdForCheck(HttpServletRequest req, @RequestBody UserDto.Info param)
    {
        return ResponseUtil.getResponse(req, service.userIdForCheck(param));
    }
}
