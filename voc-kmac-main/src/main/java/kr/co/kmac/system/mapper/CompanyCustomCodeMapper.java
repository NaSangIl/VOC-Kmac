package kr.co.kmac.system.mapper;

import kr.co.kmac.system.dto.CustomCodeDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 회사별 지정코드 MyBatis Mapper 클래스
 * 
 * @ClassName CompanyCustomCodeMapper.java
 * @Description 회사별 지정코드 Mapper 클래스
 * @voctypeor mjkim
 * @since 2023. 9. 18.
 *
 */
@Repository
@Mapper
public interface CompanyCustomCodeMapper
{

    /**
     * 회사별 지정코드 목록 조회
     *
     * @param param 지정코드 검색 조건 포함 객체
     */
    List<CustomCodeDto.Info> getCompanyCustomCodeList(CustomCodeDto.Request param);

    /**
     * 회사별 지정코드 목록 개수 조회
     *
     * @param param 지정코드 검색 조건 포함 객체
     * @return 조건에 해당되는 전체 행 수
     */
    long getCompanyCustomCodeListCount(CustomCodeDto.Request param);

    /**
     * 회사별 지정코드 목록 dropbox용 조회
     *
     * @param param 지정코드 검색 조건 포함 객체
     */
    List<CustomCodeDto.Info> getCompanyCustomSelectList(CustomCodeDto.Request param);

    /**
     * 회사별 지정코드 상세 조회
     *
     * @param customSeq 지정코드 pk
     * @return 지정코드 객체
     */
    CustomCodeDto.Info getCompanyCustomCode(int customSeq);

    /**
     * 회사별 지정코드 입력(insert)
     *
     * @param param 저장 할 지정코드 객체
     * @return 적용된 행 수
     */
    int insertCompanyCustomCode(CustomCodeDto.Info param);

    /**
     * 회사별 지정코드 수정(update)
     *
     * @param param 저장 할 지정코드 객체
     * @return 적용된 행 수
     */
    int updateCompanyCustomCode(CustomCodeDto.Info param);

    /**
     * 회사별 지정코드 삭제
     *
     * @param customSeq 지정코드 pk
     * @return 적용된 행 수
     */
    int deleteCompanyCustomCode(int customSeq);
    int deleteCompanyCustomCodeLv2(int customSeq);
    int deleteCompanyCustomCodeLv3(int customSeq);

    /**
     * 회사별 지정코드별 삭제(insert)
     *
     * @param param 저장 할 회사 객체
     * @return 적용된 행 수
     */
    int deleteAllCompanyCustomCode(CustomCodeDto.Info param);

    /**
     * 회사별 지정코드 복사(insert)
     *
     * @param param 저장 할 회사 객체
     * @return 적용된 행 수
     */
    int copyCompanyCustomCode(CustomCodeDto.Info param);

}
