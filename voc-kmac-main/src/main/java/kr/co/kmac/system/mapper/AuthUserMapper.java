package kr.co.kmac.system.mapper;

import kr.co.kmac.system.dto.AuthUserDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 권한사용자매핑정보 MyBatis Mapper 클래스
 * 
 * @ClassName AuthUserUserMapper.java
 * @Description 권한사용자매핑정보 Mapper 클래스
 * @author mjkim
 * @since 2023. 11. 20.
 *
 */
@Repository
@Mapper
public interface AuthUserMapper
{
    /**
     * 권한사용자매핑정보 목록 조회
     * 
     * @param param 권한사용자매핑정보 검색 조건 포함 객체
     */
    List<AuthUserDto.Info> getAuthUserList(AuthUserDto.Request param);

    /**
     * 권한사용자매핑정보 목록 개수 조회
     * 
     * @param param 권한사용자매핑정보 검색 조건 포함 객체
     * @return 조건에 해당되는 전체 행 수
     */
    long getAuthUserListCount(AuthUserDto.Request param);


    /**
     * 권한사용자매핑정보 입력(insert)
     * 
     * @param param 저장 할 권한사용자매핑정보 객체
     * @return 적용된 행 수
     */
    int insertAuthUser(AuthUserDto.Info param);


    /**
     * 권한사용자매핑정보 삭제
     * 
     * @param param 삭제할 권한사용자매핑정보 객체
     * @return 적용된 행 수
     */
    int deleteAuthUser(AuthUserDto.Info param);
}
