package kr.co.kmac.system.mapper;

import kr.co.kmac.system.dto.LoginLogDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 로그인로그 MyBatis Mapper 클래스
 * 
 * @ClassName LoginLogMapper.java
 * @Description 로그인로그 Mapper 클래스
 * @author mjkim
 * @since 2023. 9. 18.
 *
 */
@Repository
@Mapper
public interface LoginLogMapper
{
    /**
    /**
     * 로그인로그 목록 조회
     * 
     * @param param 로그인로그 검색 조건 포함 객체
     */
    List<LoginLogDto.Info> getLoginLogList(LoginLogDto.Request param);

    /**
     * 로그인로그 목록 개수 조회
     * 
     * @param param 로그인로그 검색 조건 포함 객체
     * @return 조건에 해당되는 전체 행 수
     */
    long getLoginLogListCount(LoginLogDto.Request param);

    /**
     * 로그인로그 상세 조회
     *
     * @param loginLogSeq 로그인로그 pk
     * @return 로그인로그 객체
     */
    LoginLogDto.Info getLoginLog(int loginLogSeq);

    /**
     * 로그인로그 입력(insert)
     * 
     * @param param 저장 할 로그인로그 객체
     * @return 적용된 행 수
     */
    int insertLoginLog(LoginLogDto.Info param);

    /**
     * 로그인로그 수정(update)
     *
     * @param param 저장 할 로그인로그 객체
     * @return 적용된 행 수
     */
    int updateLoginLog(LoginLogDto.Info param);
}
