package kr.co.kmac.statistics.mapper;

import kr.co.kmac.statistics.dto.StatisticsDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * VOC현황 MyBatis Mapper 클래스
 * 
 * @ClassName StatisticsMapper.java
 * @Description VOC현황 Mapper 클래스
 * @author mjkim
 * @since 2023. 9. 18.
 *
 */
@Repository
@Mapper
public interface StatisticsMapper
{
    /**
     * 기간별 VOC 현황 조회
     *
     * @param param 검색 조건 포함 객체
     */
    List<StatisticsDto.Info> getVocStatusByPeriod(StatisticsDto.Request param);

    /**
     * 유형별 VOC 현황 조회
     *
     * @param param 검색 조건 포함 객체
     */
    List<StatisticsDto.Info> getVocStatusByVoctype(StatisticsDto.Request param);

    /**
     * 접수채널별 VOC 현황 조회
     *
     * @param param 검색 조건 포함 객체
     */
    List<StatisticsDto.Info> getVocStatusByChannel(StatisticsDto.Request param);

    /**
     * 처리유형별 VOC 현황 조회
     *
     * @param param 검색 조건 포함 객체
     */
    List<StatisticsDto.Info> getVocStatusByActtype(StatisticsDto.Request param);

    /**
     * 처리기간별 VOC 현황 조회
     *
     * @param param 검색 조건 포함 객체
     */
    List<StatisticsDto.Info> getVocStatusByActperiod(StatisticsDto.Request param);
}
