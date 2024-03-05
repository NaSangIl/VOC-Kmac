package kr.co.kmac.system.mapper;

import kr.co.kmac.system.dto.CompanyDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 회사 MyBatis Mapper 클래스
 *
 * @ClassName CompanyMapper.java
 * @Description 회사 Mapper 클래스
 * @author mjkim
 * @since 2023. 9. 18.
 *
 */
@Repository
@Mapper
public interface CompanyMapper
{
    /**
    /**
     * 회사 목록 조회
     *
     * @param param 회사 검색 조건 포함 객체
     */
    List<CompanyDto.Info> getCompanyList(CompanyDto.Request param);

    /**
     * 회사 목록 개수 조회
     *
     * @param param 회사 검색 조건 포함 객체
     * @return 조건에 해당되는 전체 행 수
     */
    long getCompanyListCount(CompanyDto.Request param);

    /**
     * 회사 상세 조회
     *
     * @param companySeq 회사 pk
     * @return 회사 객체
     */
    CompanyDto.Info getCompany(int companySeq);

    /**
     * 회사 입력(insert)
     *
     * @param param 저장 할 회사 객체
     * @return 적용된 행 수
     */
    int insertCompany(CompanyDto.Info param);

    /**
     * 회사 수정(update)
     *
     * @param param 저장 할 회사 객체
     * @return 적용된 행 수
     */
    int updateCompany(CompanyDto.Info param);

    /**
     * 회사 삭제
     *
     * @param companySeq 회사 pk
     * @return 적용된 행 수
     */
    int deleteCompany(int companySeq);

    /**
     * 회사코드 부여
     *
     * @param companySeq 회사 pk
     * @return 적용된 행 수
     */
	int updateCompanyCode(int companySeq);
}
