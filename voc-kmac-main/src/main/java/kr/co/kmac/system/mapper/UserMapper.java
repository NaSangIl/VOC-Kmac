package kr.co.kmac.system.mapper;

import kr.co.kmac.system.dto.CodeDto;
import kr.co.kmac.system.dto.UserDto;
import kr.co.kmac.system.dto.UserDto.Request;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 사용자 MyBatis Mapper 클래스
 *
 * @ClassName UserMapper.java
 * @Description 사용자 Mapper 클래스
 * @author mjkim
 * @since 2023. 9. 18.
 *
 */
@Repository
@Mapper
public interface UserMapper
{
    /**
    /**
     * 사용자 목록 조회
     *
     * @param param 사용자 검색 조건 포함 객체
     */
    List<UserDto.Info> getUserList(UserDto.Request param);

    /**
     * 사용자 목록 개수 조회
     *
     * @param param 사용자 검색 조건 포함 객체
     * @return 조건에 해당되는 전체 행 수
     */
    long getUserListCount(UserDto.Request param);

    /**
     * 사용자 상세 조회
     *
     * @param userSeq 사용자 pk
     * @return 사용자 객체
     */
    UserDto.Info getUser(int userSeq);

    /**
     * 사용자 아이디 체크
     *
     * @param param
     * @return 로그인 객체
     */
    int getIdCheck(UserDto.LoginRequest param);

    /**
     * 사용자 아이디, 비밀번호 체크
     *
     * @param param
     * @return 로그인 객체
     */
    int getIdPwdCheck(UserDto.LoginRequest param);

    /**
     * 로그인정보 조회
     *
     * @param param
     * @return 사용자 객체
     */
    UserDto.Info getLogin(UserDto.LoginRequest param);

    /**
     * 처리자 목록[dropdown용] 조회
     *
     * @param companyCd
     * @return 공통코드 객체
     */
    List<CodeDto.CommonCode> getActUserList(String companyCd);

    /**
     * 사용자 입력(insert)
     *
     * @param param 저장 할 사용자 객체
     * @return 적용된 행 수
     */
    int insertUser(UserDto.Info param);

    /**
     * 사용자 수정(update)
     *
     * @param param 저장 할 사용자 객체
     * @return 적용된 행 수
     */
    int updateUser(UserDto.Info param);

    /**
     * 사용자 비밀번호 수정(update)
     *
     * @param param 저장 할 사용자 객체
     * @return 적용된 행 수
     */
    int updateUserPw(UserDto.Info param);

    /**
     * 사용자 삭제
     *
     * @param userSeq 사용자 pk
     * @return 적용된 행 수
     */
    int deleteUser(int userSeq);

    /**
     * 사용자 중복체크
     *
     * @param getUserDupCheck 사용자 id
     * @return 적용된 행 수
     */
    int getUserDupCheck(UserDto.Info param);
}
