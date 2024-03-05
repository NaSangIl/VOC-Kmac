package kr.co.kmac.system.service;

import kr.co.kmac.common.util.MessageUtil;
import kr.co.kmac.coreframework.service.BaseService;
import kr.co.kmac.system.dto.LoginLogDto;
import kr.co.kmac.system.mapper.LoginLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 로그인로그서비스 클래스
 * 
 * @ClassName LoginLogService.java
 * @Description 로그인로그서비스 클래스
 * @author mjkim
 * @since 2023. 9. 18.
 */
@Service
public class LoginLogService extends BaseService
{
    // 로그인로그조작 위한 DAO 객체
    @Autowired
    private LoginLogMapper mapper;

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
     * 로그인로그목록 조회
     * 
     * @param param 로그인로그검색 조건 포함 객체
     * @return 결과 행 및 페이지 정보를 포함한 PaginatedResponse 객체
     */
    public LoginLogDto.ListResponse getLoginLogList(LoginLogDto.Request param)
    {
        long total = mapper.getLoginLogListCount(param);
        return LoginLogDto.ListResponse
                .<LoginLogDto.Info>builder()
                .totalCount(total)
                .list(mapper.getLoginLogList(param))
                .build();
	}

    /**
     * 로그인로그상세 조회
     * 
     * @param loginLogSeq 로그인로그pk
     * @return 로그인로그객체
     */
    public LoginLogDto.Info getLoginLog(int loginLogSeq)
    {
        return mapper.getLoginLog(loginLogSeq);
    }
    
    /**
     * 로그인로그등록(insert)
     * 
     * @param param 등록할 로그인로그객체
     * @return 적용된 행 수
     */
    @Transactional
    public LoginLogDto.Response insertLoginLog(LoginLogDto.Info param) {
        LoginLogDto.Response res = LoginLogDto.Response.builder().build();

        res.setRtnCnt(mapper.insertLoginLog(param));
        res.setRtnMessage(MessageUtil.getMessage("common.insert.0000"));
        return res;
    }

    /**
     * 로그인로그수정(update)
     *
     * @param param 저장할 로그인로그객체
     * @return 적용된 행 수
     */
    @Transactional
    public LoginLogDto.Response updateLoginLog(LoginLogDto.Info param) {
        LoginLogDto.Response res = LoginLogDto.Response.builder().build();

        res.setRtnCnt(mapper.updateLoginLog(param));
        res.setRtnMessage(MessageUtil.getMessage("common.logout.0000"));
        return res;
    }
}
