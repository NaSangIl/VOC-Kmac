package kr.co.kmac.voc.controller;

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
import kr.co.kmac.system.dto.UserDto;
import kr.co.kmac.voc.dto.VocCustDto;
import kr.co.kmac.voc.service.VocCustService;

/**
 * VOC고객 관리/작업 Controller
 *
 * @ClassName VocCustController.java
 * @Description VOC고객
 * @author mjkim
 * @since 2023. 9. 18.
 */
@Tag(name = "VocCustController", description = "VOC 고객관리 API")
@RestController
@RequestMapping("/kmacvoc/v1/customer")
public class VocCustController extends BaseController {
	@Autowired
	private VocCustService service;

	/**
	 * VOC고객 목록 조회
	 *
	 * @param param VOC고객 검색 조건 포함 객체
	 * @return 결과 행 및 페이지 정보를 포함한 PaginatedResponse 객체
	 */
	@Operation(summary = "GET VOC고객 목록 조회", description = "VOC고객 목록 조회")
	@GetMapping("/list")
	public ResponseObject<VocCustDto.ListResponse> getVocCustList(HttpServletRequest req, VocCustDto.Request param) {
		UserDto.LoginInfo loginInfo = LoginInfoUtil.getLoginUserInfo(req);
		if (!LoginInfoUtil.isSystemAdmin(loginInfo)) {
			param.setCompanyCd(loginInfo.getCompanyCd());
		}
		return ResponseUtil.getResponse(req, service.getVocCustList(param));
	}

	/**
	 * VOC고객 상세 조회
	 *
	 * @param custSeq VOC고객 검색 조건 포함 객체
	 * @return VOC고객 객체
	 */
	@Operation(summary = "GET VOC고객 상세 조회", description = "vocSeq로 VOC고객 정보 1건 조회")
	@GetMapping("/{custSeq}")
	public ResponseObject<VocCustDto.Info> getVocCust(HttpServletRequest req, @Parameter(description = "고객일련번호") @PathVariable(name = "custSeq") int custSeq) {
		return ResponseUtil.getResponse(req, service.getVocCust(custSeq));
	}

	/**
	 * VOC고객 등록
	 *
	 * @param param 등록할 VOC고객 객체
	 * @return 등록결과 객체
	 */
	@Operation(summary = "VOC고객 등록", description = "VOC고객 정보를 등록한다.")
	@PostMapping("/add")
	public ResponseObject insertVocCust(HttpServletRequest req, @RequestBody @Validated(PostMethod.class) VocCustDto.Info param) {
		UserDto.LoginInfo loginInfo = LoginInfoUtil.getLoginUserInfo(req);
		if (!LoginInfoUtil.isSystemAdmin(loginInfo)) {
			param.setCompanyCd(loginInfo.getCompanyCd());
		}
		return ResponseUtil.getResponse(req, service.insertVocCust(param));
	}

	/**
	 * VOC고객 수정
	 *
	 * @param param 수정할 VOC고객 객체
	 * @return 수정결과 객체
	 */
	@Operation(summary = "VOC고객 수정", description = "VOC고객 정보를 수정한다.")
	@PostMapping("/modify")
	public ResponseObject updateVocCust(HttpServletRequest req, @RequestBody @Validated(PostMethod.class) VocCustDto.Info param) {
		UserDto.LoginInfo loginInfo = LoginInfoUtil.getLoginUserInfo(req);
		if (!LoginInfoUtil.isSystemAdmin(loginInfo)) {
			param.setCompanyCd(loginInfo.getCompanyCd());
		}
		return ResponseUtil.getResponse(req, service.updateVocCust(param));
	}

	/**
	 * VOC고객 삭제
	 *
	 * @param custSeq 삭제할 VOC고객 key
	 */
	@Operation(summary = "VOC 삭제", description = "VOC정보를 삭제한다.")
	@PostMapping("/remove/{custSeq}")
	public ResponseObject deleteVocCust(HttpServletRequest req, @Parameter(description = "고객일련번호") @PathVariable(name = "custSeq") int custSeq) {
		return ResponseUtil.getResponse(req, service.deleteVocCust(custSeq));
	}
}
