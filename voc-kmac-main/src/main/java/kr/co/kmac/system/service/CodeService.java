package kr.co.kmac.system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.kmac.common.util.MessageUtil;
import kr.co.kmac.coreframework.service.BaseService;
import kr.co.kmac.system.dto.CodeDto;
import kr.co.kmac.system.mapper.CodeMapper;

/**
 * 코드 서비스 클래스
 *
 * @ClassName CodeService.java
 * @Description 코드 서비스 클래스
 * @author mjkim
 * @since 2023. 9. 18.
 */
@Service
public class CodeService extends BaseService
{
    // 코드 조작 위한 DAO 객체
    @Autowired
    private CodeMapper mapper;

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
     * 코드 목록 조회
     *
     * @param param 코드 검색 조건 포함 객체
     * @return 결과 행 및 페이지 정보를 포함한 PaginatedResponse 객체
     */
    public CodeDto.ListResponse getCodeList(CodeDto.Request param)
    {
        long total = mapper.getCodeListCount(param);
        return CodeDto.ListResponse
                .<CodeDto.Info>builder()
                .totalCount(total)
                .list(mapper.getCodeList(param))
                .build();
	}

    /**
     * 코드 목록[dropdown용] 조회
     *
     * @param param dropdown용 코드 조건 포함 객체
     * @return 결과 행 및 페이지 정보를 포함한 PaginatedResponse 객체
     */
    public List<CodeDto.CommonCode> getCommonCodeList(CodeDto.Request param)
    {
        return mapper.getCommonCodeList(param);
    }

    /**
     * 코드 상세 조회
     *
     * @param codeSeq 코드 pk
     * @return 코드 객체
     */
    public CodeDto.Info getCode(int codeSeq)
    {
        return mapper.getCode(codeSeq);
    }

    /**
     * 코드 등록(insert)
     *
     * @param param 등록할 코드 객체
     * @return 적용된 행 수
     */
    @Transactional
    public CodeDto.Response insertCode(CodeDto.Info param) {
        CodeDto.Response res = CodeDto.Response.builder().build();

        res.setRtnCnt(mapper.insertCode(param));
        res.setRtnMessage(MessageUtil.getMessage("common.insert.0000"));
        return res;
    }

    /**
     * 코드 수정(update)
     *
     * @param param 저장할 코드 객체
     * @return 적용된 행 수
     */
    @Transactional
    public CodeDto.Response updateCode(CodeDto.Info param) {
        CodeDto.Response res = CodeDto.Response.builder().build();

        res.setRtnCnt(mapper.updateCode(param));
        res.setRtnMessage(MessageUtil.getMessage("common.update.0000"));
        return res;
    }


    /**
     * 코드 삭제
     *
     * @param codeSeq 삭제 할 key
     * @return 적용된 행 수
     */
    @Transactional
    public CodeDto.Response deleteCode(int codeSeq) {
        CodeDto.Response res = CodeDto.Response.builder().build();

        res.setRtnCnt(mapper.deleteCode(codeSeq));
        res.setRtnMessage(MessageUtil.getMessage("common.delete.0000"));
        return res;
    }

}
