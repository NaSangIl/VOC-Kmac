package kr.co.kmac.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.kmac.common.util.MessageUtil;
import kr.co.kmac.coreframework.service.BaseService;
import kr.co.kmac.system.dto.AuthUserDto;
import kr.co.kmac.system.mapper.AuthUserMapper;

/**
 * 권한사용자매핑정보 서비스 클래스
 *
 * @ClassName AuthUserService.java
 * @Description 권한사용자매핑정보 서비스 클래스
 * @author mjkim
 * @since 2023. 11. 20.
 */
@Service
public class AuthUserService extends BaseService
{
    // 권한사용자매핑정보 조작 위한 DAO 객체
    @Autowired
    private AuthUserMapper mapper;

    // 메시지 소스
    @Autowired
    private MessageSource messageSource;

    /**
     * 메시지 소스 반환
     *
     * @return 메시지 소스 객체
     */
    protected MessageSource getMessageSource()
    {
        return this.messageSource;
    }

    /**
     * 권한사용자매핑정보 목록 조회
     *
     * @param param 권한사용자매핑정보 검색 조건 포함 객체
     * @return 결과 행 및 페이지 정보를 포함한 PaginatedResponse 객체
     */
    public AuthUserDto.ListResponse getAuthUserList(AuthUserDto.Request param)
    {
        long total = mapper.getAuthUserListCount(param);
        return AuthUserDto.ListResponse
                .<AuthUserDto.Info>builder()
                .totalCount(total)
				.list(mapper.getAuthUserList(param))
                .build();
	}

    /**
     * 권한사용자매핑정보 등록(insert)
     *
     * @param param 등록할 권한사용자매핑정보 객체
     * @return 적용된 행 수
     */
    @Transactional
    public AuthUserDto.Response insertAuthUser(AuthUserDto.Info param) {
        AuthUserDto.Response res = AuthUserDto.Response.builder().build();

        //중복오류 방지를 위해 삭제 후 insert
        mapper.deleteAuthUser(param);

        res.setRtnCnt(mapper.insertAuthUser(param));
        res.setRtnMessage(MessageUtil.getMessage("common.insert.0000"));
        return res;
    }

	/**
     * 권한사용자매핑정보 삭제
     *
     * @param param 삭제할 권한사용자매핑정보 객체
     * @return 적용된 행 수
     */
    @Transactional
    public AuthUserDto.Response deleteAuthUser(AuthUserDto.Info param) {
        AuthUserDto.Response res = AuthUserDto.Response.builder().build();

        res.setRtnCnt(mapper.deleteAuthUser(param));
        res.setRtnMessage(MessageUtil.getMessage("common.delete.0000"));
        return res;
    }

}
