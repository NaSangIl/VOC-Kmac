package kr.co.kmac.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.kmac.system.dto.CodeDto;

/**
 * 코드 MyBatis Mapper 클래스
 *
 * @ClassName CodeMapper.java
 * @Description 코드 Mapper 클래스
 * @author mjkim
 * @since 2023. 9. 18.
 *
 */
@Repository
@Mapper
public interface CodeMapper
{
    /**
     * 코드 목록 조회
     *
     * @param param 코드 검색 조건 포함 객체
     */
    List<CodeDto.Info> getCodeList(CodeDto.Request param);

    /**
     * 코드 목록 개수 조회
     *
     * @param param 코드 검색 조건 포함 객체
     * @return 조건에 해당되는 전체 행 수
     */
    long getCodeListCount(CodeDto.Request param);

    /**
     * 코드 목록[콤보박스용] 조회
     *
     * @param param 콤보박스용 코드 조건 포함 객체
     */
    List<CodeDto.CommonCode> getCommonCodeList(CodeDto.Request param);

    /**
     * 코드 상세 조회
     *
     * @param codeSeq 코드 pk
     * @return 코드 객체
     */
    CodeDto.Info getCode(int codeSeq);

    /**
     * 코드 입력(insert)
     *
     * @param param 저장 할 코드 객체
     * @return 적용된 행 수
     */
    int insertCode(CodeDto.Info param);

    /**
     * 코드 수정(update)
     *
     * @param param 저장 할 코드 객체
     * @return 적용된 행 수
     */
    int updateCode(CodeDto.Info param);

    /**
     * 코드 삭제
     *
     * @param param 삭제 할 조건객체
     * @return 적용된 행 수
     */
    int deleteCode(int codeSeq);
}
