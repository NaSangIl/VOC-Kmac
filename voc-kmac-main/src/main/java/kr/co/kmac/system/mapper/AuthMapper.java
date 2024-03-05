package kr.co.kmac.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.kmac.system.dto.AuthDto;

/**
 * 권한 MyBatis Mapper 클래스
 *
 * @ClassName AuthMapper.java
 * @Description 권한 Mapper 클래스
 * @author mjkim
 * @since 2023. 9. 18.
 *
 */
@Repository
@Mapper
public interface AuthMapper
{
    /**
    /**
     * 권한 목록 조회
     *
     * @param param 권한 검색 조건 포함 객체
     */
    List<AuthDto.Info> getAuthList(AuthDto.Request param);

    /**
     * 권한 목록 개수 조회
     *
     * @param param 권한 검색 조건 포함 객체
     * @return 조건에 해당되는 전체 행 수
     */
    long getAuthListCount(AuthDto.Request param);

    /**
     * 권한 상세 조회
     *
     * @param authSeq 권한 pk
     * @return 권한 객체
     */
    AuthDto.Info getAuth(int authSeq);

    /**
     * 권한 입력(insert)
     *
     * @param param 저장 할 권한 객체
     * @return 적용된 행 수
     */
    int insertAuth(AuthDto.Info param);

    /**
     * 권한 수정(update)
     *
     * @param param 저장 할 권한 객체
     * @return 적용된 행 수
     */
    int updateAuth(AuthDto.Info param);

    /**
     * 권한 삭제
     *
     * @param authSeq 권한 pk
     * @return 적용된 행 수
     */
    int deleteAuth(int authSeq);

    /**
     * 권한 중복체크
     *
     * @param authCd 권한 코드
     * @return 권한 객체
     */
    long getAuthCheck(AuthDto.Info param);
}
