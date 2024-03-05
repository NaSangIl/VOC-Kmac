package kr.co.kmac.voc.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.kmac.common.util.ResponseUtil;
import kr.co.kmac.coreframework.common.response.ResponseObject;
import kr.co.kmac.coreframework.controller.BaseController;
import kr.co.kmac.voc.dto.VocHistDto;
import kr.co.kmac.voc.service.VocHistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * VOC 이력정보 Controller
 * 
 * @ClassName VocCustController.java
 * @Description VOC이력정보
 * @author mjkim
 * @since 2023. 9. 18.
 */
@Tag(name = "VocCustController", description = "VOC 이력정보관리 API")
@RestController
@RequestMapping("/kmacvoc/v1/history")
public class VocHistController extends BaseController
{
    @Autowired
    private VocHistService service;

    /**
     * VOC이력정보 목록 조회
     * 
     * @param vocSeq VOC key
     * @return 결과 행 및 페이지 정보를 포함한 PaginatedResponse 객체
     */
    @Operation(summary = "GET VOC이력정보 목록 조회", description = "VOC이력정보 목록 조회")
    @GetMapping("/list/{vocSeq}")
    public ResponseObject<VocHistDto.ListResponse> getVocCustList(HttpServletRequest req
            , @Parameter(description = "VOC일련번호") @PathVariable(name = "vocSeq") int vocSeq)
    {
        return ResponseUtil.getResponse(req, service.getVocHistList(vocSeq));
    }
}
