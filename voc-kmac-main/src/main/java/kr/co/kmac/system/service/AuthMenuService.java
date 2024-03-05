package kr.co.kmac.system.service;

import kr.co.kmac.common.util.MessageUtil;
import kr.co.kmac.coreframework.service.BaseService;
import kr.co.kmac.system.dto.AuthMenuDto;
import kr.co.kmac.system.mapper.AuthMenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 권한메뉴매핑정보 서비스 클래스
 * 
 * @ClassName AuthMenuService.java
 * @Description 권한메뉴매핑정보 서비스 클래스
 * @author mjkim
 * @since 2023. 11. 20.
 */
@Service
public class AuthMenuService extends BaseService
{
    // 권한메뉴매핑정보 조작 위한 DAO 객체
    @Autowired
    private AuthMenuMapper mapper;

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
     * 권한메뉴매핑정보 목록 조회
     * 
     * @param param 권한메뉴매핑정보 검색 조건 포함 객체
     * @return 결과 행 및 페이지 정보를 포함한 PaginatedResponse 객체
     */
    public AuthMenuDto.ListResponse getAuthMenuList(AuthMenuDto.Request param)
    {
        return AuthMenuDto.ListResponse
                .<AuthMenuDto.Info>builder()
                .list(mapper.getAuthMenuList(param))
                .build();
	}
    
    /**
     * 권한메뉴매핑정보 등록(insert)
     * 
     * @param param 등록할 권한메뉴매핑정보 객체
     * @return 적용된 행 수
     */
    @Transactional
    public AuthMenuDto.Response insertAuthMenu(AuthMenuDto.Info param) {
        AuthMenuDto.Response res = AuthMenuDto.Response.builder().build();
        int rtnCnt = 0;

        //중복오류 방지를 위해 삭제 후 insert
        mapper.deleteAuthMenu(param);

        for(int menuSeq : param.getMenuSeqs()) {
            param.setMenuSeq(menuSeq);
            rtnCnt += mapper.insertAuthMenu(param);
        }
        res.setRtnCnt(rtnCnt);
        res.setRtnMessage(MessageUtil.getMessage("common.insert.0000"));
        return res;
    }

	/**
     * 권한메뉴매핑정보 삭제
     * 
     * @param param 삭제할 권한메뉴매핑정보 객체
     * @return 적용된 행 수
     */
    @Transactional
    public AuthMenuDto.Response deleteAuthMenu(AuthMenuDto.Info param) {
        AuthMenuDto.Response res = AuthMenuDto.Response.builder().build();

        res.setRtnCnt(mapper.deleteAuthMenu(param));
        res.setRtnMessage(MessageUtil.getMessage("common.delete.0000"));
        return res;
    }

}
