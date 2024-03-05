package kr.co.kmac.system.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.kmac.common.util.ResponseUtil;
import kr.co.kmac.common.util.validation.PostMethod;
import kr.co.kmac.coreframework.common.response.ResponseObject;
import kr.co.kmac.coreframework.controller.BaseController;
import kr.co.kmac.system.dto.CustomCodeDto;
import kr.co.kmac.system.service.CompanyCustomCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 회사별 지정코드 관리/작업 Controller
 * 
 * @ClassName CompanyCustomCodeController.java
 * @Description 회사별 지정코드
 * @voctypeor mjkim
 * @since 2023. 9. 18.
 */
@Tag(name = "CompanyCustomCodeController", description = "회사별 지정코드관리 API")
@RestController
@RequestMapping("/kmacvoc/v1/company/custom")
public class CompanyCustomCodeController extends BaseController
{
    @Autowired
    private CompanyCustomCodeService service;

    /**
     * 회사별 지정코드 목록 조회
     *
     * @param param 회사별 지정코드 검색 조건 포함 객체
     * @return 결과 행 및 페이지 정보를 포함한 PaginatedResponse 객체
     */
    @Operation(summary = "GET 회사별 지정코드 목록 조회", description = "회사별 지정코드 목록 조회")
    @GetMapping("/list")
    public ResponseObject<CustomCodeDto.ListResponse> getCompanyCustomCodeList(HttpServletRequest req, CustomCodeDto.Request param)
    {
        return ResponseUtil.getResponse(req, service.getCompanyCustomCodeList(param));
    }

    /**
     * 회사별 지정코드 dropbox용 목록 조회
     *
     * @param param 회사별 지정코드 검색 조건 포함 객체
     * @return 결과 행 및 페이지 정보를 포함한 PaginatedResponse 객체
     */
    @Operation(summary = "GET 회사별 지정코드 dropbox용 목록 조회", description = "회사별 지정코드 dropbox용 목록 조회")
    @GetMapping("/code/list")
    public ResponseObject<CustomCodeDto.ListResponse> getCompanyCustomSelectList(HttpServletRequest req, CustomCodeDto.Request param)
    {
        return ResponseUtil.getResponse(req, service.getCompanyCustomSelectList(param));
    }

    /**
     * 회사별 지정코드 상세 조회
     *
     * @param customSeq 회사별 지정코드 검색 조건 포함 객체
     * @return 회사별 지정코드 객체
     */
    @Operation(summary = "GET 회사별 지정코드 상세 조회", description = "customSeq로 회사별 지정코드 정보 1건 조회")
    @GetMapping("/{customSeq}")
    public ResponseObject<CustomCodeDto.Info> getCompanyCustomCode(HttpServletRequest req
            , @Parameter(description = "회사별 지정코드일련번호") @PathVariable(name = "customSeq") int customSeq)
    {
        return ResponseUtil.getResponse(req, service.getCompanyCustomCode(customSeq));
    }

    /**
     * 회사별 지정코드 등록
     *ss
     * @param param 등록할 회사별 지정코드 객체
     * @return 등록결과 객체
     */
    @Operation(summary = "회사별 지정코드 등록", description = "회사별 지정코드 정보를 등록한다.")
    @PostMapping("/add")
    public ResponseObject insertCompanyCustomCode(HttpServletRequest req, @RequestBody @Validated(PostMethod.class) CustomCodeDto.Info param)
    {
        return ResponseUtil.getResponse(req, service.insertCompanyCustomCode(param));
    }

    /**
     * 회사별 지정코드 수정
     *
     * @param param 수정할 회사별 지정코드 객체
     * @return 수정결과 객체
     */
    @Operation(summary = "회사별 지정코드 수정", description = "회사별 지정코드 정보를 수정한다.")
    @PostMapping("/modify")
    public ResponseObject updateCompanyCustomCode(HttpServletRequest req, @RequestBody @Validated(PostMethod.class) CustomCodeDto.Info param)
    {
        return ResponseUtil.getResponse(req, service.updateCompanyCustomCode(param));
    }

    /**end
     * 회사별 지정코드 삭제
     *
     * @param customSeq 삭제할 회사별 지정코드 key
     */
    @Operation(summary = "회사별 지정코드 삭제", description = "회사별 지정코드정보를 삭제한다.")
    @PostMapping("/remove/{customSeq}")
    public ResponseObject deleteCompanyCustomCode(HttpServletRequest req
            , @Parameter(description = "회사별 지정코드일련번호") @PathVariable(name = "customSeq") int customSeq)
    {
        return ResponseUtil.getResponse(req, service.deleteCompanyCustomCode(customSeq));
    }

    /**
     * 회사별 지정코드 복사
     *
     * @param param 복사할 회사 객체
     * @return 복사결과 객체
     */
    @Operation(summary = "회사별 지정코드 복사", description = "회사별 지정코드 복사")
    @PostMapping(value="/copy")
    public ResponseObject copyCompanyCustomCode(HttpServletRequest req, @RequestBody CustomCodeDto.Info param) throws Exception
    {
        return ResponseUtil.getResponse(req, service.copyCompanyCustomCode(param));
    }

}
