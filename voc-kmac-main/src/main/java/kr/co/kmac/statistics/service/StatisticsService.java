package kr.co.kmac.statistics.service;

import kr.co.kmac.coreframework.service.BaseService;
import kr.co.kmac.statistics.dto.StatisticsDto;
import kr.co.kmac.statistics.mapper.StatisticsMapper;
import kr.co.kmac.voc.dto.VocHistDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * VOC현황 서비스 클래스
 * 
 * @ClassName StatisticsService.java
 * @Description VOC현황 서비스 클래스
 * @author mjkim
 * @since 2023. 9. 18.
 */
@Service
public class StatisticsService extends BaseService
{
    @Autowired
    private StatisticsMapper mapper;

    /**
     * 기간별 VOC 현황 조회
     * 
     * @param param 현황 검색 조건 포함 객체
     * @return 결과 행 및 페이지 정보를 포함한 PaginatedResponse 객체
     */
    public StatisticsDto.ListResponse getVocStatusByPeriod(StatisticsDto.Request param)
    {
        List<StatisticsDto.Info> list = mapper.getVocStatusByPeriod(param);
        return StatisticsDto.ListResponse
                .<VocHistDto.Info>builder()
                .totalCount(list.size())
                .list(list)
                .build();
	}

    /**
     * 유형별 VOC 현황 조회
     *
     * @param param 현황 검색 조건 포함 객체
     * @return 결과 행 및 페이지 정보를 포함한 PaginatedResponse 객체
     */
    public StatisticsDto.ListResponse getVocStatusByVoctype(StatisticsDto.Request param)
    {
        List<StatisticsDto.Info> list = mapper.getVocStatusByVoctype(param);
        return StatisticsDto.ListResponse
                .<VocHistDto.Info>builder()
                .totalCount(list.size())
                .list(list)
                .build();
    }

    /**
     * 접수채널별 VOC 현황 조회
     *
     * @param param 현황 검색 조건 포함 객체
     * @return 결과 행 및 페이지 정보를 포함한 PaginatedResponse 객체
     */
    public StatisticsDto.ListResponse getVocStatusByChannel(StatisticsDto.Request param)
    {
        List<StatisticsDto.Info> list = mapper.getVocStatusByChannel(param);
        return StatisticsDto.ListResponse
                .<VocHistDto.Info>builder()
                .totalCount(list.size())
                .list(list)
                .build();
    }

    /**
     * 처리유형별 VOC 현황 조회
     *
     * @param param 현황 검색 조건 포함 객체
     * @return 결과 행 및 페이지 정보를 포함한 PaginatedResponse 객체
     */
    public StatisticsDto.ListResponse getVocStatusByActtype(StatisticsDto.Request param)
    {
        List<StatisticsDto.Info> list = mapper.getVocStatusByActtype(param);
        return StatisticsDto.ListResponse
                .<VocHistDto.Info>builder()
                .totalCount(list.size())
                .list(list)
                .build();
    }

    /**
     * 처리기간별 VOC 현황 조회
     *
     * @param param 현황 검색 조건 포함 객체
     * @return 결과 행 및 페이지 정보를 포함한 PaginatedResponse 객체
     */
    public StatisticsDto.ListResponse getVocStatusByActperiod(StatisticsDto.Request param)
    {
        List<StatisticsDto.Info> list = mapper.getVocStatusByActperiod(param);
        return StatisticsDto.ListResponse
                .<VocHistDto.Info>builder()
                .totalCount(list.size())
                .list(list)
                .build();
    }
}
