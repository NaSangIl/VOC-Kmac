package kr.co.kmac.system.service;

import kr.co.kmac.common.util.MessageUtil;
import kr.co.kmac.coreframework.service.BaseService;
import kr.co.kmac.system.dto.CompanyDto;
import kr.co.kmac.system.mapper.CompanyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 회사 서비스 클래스
 *
 * @ClassName CompanyService.java
 * @Description 회사 서비스 클래스
 * @author mjkim
 * @since 2023. 9. 18.
 */
@Service
public class CompanyService extends BaseService
{
    // 회사 조작 위한 DAO 객체
    @Autowired
    private CompanyMapper mapper;

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
     * 회사 목록 조회
     *
     * @param param 회사 검색 조건 포함 객체
     * @return 결과 행 및 페이지 정보를 포함한 PaginatedResponse 객체
     */
    public CompanyDto.ListResponse getCompanyList(CompanyDto.Request param)
    {
        long total = mapper.getCompanyListCount(param);
        return CompanyDto.ListResponse
                .<CompanyDto.Info>builder()
                .totalCount(total)
                .list(mapper.getCompanyList(param))
                .build();
	}

    /**
     * 회사 상세 조회
     *
     * @param companySeq 회사 pk
     * @return 회사 객체
     */
    public CompanyDto.Info getCompany(int companySeq)
    {
        return mapper.getCompany(companySeq);
    }

    /**
     * 회사 등록(insert)
     *
     * @param param 등록할 회사 객체
     * @return 적용된 행 수
     */
    @Transactional
    public CompanyDto.Response insertCompany(CompanyDto.Info param) {
        CompanyDto.Response res = CompanyDto.Response.builder().build();

        res.setRtnCnt(mapper.insertCompany(param));
        res.setRtnKey(param.getReturnKey());
        mapper.updateCompanyCode(param.getReturnKey());
        res.setRtnMessage(MessageUtil.getMessage("common.insert.0000"));
        return res;
    }

    /**
     * 회사 수정(update)
     *
     * @param param 저장할 회사 객체
     * @return 적용된 행 수
     */
    @Transactional
    public CompanyDto.Response updateCompany(CompanyDto.Info param) {
        CompanyDto.Response res = CompanyDto.Response.builder().build();

        res.setRtnCnt(mapper.updateCompany(param));
        res.setRtnMessage(MessageUtil.getMessage("common.update.0000"));
        return res;
    }


    /**
     * 회사 삭제
     *
     * @param companySeq 회사 pk
     * @return 적용된 행 수
     */
    @Transactional
    public CompanyDto.Response deleteCompany(int companySeq) {
        CompanyDto.Response res = CompanyDto.Response.builder().build();

        res.setRtnCnt(mapper.deleteCompany(companySeq));
        res.setRtnMessage(MessageUtil.getMessage("common.delete.0000"));
        return res;
    }

}
