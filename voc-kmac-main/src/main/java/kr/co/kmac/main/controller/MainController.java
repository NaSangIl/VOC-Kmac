package kr.co.kmac.main.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.kmac.common.util.ResponseUtil;
import kr.co.kmac.coreframework.common.response.ResponseObject;
import kr.co.kmac.coreframework.controller.BaseController;
import kr.co.kmac.voc.dto.VocMstDto;
import kr.co.kmac.voc.service.VocMstService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 메인 Controller
 * 
 * @ClassName MainController.java
 * @Description 메인화면
 * @author mjkim
 * @since 2023. 12. 04.
 */
@Tag(name = "MainController", description = "메인화면 관리 API")
@RestController
@RequestMapping("/kmacvoc/v1/main")
public class MainController extends BaseController
{
    @Autowired
    private VocMstService service;

    /**
     * 메인화면 VOC현황 조회
     *
     * @param param 메인화면 VOC현황 검색 조건 포함 객체
     * @return 결과 행 및 페이지 정보를 포함한 Response 객체
     */
    @Operation(summary = "메인화면 VOC현황 조회", description = "메인화면 VOC현황 조회")
    @GetMapping("/voc-states")
    public ResponseObject<VocMstDto.StatusInfo> getVocStates(HttpServletRequest req, VocMstDto.Request param)
    {
        return ResponseUtil.getResponse(req, service.getVocStates(param));
    }

}
