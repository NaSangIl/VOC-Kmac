package kr.co.kmac.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.kmac.system.dto.CustomCodeDto;
import kr.co.kmac.system.dto.CustomCodeMstDto;

/**
 * 회사지정코드 MyBatis Mapper 클래스
 *
 * @ClassName CustomCodeMapper.java
 * @Description 회사지정코드 Mapper 클래스
 * @customor mjkim
 * @since 2023. 9. 18.
 *
 */
@Repository
@Mapper
public interface CustomCodeMapper
{
    /**
     * 회사지정코드마스터 목록 조회
     *
     * @param param 회사지정코드마스터 검색 조건 포함 객체
     */
    List<CustomCodeMstDto.Info> getCustomCodeMstList(CustomCodeMstDto.Request param);

    /**
     * 회사지정코드마스터 목록 개수 조회
     *
     * @param param 회사지정코드마스터 검색 조건 포함 객체
     * @return 조건에 해당되는 전체 행 수
     */
    long getCustomCodeMstListCount(CustomCodeMstDto.Request param);

    /**
     * 회사지정코드 목록 조회
     *
     * @param param 회사지정코드 검색 조건 포함 객체
     */
    List<CustomCodeDto.Info> getCustomCodeList(CustomCodeDto.Request param);

    /**
     * 회사지정코드 목록 개수 조회
     *
     * @param param 회사지정코드 검색 조건 포함 객체
     * @return 조건에 해당되는 전체 행 수
     */
    long getCustomCodeListCount(CustomCodeDto.Request param);

    /**
     * 회사지정코드 목록 dropbox용 조회
     *
     * @param param 회사지정코드 검색 조건 포함 객체
     */
    List<CustomCodeDto.Info> getCustomCodeCodeList(CustomCodeDto.Request param);


    /**
     * 회사지정코드마스터 상세 조회
     *
     * @param customMstSeq 회사지정코드마스터 pk
     * @return 회사지정코드마스터 객체
     */
    CustomCodeMstDto.Info getCustomCodeMst(int customMstSeq);

    /**
     * 회사지정코드 상세 조회
     *
     * @param customSeq 회사지정코드 pk
     * @return 회사지정코드 객체
     */
    CustomCodeDto.Info getCustomCode(int customSeq);

    /**
     * 회사지정코드마스터 입력(insert)
     *
     * @param param 저장 할 회사지정코드 객체
     * @return 적용된 행 수
     */
    int insertCustomCodeMst(CustomCodeMstDto.Info param);

    /**
     * 회사지정코드 입력(insert)
     *
     * @param param 저장 할 회사지정코드 객체
     * @return 적용된 행 수
     */
    int insertCustomCode(CustomCodeDto.Info param);

    /**
     * 회사지정코드마스터 수정(update)
     *
     * @param param 저장 할 회사지정코드마스터 객체
     * @return 적용된 행 수
     */
    int updateCustomCodeMst(CustomCodeMstDto.Info param);

    /**
     * 회사지정코드 수정(update)
     *
     * @param param 저장 할 회사지정코드 객체
     * @return 적용된 행 수
     */
    int updateCustomCode(CustomCodeDto.Info param);

    /**
     * 회사지정코드마스터 삭제
     *
     * @param customMstSeq 회사지정코드마스터 pk
     * @return 적용된 행 수
     */
    int deleteCustomCodeMst(int customMstSeq);

    /**
     * 회사지정코드 삭제
     *
     * @param customSeq 회사지정코드 pk
     * @return 적용된 행 수
     */
    int deleteCustomCode(int customSeq);
    int deleteCustomCodeLv2(int customSeq);
	int deleteCustomCodeLv3(int customSeq);

    /**
     * 회사지정코드 중복삭제
     *
     * @param param 회사지정코드 객체
     * @return 적용된 행 수
     */
    int deleteCustomCodeDup(CustomCodeDto.Info param);

    /**
     * 회사지정코드 중복조회
     *
     * @param param 회사지정코드 객체
     * @return 조회된 행 수
     */
    int getCustomCodeDup(CustomCodeDto.Info param);

    /**
     * 회사지정코드 Key 부여
     *
     * @param customSeq 회사지정코드 pk
     * @return 적용된 행 수
     */
	int updateCustomCodeMstCode(int customSeq);

    /**
     * 회사지정코드 전체삭제
     *
     * @param param 회사지정코드 객체
     * @return 적용된 행 수
     */
	int deleteCustomCodeAll(CustomCodeDto.Info info);


}
