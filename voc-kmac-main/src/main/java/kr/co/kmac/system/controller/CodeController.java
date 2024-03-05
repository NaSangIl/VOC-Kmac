package kr.co.kmac.system.controller;

import java.util.List;

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
import kr.co.kmac.common.util.ResponseUtil;
import kr.co.kmac.common.util.validation.PostMethod;
import kr.co.kmac.coreframework.common.response.ResponseObject;
import kr.co.kmac.coreframework.controller.BaseController;
import kr.co.kmac.system.dto.CodeDto;
import kr.co.kmac.system.service.CodeService;

/**
 * 코드 관리/작업 Controller
 *
 * @ClassName CodeController.java
 * @Description 코드
 * @codeor mjkim
 * @since 2023. 9. 18.
 */
@Tag(name = "CodeController", description = "코드관리 API")
@RestController
@RequestMapping("/kmacvoc/v1/code")
public class CodeController extends BaseController
{
    @Autowired
    private CodeService service;

    /**
     * 코드 목록 조회
     *
     * @param param 코드 검색 조건 포함 객체
     * @return 결과 행 및 페이지 정보를 포함한 PaginatedResponse 객체
     */
    @Operation(summary = "GET 코드 목록 조회", description = "코드 목록 조회")
    @GetMapping("/list")
    public ResponseObject<CodeDto.ListResponse> getCodeList(HttpServletRequest req, CodeDto.Request param)
    {
        return ResponseUtil.getResponse(req, service.getCodeList(param));
    }

    /**
     * 코드 목록[dropdown용] 조회
     *
     * @param param 코드목록 조회할 조건 객체
     */
    @Operation(summary = "공통코드 목록조회", description = "dropdown용 공통코드 목록을 조회한다.")
    @GetMapping("/dropdown/list")
    public ResponseObject<List<CodeDto.CommonCode>> getCommonCodeList(HttpServletRequest req, CodeDto.Request param)
    {
        return ResponseUtil.getResponse(req, service.getCommonCodeList(param));
    }

    /**
     * 코드 상세 조회
     *
     * @param codeSeq 코드 검색 조건 포함 객체
     * @return 코드 객체
     */
    @Operation(summary = "GET 코드 상세 조회", description = "codeSeq로 코드 정보 1건 조회")
    @GetMapping("/{codeSeq}")
    public ResponseObject<CodeDto.Info> getCode(HttpServletRequest req
            , @Parameter(description = "코드일련번호") @PathVariable(name = "codeSeq") int codeSeq)
    {
        return ResponseUtil.getResponse(req, service.getCode(codeSeq));
    }

    /**
     * 코드 등록
     *ss
     * @param param 등록할 코드 객체
     * @return 등록결과 객체
     */
    @Operation(summary = "코드 등록", description = "코드 정보를 등록한다.")
    @PostMapping("/add")
    public ResponseObject insertCode(HttpServletRequest req, @RequestBody @Validated(PostMethod.class) CodeDto.Info param)
    {
        return ResponseUtil.getResponse(req, service.insertCode(param));
    }

    /**
     * 코드 수정
     *
     * @param param 수정할 코드 객체
     * @return 수정결과 객체
     */
    @Operation(summary = "코드 수정", description = "코드 정보를 수정한다.")
    @PostMapping("/modify")
    public ResponseObject updateCode(HttpServletRequest req, @RequestBody @Validated(PostMethod.class) CodeDto.Info param)
    {
        return ResponseUtil.getResponse(req, service.updateCode(param));
    }

    /**end
     * 코드 삭제
     *
     * @param codeSeq 삭제할 코드 key
     */
    @Operation(summary = "코드 삭제", description = "코드정보를 삭제한다.")
    @PostMapping("/remove/{codeSeq}")
    public ResponseObject deleteCode(HttpServletRequest req ,
                                     @Parameter(description = "코드일련번호") @PathVariable(name = "codeSeq") int codeSeq)
    {
        return ResponseUtil.getResponse(req, service.deleteCode(codeSeq));
    }
}
