package kr.co.kmac.system.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.kmac.common.util.MessageUtil;
import kr.co.kmac.coreframework.common.exception.ApiBusinessException;
import kr.co.kmac.coreframework.service.BaseService;
import kr.co.kmac.system.dto.AuthUserDto;
import kr.co.kmac.system.dto.CodeDto;
import kr.co.kmac.system.dto.UserDto;
import kr.co.kmac.system.mapper.AuthUserMapper;
import kr.co.kmac.system.mapper.UserMapper;

/**
 * 사용자 서비스 클래스
 *
 * @ClassName UserService.java
 * @Description 사용자 서비스 클래스
 * @author mjkim
 * @since 2023. 9. 18.
 */
@Service
public class UserService extends BaseService
{
    // 사용자 조작 위한 DAO 객체
    @Autowired
    private UserMapper mapper;

    @Autowired
    private AuthUserMapper authUserMapper;

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
     * 사용자 목록 조회
     *
     * @param param 사용자 검색 조건 포함 객체
     * @return 결과 행 및 페이지 정보를 포함한 PaginatedResponse 객체
     */
    public UserDto.ListResponse getUserList(UserDto.Request param)
    {
        long total = mapper.getUserListCount(param);
        return UserDto.ListResponse
                .<UserDto.Info>builder()
                .totalCount(total)
                .list(mapper.getUserList(param))
                .build();
	}

    /**
     * 사용자 상세 조회
     *
     * @param userSeq 사용자 pk
     * @return 사용자 객체
     */
    public UserDto.Info getUser(int userSeq)
    {
        return mapper.getUser(userSeq);
    }

    /**
     * 처리자 목록[dropdown용] 조회
     *
     * @param companyCd 회사코드
     * @return 결과 행 정보를 포함한 List 객체
     */
    public List<CodeDto.CommonCode> getActUserList(String companyCd)
    {
        return mapper.getActUserList(companyCd);
    }

    /**
     * 사용자 등록(insert)
     *
     * @param param 등록할 사용자 객체
     * @return 적용된 행 수
     */
    @Transactional
    public UserDto.Response insertUser(UserDto.Info param) {
        UserDto.Response res = UserDto.Response.builder().build();

        // 중복체크
        param.setUserIdForCheck(param.getUserId());
        long total = mapper.getUserDupCheck(param);
        if(total > 0) {
            throw new ApiBusinessException("error.1025","중복된 사용자 ID가 존재합니다.");
        }

        res.setRtnCnt(mapper.insertUser(param));
        res.setRtnMessage(MessageUtil.getMessage("common.insert.0000"));

        // 권한사용자 등록
        String[] userAuthCodes = param.getUserAuthCodes().split(",");
        AuthUserDto.Info authUserDto = AuthUserDto.Info.builder().build();
        authUserDto.setUserSeq(param.getReturnKey());

        if(StringUtils.isBlank(param.getCompanyCd())) {
        	authUserDto.setCompanyCd(param.getLoginCompanyCd());
        }else {
        	authUserDto.setCompanyCd(param.getCompanyCd());
        }

        if(!StringUtils.isBlank(param.getUserAuthCodes())) {
        	for(String authSeq : userAuthCodes) {
        		authUserDto.setAuthSeq(Integer.parseInt(authSeq));
        		authUserMapper.insertAuthUser(authUserDto);
        	}
        }else {
        	authUserDto.setAuthSeq(0);
    		authUserMapper.insertAuthUser(authUserDto);
        }

        return res;
    }

    /**
     * 사용자 수정(update)
     *
     * @param param 저장할 사용자 객체
     * @return 적용된 행 수
     */
    @Transactional
    public UserDto.Response updateUser(UserDto.Info param) {
        UserDto.Response res = UserDto.Response.builder().build();

        res.setRtnCnt(mapper.updateUser(param));
        res.setRtnMessage(MessageUtil.getMessage("common.update.0000"));

        // 권한사용자 등록
        String[] userAuthCodes = param.getUserAuthCodes().split(",");
        AuthUserDto.Info authUserDto = AuthUserDto.Info.builder().build();
        authUserDto.setUserSeq(param.getUserSeq());
        if(!StringUtils.isBlank(param.getCompanyCd())) {
        	authUserDto.setCompanyCd(param.getCompanyCd());
        }else {
        	authUserDto.setCompanyCd(param.getLoginCompanyCd());
        }
        authUserMapper.deleteAuthUser(authUserDto);

        if(!StringUtils.isBlank(param.getUserAuthCodes())) {
        	for(String authSeq : userAuthCodes) {
        		if(StringUtils.isEmpty(authSeq)) continue;
                authUserDto.setAuthSeq(Integer.parseInt(authSeq));
                authUserMapper.insertAuthUser(authUserDto);
        	}
        }

        return res;
    }

    /**
     * 사용자 비밀번호 수정(update)
     *
     * @param param 저장할 사용자 객체
     * @return 적용된 행 수
     */
    @Transactional
    public UserDto.Response updateUserPw(UserDto.Info param) {
        UserDto.Response res = UserDto.Response.builder().build();

        res.setRtnCnt(mapper.updateUserPw(param));
        res.setRtnMessage(MessageUtil.getMessage("common.update.0000"));
        return res;
    }

    /**
     * 사용자 삭제
     *
     * @param userSeq 사용자 pk
     * @return 적용된 행 수
     */
    @Transactional
    public UserDto.Response deleteUser(int userSeq) {
        UserDto.Response res = UserDto.Response.builder().build();

        //사용자권한 삭제
        AuthUserDto.Info authUserDto = AuthUserDto.Info.builder().build();
        authUserDto.setUserSeq(userSeq);
        authUserMapper.deleteAuthUser(authUserDto);

        res.setRtnCnt(mapper.deleteUser(userSeq));
        res.setRtnMessage(MessageUtil.getMessage("common.delete.0000"));
        return res;
    }

    /**
     * 사용자 중복체크
     *
     * @param userIdForCheck 사용자ID
     * @return 건수
     */
    public UserDto.Response userIdForCheck(UserDto.Info param){

    	UserDto.Response res = UserDto.Response.builder().build();

        res.setRtnCnt(mapper.getUserDupCheck(param));
        res.setRtnMessage("");
        return res;
    }
}
