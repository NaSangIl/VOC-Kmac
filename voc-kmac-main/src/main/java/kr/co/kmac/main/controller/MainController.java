package kr.co.kmac.main.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.kmac.bbs.dto.BbsDto;
import kr.co.kmac.common.util.ResponseUtil;
import kr.co.kmac.coreframework.common.response.ResponseObject;
import kr.co.kmac.coreframework.controller.BaseController;
import kr.co.kmac.voc.dto.VocMstDto;
import kr.co.kmac.voc.service.VocMstService;

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
    
    /**
     * (신) 메인화면 VOC현황 및 추이 조회
     *
     * @param param 메인화면 VOC현황 검색 조건 포함 객체
     * @return 결과 행 및 페이지 정보를 포함한 Response 객체
     */
    @Operation(summary = "메인화면 VOC현황 및 추이 조회", description = "메인화면 VOC현황 및 추이 조회")
    @GetMapping("/voc-progress")
    public ResponseObject<List<VocMstDto.ProgressInfo>> getVocProgress(HttpServletRequest req, VocMstDto.Request param)
    {
        return ResponseUtil.getResponse(req, service.getVocProgress(param));
    }
    
    /**
     * (신) 메인화면 VOC처리현황 조회
     *
     * @param param 메인화면 VOC현황 검색 조건 포함 객체
     * @return 결과 행 및 페이지 정보를 포함한 Response 객체
     */
    @Operation(summary = "메인화면 VOC처리현황 조회", description = "메인화면 VOC처리현황 조회")
    @GetMapping("/voc-ActProgress")
    public ResponseObject<VocMstDto.ActProgressInfo> getVocActProgress(HttpServletRequest req, VocMstDto.Request param)
    {
        return ResponseUtil.getResponse(req, service.getVocActProgress(param));
    }    

    
    /**
     * (신) 메인화면 VOC유형별 접수현황 조회
     *
     * @param param 메인화면 VOC현황 검색 조건 포함 객체
     * @return 결과 행 및 페이지 정보를 포함한 Response 객체
     */
    @Operation(summary = "메인화면 VOC유형별 접수현황 조회(대분류)", description = "메인화면 VOC유형별 접수현황 조회(대분류)")
    @GetMapping("/voc-TypeStates1")
    public ResponseObject<List<VocMstDto.VocTypeStatusInfo>> getVocTypeStates1Lev(HttpServletRequest req, VocMstDto.Request param)
    {
        return ResponseUtil.getResponse(req, service.getVocTypeStates1Lev(param));
    }    

    /**
     * (신) 메인화면 VOC유형별 접수현황 조회
     *
     * @param param 메인화면 VOC현황 검색 조건 포함 객체
     * @return 결과 행 및 페이지 정보를 포함한 Response 객체
     */
    @Operation(summary = "메인화면 VOC유형별 접수현황 조회(중분류)", description = "메인화면 VOC유형별 접수현황 조회(중분류)")
    @GetMapping("/voc-TypeStates2")
    public ResponseObject<List<VocMstDto.VocTypeStatusInfo>> getVocTypeStates2Lev(HttpServletRequest req, VocMstDto.Request param)
    {
        return ResponseUtil.getResponse(req, service.getVocTypeStates2Lev(param));
    }    
    
    /**
     * (신) 메인화면 VOC유형별 접수현황 조회
     *
     * @param param 메인화면 VOC현황 검색 조건 포함 객체
     * @return 결과 행 및 페이지 정보를 포함한 Response 객체
     */
    @Operation(summary = "메인화면 VOC유형별 접수현황 조회(소분류)", description = "메인화면 VOC유형별 접수현황 조회(소분류)")
    @GetMapping("/voc-TypeStates3")
    public ResponseObject<List<VocMstDto.VocTypeStatusInfo>> getVocTypeStates3Lev(HttpServletRequest req, VocMstDto.Request param)
    {
        return ResponseUtil.getResponse(req, service.getVocTypeStates3Lev(param));
    }        
   

    /**
     * (신) 메인화면 VOC처리유형별 접수현황 조회
     *
     * @param param 메인화면 VOC현황 검색 조건 포함 객체
     * @return 결과 행 및 페이지 정보를 포함한 Response 객체
     */
    @Operation(summary = "메인화면 VOC처리유형별 접수현황 조회(대분류)", description = "메인화면 VOC처리유형별 접수현황 조회(대분류)")
    @GetMapping("/voc-ActTypeStates1")
    public ResponseObject<List<VocMstDto.VocActTypeStatusInfo>> getVocActTypeStates1Lev(HttpServletRequest req, VocMstDto.Request param)
    {
        return ResponseUtil.getResponse(req, service.getVocActTypeStates1Lev(param));
    }    

    /**
     * (신) 메인화면 VOC처리유형별 접수현황 조회
     *
     * @param param 메인화면 VOC현황 검색 조건 포함 객체
     * @return 결과 행 및 페이지 정보를 포함한 Response 객체
     */
    @Operation(summary = "메인화면 VOC처리유형별 접수현황 조회(중분류)", description = "메인화면 VOC처리유형별 접수현황 조회(중분류)")
    @GetMapping("/voc-ActTypeStates2")
    public ResponseObject<List<VocMstDto.VocActTypeStatusInfo>> getVocActTypeStates2Lev(HttpServletRequest req, VocMstDto.Request param)
    {
        return ResponseUtil.getResponse(req, service.getVocActTypeStates2Lev(param));
    }  

    /**
     * (신) 메인화면 공지사항 목록 조회
     * 
     * @param param 게시판 검색 조건 포함 객체
     * @return 결과 행 및 페이지 정보를 포함한 PaginatedResponse 객체
     */
    @Operation(summary = "GET 공지사항 목록 조회", description = "공지사항 목록 조회")
    @GetMapping("/mian-List")
    public ResponseObject<List<BbsDto.MainNoticeListInfo>> getMainNoticeList(HttpServletRequest req, BbsDto.Request param)
    {
        return ResponseUtil.getResponse(req, service.getMainNoticeList(param));
    }  

}
