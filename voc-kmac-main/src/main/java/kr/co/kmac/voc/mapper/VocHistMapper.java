package kr.co.kmac.voc.mapper;

import kr.co.kmac.voc.dto.VocHistDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * VOC이력 MyBatis Mapper 클래스
 * 
 * @ClassName VocHistMapper.java
 * @Description VOC이력 Mapper 클래스
 * @author mjkim
 * @since 2023. 9. 18.
 *
 */
@Repository
@Mapper
public interface VocHistMapper
{
    /**
     * VOC이력 목록 조회
     *
     * @param vocSeq VOC이력 검색 조건 포함 객체
     */
    List<VocHistDto.Info> getVocHistList(int vocSeq);

    /**
     * VOC이력 상세 조회
     * 
     * @param histSeq VOC이력 pk
     * @return VOC이력 객체
     */
    VocHistDto.Info getVocHist(int histSeq);

    /**
     * VOC이력 입력(insert)
     * 
     * @param param 저장 할 VOC이력 객체
     * @return 적용된 행 수
     */
    int insertVocHist(VocHistDto.Info param);
}
