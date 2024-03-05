package kr.co.kmac.voc.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.kmac.common.dto.AttachFileDto;
import kr.co.kmac.common.util.FileUtil;
import kr.co.kmac.common.util.LoginInfoUtil;
import kr.co.kmac.common.util.ResponseUtil;
import kr.co.kmac.common.util.validation.PostMethod;
import kr.co.kmac.coreframework.common.response.ResponseObject;
import kr.co.kmac.coreframework.controller.BaseController;
import kr.co.kmac.system.dto.UserDto;
import kr.co.kmac.voc.dto.VocMstDto;
import kr.co.kmac.voc.service.VocMstService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * VOC마스터 관리/작업 Controller
 *
 * @ClassName VocMstController.java
 * @Description VOC마스터
 * @author mjkim
 * @since 2023. 9. 18.
 */
@Tag(name = "VocMstController", description = "VOC 처리 API")
@RestController
@RequestMapping("/kmacvoc/v1/voc")
public class VocMstController extends BaseController
{
    @Autowired
    private VocMstService service;

    /**
     * VOC마스터 목록 조회
     *
     * @param param VOC마스터 검색 조건 포함 객체
     * @return 결과 행 및 페이지 정보를 포함한 PaginatedResponse 객체
     */
    @Operation(summary = "GET VOC 목록 조회", description = "VOC정보 목록 조회")
    @GetMapping("/list")
    public ResponseObject<VocMstDto.ListResponse> getVocMstList(HttpServletRequest req, VocMstDto.Request param)
    {
    	UserDto.LoginInfo loginInfo = LoginInfoUtil.getLoginUserInfo(req);
		if (!LoginInfoUtil.isSystemAdmin(loginInfo)) {
			param.setCompanyCd(loginInfo.getCompanyCd());
		}
        return ResponseUtil.getResponse(req, service.getVocMstList(param));
    }

    /**
     * VOC마스터 상세 조회
     *
     * @param vocSeq VOC마스터 검색 조건 포함 객체
     * @return VOC마스터 객체
     */
    @Operation(summary = "GET VOC 상세 조회", description = "vocSeq로 VOC정보 1건 조회")
    @GetMapping("/{vocSeq}")
    public ResponseObject<VocMstDto.DetailResponse> getVocMst(HttpServletRequest req
            , @Parameter(description = "VOC일련번호") @PathVariable(name = "vocSeq") int vocSeq)
    {
        return ResponseUtil.getResponse(req, service.getVocMst(vocSeq));
    }

    /**
     * VOC 등록
     *
     * @param param 등록할 VOC마스터 객체
     * @return VOC등록결과 객체
     */
    @Operation(summary = "VOC 등록", description = "VOC정보를 등록한다.")
    @PostMapping("/add")
    public ResponseObject insertVocMst(MultipartHttpServletRequest req, @Validated(PostMethod.class) VocMstDto.Info param) throws Exception
    {
    	UserDto.LoginInfo loginInfo = LoginInfoUtil.getLoginUserInfo(req);
    	if (!LoginInfoUtil.isSystemAdmin(loginInfo) || ObjectUtils.isEmpty(param.getCompanyCd())) {
			param.setCompanyCd(loginInfo.getCompanyCd());
		}
        _attachFile(param, req);
        return ResponseUtil.getResponse(req, service.insertVocMst(param));
    }

    /**
     * VOC 수정
     *
     * @param param 수정할 VOC마스터 객체
     * @return VOC수정결과 객체
     */
    @Operation(summary = "VOC 수정", description = "VOC정보를 수정한다.")
    @PostMapping("/modify")
    public ResponseObject updateVocMst(MultipartHttpServletRequest req, @Validated(PostMethod.class) VocMstDto.Info param) throws Exception
    {
    	UserDto.LoginInfo loginInfo = LoginInfoUtil.getLoginUserInfo(req);
    	if (!LoginInfoUtil.isSystemAdmin(loginInfo) || ObjectUtils.isEmpty(param.getCompanyCd())) {
			param.setCompanyCd(loginInfo.getCompanyCd());
		}
        _attachFile(param, req);
        return ResponseUtil.getResponse(req, service.updateVocMst(param));
    }

    /**
     * VOC 완료상신
     *
     * @param param 완료상신처리할 VOC마스터 객체
     * @return VOC완료상신결과 객체
     */
    @Operation(summary = "VOC 완료상신", description = "VOC정보를 완료상신처리한다.")
    @PostMapping("/approval")
    public ResponseObject procApproval(MultipartHttpServletRequest req, @Validated(PostMethod.class) VocMstDto.Info param) throws Exception
    {
    	UserDto.LoginInfo loginInfo = LoginInfoUtil.getLoginUserInfo(req);
    	if (!LoginInfoUtil.isSystemAdmin(loginInfo) || ObjectUtils.isEmpty(param.getCompanyCd())) {
			param.setCompanyCd(loginInfo.getCompanyCd());
		}
        _attachFile(param, req);
        return ResponseUtil.getResponse(req, service.procApproval(param));
    }

