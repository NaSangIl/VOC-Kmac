package kr.co.kmac.voc.mapper;

import kr.co.kmac.voc.dto.VocCustDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * VOC고객 MyBatis Mapper 클래스
 * 
 * @ClassName VocCustMapper.java
 * @Description VOC고객 Mapper 클래스
 * @author mjkim
 * @since 2023. 9. 18.
 *
 */
@Repository
@Mapper
public interface VocCustMapper
{
    /**
     * VOC고객 상세 조회
     * 
     * @param custSeq VOC고객 pk
     * @return VOC고객 객체
     */
    VocCustDto.Info getVocCust(int custSeq);

    /**
     * VOC고객 목록 조회
     * 
     * @param param VOC고객 검색 조건 포함 객체
     */
    List<VocCustDto.Info> getVocCustList(VocCustDto.Request param);

    /**
     * VOC고객 목록 개수 조회
     * 
     * @param param VOC고객 검색 조건 포함 객체
     * @return 조건에 해당되는 전체 행 수
     */
    long getVocCustListCount(VocCustDto.Request param);

    /**
     * VOC고객 입력(insert)
     * 
     * @param param 저장 할 VOC고객 객체
     * @return 적용된 행 수
     */
    int insertVocCust(VocCustDto.Info param);

    /**
     * VOC고객 수정(update)
     *
     * @param param 저장 할 VOC고객 객체
     * @return 적용된 행 수
     */
    int updateVocCust(VocCustDto.Info param);

    /**
     * VOC고객 삭제
     * 
     * @param custSeq 삭제 할 조건객체
     * @return 적용된 행 수
     */
    int deleteVocCust(int custSeq);
}
