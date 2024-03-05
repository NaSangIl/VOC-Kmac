package kr.co.kmac.statistics.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.kmac.common.util.ResponseUtil;
import kr.co.kmac.coreframework.common.response.ResponseObject;
import kr.co.kmac.coreframework.controller.BaseController;
import kr.co.kmac.statistics.dto.StatisticsDto;
import kr.co.kmac.statistics.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * VOC 현황 Controller
 * 
 * @ClassName StatisticsController.java
 * @Description VOC현황
 * @author mjkim
 * @since 2023. 9. 18.
 */
@Tag(name = "StatisticsController", description = "VOC 현황관리 API")
@RestController
@RequestMapping("/kmacvoc/v1/statistics")
public class StatisticsController extends BaseController
{
    @Autowired
    private StatisticsService service;

    /**
     * 기간별 VOC 현황 조회
     * 
     * @param param 현황 검색 조건 포함 객체
     * @return 결과 행 및 페이지 정보를 포함한 PaginatedResponse 객체
     */
    @Operation(summary = "GET 기간별 VOC 현황 조회", description = "기간별 VOC 현황 조회")
    @GetMapping("/list/period")
    public ResponseObject<StatisticsDto.ListResponse> getVocStatusByPeriod(HttpServletRequest req, StatisticsDto.Request param)
    {
        return ResponseUtil.getResponse(req, service.getVocStatusByPeriod(param));
    }

    /**
     * 유형별 VOC 현황 조회
     *
     * @param param 현황 검색 조건 포함 객체
     * @return 결과 행 및 페이지 정보를 포함한 PaginatedResponse 객체
     */
    @Operation(summary = "GET 유형별 VOC 현황 조회", description = "유형별 VOC 현황 조회")
    @GetMapping("/list/voctype")
    public ResponseObject<StatisticsDto.ListResponse> getVocStatusByVoctype(HttpServletRequest req, StatisticsDto.Request param)
    {
        return ResponseUtil.getResponse(req, service.getVocStatusByVoctype(param));
    }

    /**
     * 접수채널별 VOC 현황 조회
     *
     * @param param 현황 검색 조건 포함 객체
     * @return 결과 행 및 페이지 정보를 포함한 PaginatedResponse 객체
     */
    @Operation(summary = "GET 접수채널별 VOC 현황 조회", description = "접수채널별 VOC 현황 조회")
    @GetMapping("/list/channel")
    public ResponseObject<StatisticsDto.ListResponse> getVocStatusByChannel(HttpServletRequest req, StatisticsDto.Request param)
    {
        return ResponseUtil.getResponse(req, service.getVocStatusByChannel(param));
    }

    /**
     * 처리유형별 VOC 현황 조회
     *
     * @param param 현황 검색 조건 포함 객체
     * @return 결과 행 및 페이지 정보를 포함한 PaginatedResponse 객체
     */
    @Operation(summary = "GET 처리유형별 VOC 현황 조회", description = "처리유형별 VOC 현황 조회")
    @GetMapping("/list/acttype")
    public ResponseObject<StatisticsDto.ListResponse> getVocStatusByActtype(HttpServletRequest req, StatisticsDto.Request param)
    {
        return ResponseUtil.getResponse(req, service.getVocStatusByActtype(param));
    }

    /**
     * 처리기간별 VOC 현황 조회
     *
     * @param param 현황 검색 조건 포함 객체
     * @return 결과 행 및 페이지 정보를 포함한 PaginatedResponse 객체
     */
    @Operation(summary = "GET 처리기간별 VOC 현황 조회", description = "처리기간별 VOC 현황 조회")
    @GetMapping("/list/actperiod")
    public ResponseObject<StatisticsDto.ListResponse> getVocStatusByActperiod(HttpServletRequest req, StatisticsDto.Request param)
    {
        return ResponseUtil.getResponse(req, service.getVocStatusByActperiod(param));
    }
}
