package kr.co.kmac.system.service;

import kr.co.kmac.common.util.MessageUtil;
import kr.co.kmac.coreframework.service.BaseService;
import kr.co.kmac.system.dto.CustomCodeDto;
import kr.co.kmac.system.mapper.CompanyCustomCodeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 회사별 지정코드 서비스 클래스
 * 
 * @ClassName CompanyCustomCodeService.java
 * @Description 회사별 지정코드 서비스 클래스
 * @vocTypeor mjkim
 * @since 2023. 9. 18.
 */
@Service
public class CompanyCustomCodeService extends BaseService
{
    // 회사별 지정코드 조작 위한 DAO 객체
    @Autowired
    private CompanyCustomCodeMapper mapper;

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
     * 회사별 지정코드 목록 조회
     *
     * @param param 회사별 지정코드 검색 조건 포함 객체
     * @return 결과 행 및 페이지 정보를 포함한 PaginatedResponse 객체
     */
    public CustomCodeDto.ListResponse getCompanyCustomCodeList(CustomCodeDto.Request param)
    {
        long total = mapper.getCompanyCustomCodeListCount(param);
        return CustomCodeDto.ListResponse
                .<CustomCodeDto.Info>builder()
                .totalCount(total)
                .list(mapper.getCompanyCustomCodeList(param))
                .build();
    }

    /**
     * 회사별 지정코드 dropbox용 목록 조회
     *
     * @param param 회사별 지정코드 검색 조건 포함 객체
     * @return 결과 행 및 페이지 정보를 포함한 PaginatedResponse 객체
     */
    public CustomCodeDto.ListResponse getCompanyCustomSelectList(CustomCodeDto.Request param)
    {
        return CustomCodeDto.ListResponse
                .<CustomCodeDto.Info>builder()
                .list(mapper.getCompanyCustomSelectList(param))
                .build();
    }

    /**
     * 회사별 지정코드 상세 조회
     *
     * @param customSeq 회사별 지정코드 pk
     * @return 회사별 지정코드 객체
     */
    public CustomCodeDto.Info getCompanyCustomCode(int customSeq)
    {
        return mapper.getCompanyCustomCode(customSeq);
    }

    /**
     * 회사별 지정코드 등록(insert)
     *
     * @param param 등록할 회사별 지정코드 객체
     * @return 적용된 행 수
     */
    @Transactional
    public CustomCodeDto.Response insertCompanyCustomCode(CustomCodeDto.Info param) {
        CustomCodeDto.Response res = CustomCodeDto.Response.builder().build();

        res.setRtnCnt(mapper.insertCompanyCustomCode(param));
        res.setRtnMessage(MessageUtil.getMessage("common.insert.0000"));
        return res;
    }

    /**
     * 회사별 지정코드 수정(update)
     *
     * @param param 저장할 회사별 지정코드 객체
     * @return 적용된 행 수
     */
    @Transactional
    public CustomCodeDto.Response updateCompanyCustomCode(CustomCodeDto.Info param) {
        CustomCodeDto.Response res = CustomCodeDto.Response.builder().build();

        res.setRtnCnt(mapper.updateCompanyCustomCode(param));
        res.setRtnMessage(MessageUtil.getMessage("common.update.0000"));
        return res;
    }

    /**
     * 회사별 지정코드 삭제
     *
     * @param customSeq 회사별 지정코드 pk
     * @return 적용된 행 수
     */
    @Transactional
    public CustomCodeDto.Response deleteCompanyCustomCode(int customSeq) {
        CustomCodeDto.Response res = CustomCodeDto.Response.builder().build();

        int rtnCnt = 0;
        rtnCnt += mapper.deleteCompanyCustomCodeLv3(customSeq); //연결된 3레벨 삭제
        rtnCnt += mapper.deleteCompanyCustomCodeLv2(customSeq); //연결된 2레벨 삭제
        rtnCnt += mapper.deleteCompanyCustomCode(customSeq); //연결된 1레벨 삭제
        res.setRtnCnt(rtnCnt);
        res.setRtnMessage(MessageUtil.getMessage("common.delete.0000"));
        return res;
    }

    /**
     * 회사별 지정코드 복사(insert)
     *
     * @param param 복사할 회사 객체
     * @return 적용된 행 수
     */
    @Transactional
    public CustomCodeDto.Response copyCompanyCustomCode(CustomCodeDto.Info param) {
        CustomCodeDto.Response res = CustomCodeDto.Response.builder().build();

        //동일유형그룹 삭제 후 복사
        mapper.deleteAllCompanyCustomCode(param);

        res.setRtnCnt(mapper.copyCompanyCustomCode(param));
        res.setRtnMessage(MessageUtil.getMessage("common.insert.0000"));
        return res;
    }
}
