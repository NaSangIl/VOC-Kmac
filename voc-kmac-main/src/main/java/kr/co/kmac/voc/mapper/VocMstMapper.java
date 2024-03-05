package kr.co.kmac.voc.mapper;

import kr.co.kmac.voc.dto.VocMstDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * VOC마스터 MyBatis Mapper 클래스
 * 
 * @ClassName VocMstMapper.java
 * @Description VOC마스터 Mapper 클래스
 * @author mjkim
 * @since 2023. 9. 18.
 *
 */
@Repository
@Mapper
public interface VocMstMapper
{
    /**
     * VOC마스터 목록 조회
     * 
     * @param param VOC마스터 검색 조건 포함 객체
     */
    List<VocMstDto.Info> getVocMstList(VocMstDto.Request param);

    /**
     * VOC마스터 목록 개수 조회
     * 
     * @param param VOC마스터 검색 조건 포함 객체
     * @return 조건에 해당되는 전체 행 수
     */
    long getVocMstListCount(VocMstDto.Request param);

    /**
     * VOC마스터 상세 조회
     *
     * @param vocSeq VOC마스터 pk
     * @return VOC마스터 객체
     */
    VocMstDto.Info getVocMst(int vocSeq);

    /**
     * 메인화면 VOC현황 조회
     *
     * @param param VOC마스터 객체
     * @return VOC마스터 객체
     */
    VocMstDto.StatusInfo getVocStates(VocMstDto.Request param);

    /**
     * VOC마스터 입력(insert)
     * 
     * @param param 저장 할 VOC마스터 객체
     * @return 적용된 행 수
     */
    int insertVocMst(VocMstDto.Info param);

    /**
     * VOC마스터 수정(update)
     *
     * @param param 저장 할 VOC마스터 객체
     * @return 적용된 행 수
     */
    int updateVocMst(VocMstDto.Info param);

    /**
     * VOC마스터 상태 수정(update)
     *
     * @param param 저장 할 VOC마스터 객체
     * @return 적용된 행 수
     */
    int updateVocMstStatus(VocMstDto.Info param);

    /**
     * VOC마스터 삭제(update)
     *
     * @param param 저장 할 VOC마스터 객체
     * @return 적용된 행 수
     */
    int deleteVocMst(VocMstDto.Info param);
}
