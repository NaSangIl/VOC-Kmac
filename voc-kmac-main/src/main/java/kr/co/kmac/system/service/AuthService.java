package kr.co.kmac.system.service;

import kr.co.kmac.common.util.MessageUtil;
import kr.co.kmac.coreframework.common.exception.ApiBusinessException;
import kr.co.kmac.coreframework.service.BaseService;
import kr.co.kmac.system.dto.AuthDto;
import kr.co.kmac.system.mapper.AuthMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 권한 서비스 클래스
 *
 * @ClassName AuthService.java
 * @Description 권한 서비스 클래스
 * @author mjkim
 * @since 2023. 9. 18.
 */
@Service
public class AuthService extends BaseService
{
    // 권한 조작 위한 DAO 객체
    @Autowired
    private AuthMapper mapper;

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
     * 권한 목록 조회
     *
     * @param param 권한 검색 조건 포함 객체
     * @return 결과 행 및 페이지 정보를 포함한 PaginatedResponse 객체
     */
    public AuthDto.ListResponse getAuthList(AuthDto.Request param)
    {
        long total = mapper.getAuthListCount(param);
        return AuthDto.ListResponse
                .<AuthDto.Info>builder()
                .totalCount(total)
                .list(mapper.getAuthList(param))
                .build();
	}

    /**
     * 권한 상세 조회
     *
     * @param authSeq 권한 pk
     * @return 권한 객체
     */
    public AuthDto.Info getAuth(int authSeq)
    {
        return mapper.getAuth(authSeq);
    }

    /**
     * 권한 등록(insert)
     *
     * @param param 등록할 권한 객체
     * @return 적용된 행 수
     */
    @Transactional
    public AuthDto.Response insertAuth(AuthDto.Info param) {
        AuthDto.Response res = AuthDto.Response.builder().build();

        // 중복체크
        long authInfoCnt = mapper.getAuthCheck(param);
        if(authInfoCnt > 0) {
            throw new ApiBusinessException("error.1025","중복된 권한코드가 존재합니다.");
        }

        res.setRtnCnt(mapper.insertAuth(param));
        res.setRtnMessage(MessageUtil.getMessage("common.insert.0000"));
        return res;
    }

    /**
     * 권한 수정(update)
     *
     * @param param 저장할 권한 객체
     * @return 적용된 행 수
     */
    @Transactional
    public AuthDto.Response updateAuth(AuthDto.Info param) {
        AuthDto.Response res = AuthDto.Response.builder().build();

        res.setRtnCnt(mapper.updateAuth(param));
        res.setRtnMessage(MessageUtil.getMessage("common.update.0000"));
        return res;
    }


    /**
     * 권한 삭제
     *
     * @param authSeq 권한 pk
     * @return 적용된 행 수
     */
    @Transactional
    public AuthDto.Response deleteAuth(int authSeq) {
        AuthDto.Response res = AuthDto.Response.builder().build();

        res.setRtnCnt(mapper.deleteAuth(authSeq));
        res.setRtnMessage(MessageUtil.getMessage("common.delete.0000"));
        return res;
    }

}