    /**
     * VOC 반려처리
     *
     * @param param 반려처리할 VOC마스터 객체
     * @return VOC반려처리결과 객체
     */
    @Operation(summary = "VOC 반려처리", description = "VOC정보를 반려처리한다.")
    @PostMapping("/reject")
    public ResponseObject procReject(HttpServletRequest req, @RequestBody VocMstDto.Info param)
    {
    	UserDto.LoginInfo loginInfo = LoginInfoUtil.getLoginUserInfo(req);
    	if (!LoginInfoUtil.isSystemAdmin(loginInfo) || ObjectUtils.isEmpty(param.getCompanyCd())) {
			param.setCompanyCd(loginInfo.getCompanyCd());
		}
        return ResponseUtil.getResponse(req, service.procReject(param));
    }

    /**
     * VOC 완료
     *
     * @param param 완료처리할 VOC마스터 객체
     * @return VOC완료결과 객체
     */
    @Operation(summary = "VOC 완료", description = "VOC정보를 완료처리한다.")
    @PostMapping("/finish")
    public ResponseObject finishVocMst(HttpServletRequest req, @RequestBody VocMstDto.Info param)
    {
    	UserDto.LoginInfo loginInfo = LoginInfoUtil.getLoginUserInfo(req);
    	if (!LoginInfoUtil.isSystemAdmin(loginInfo) || ObjectUtils.isEmpty(param.getCompanyCd())) {
			param.setCompanyCd(loginInfo.getCompanyCd());
		}
        return ResponseUtil.getResponse(req, service.finishVocMst(param));
    }

    /**
     * VOC 취소
     *
     * @param param 취소처리할 VOC마스터 객체
     * @return VOC취소결과 객체
     */
    @Operation(summary = "VOC 취소", description = "VOC정보를 취소처리한다.")
    @PostMapping("/cancel")
    public ResponseObject cancelVocMst(HttpServletRequest req, @RequestBody VocMstDto.Info param)
    {
    	UserDto.LoginInfo loginInfo = LoginInfoUtil.getLoginUserInfo(req);
    	if (!LoginInfoUtil.isSystemAdmin(loginInfo) || ObjectUtils.isEmpty(param.getCompanyCd())) {
			param.setCompanyCd(loginInfo.getCompanyCd());
		}
        return ResponseUtil.getResponse(req, service.cancelVocMst(param));
    }

    /**
     * VOC마스터 삭제
     *
     * @param param 삭제할 VOC마스터 객체
     */
    @Operation(summary = "VOC 삭제", description = "VOC정보를 삭제한다.")
    @PostMapping("/remove")
    public ResponseObject deleteVocMst(HttpServletRequest req, @RequestBody VocMstDto.Info param)
    {
    	UserDto.LoginInfo loginInfo = LoginInfoUtil.getLoginUserInfo(req);
    	if (!LoginInfoUtil.isSystemAdmin(loginInfo) || ObjectUtils.isEmpty(param.getCompanyCd())) {
			param.setCompanyCd(loginInfo.getCompanyCd());
		}
        return ResponseUtil.getResponse(req, service.deleteVocMst(param));
    }

    /**
     * VOC 파일첨부 공통처리
     *
     * @param param 파일업로드 후 셋팅할 파일정보 객체
     * @param mRequest
     */
    private void _attachFile(VocMstDto.Info param, MultipartHttpServletRequest mRequest) throws Exception {
        List<AttachFileDto.Info> fileList = null;

        UserDto.LoginInfo loginInfo = LoginInfoUtil.getLoginUserInfo(mRequest);
		if (!LoginInfoUtil.isSystemAdmin(loginInfo) || ObjectUtils.isEmpty(param.getCompanyCd())) {
			param.setCompanyCd(loginInfo.getCompanyCd());
		}

        //VOC 첨부파일
        List<MultipartFile> files = mRequest.getFiles("vocFiles");
        if(files.size() > 0 && StringUtils.isNotEmpty(files.get(0).getOriginalFilename())) {
            fileList = FileUtil.uploadFiles(files);
            param.setFileList1(fileList);
        }

        //VOC처리 첨부파일
        files = mRequest.getFiles("vocActFiles");
        if(files.size() > 0 && StringUtils.isNotEmpty(files.get(0).getOriginalFilename())) {
            fileList = FileUtil.uploadFiles(files);
            param.setFileList2(fileList);
        }
    }
}
