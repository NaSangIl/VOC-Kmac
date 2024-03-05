package kr.co.kmac.voc.service;

import kr.co.kmac.common.util.MessageUtil;
import kr.co.kmac.coreframework.service.BaseService;
import kr.co.kmac.voc.dto.VocCustDto;
import kr.co.kmac.voc.mapper.VocCustMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * VOC고객 서비스 클래스
 *
 * @ClassName VocCustService.java
 * @Description VOC고객 서비스 클래스
 * @author mjkim
 * @since 2023. 9. 18.
 */
@Service
public class VocCustService extends BaseService
{
    // VOC고객 조작 위한 DAO 객체
    @Autowired
    private VocCustMapper mapper;

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
     * VOC고객 목록 조회
     *
     * @param param VOC고객 검색 조건 포함 객체
     * @return 결과 행 및 페이지 정보를 포함한 PaginatedResponse 객체
     */
    public VocCustDto.ListResponse getVocCustList(VocCustDto.Request param)
    {


        long total = mapper.getVocCustListCount(param);
        return VocCustDto.ListResponse
                .<VocCustDto.Info>builder()
                .totalCount(total)
                .list(mapper.getVocCustList(param))
                .build();
	}

    /**
     * VOC고객 상세 조회
     *
     * @param custSeq VOC고객 pk
     * @return VOC고객 객체
     */
    public VocCustDto.Info getVocCust(int custSeq)
    {
        return mapper.getVocCust(custSeq);
    }

    /**
     * VOC고객 등록(insert)
     *
     * @param param 등록할 VOC고객 객체
     * @return 적용된 행 수
     */
    @Transactional
    public VocCustDto.Response insertVocCust(VocCustDto.Info param) {
        VocCustDto.Response res = VocCustDto.Response.builder().build();

        res.setRtnCnt(mapper.insertVocCust(param));
        res.setRtnMessage(MessageUtil.getMessage("cust.insert.0000"));
        return res;
    }

    /**
     * VOC고객 수정(update)
     *
     * @param param 저장할 VOC고객 객체
     * @return 적용된 행 수
     */
    @Transactional
    public VocCustDto.Response updateVocCust(VocCustDto.Info param) {
        VocCustDto.Response res = VocCustDto.Response.builder().build();

        res.setRtnCnt(mapper.updateVocCust(param));
        res.setRtnMessage(MessageUtil.getMessage("cust.update.0000"));
        return res;
    }


    /**
     * VOC고객 삭제
     *
     * @param custSeq 삭제 할 조건객체
     * @return 적용된 행 수
     */
    @Transactional
    public VocCustDto.Response deleteVocCust(int custSeq) {
        VocCustDto.Response res = VocCustDto.Response.builder().build();

        res.setRtnCnt(mapper.deleteVocCust(custSeq));
        res.setRtnMessage(MessageUtil.getMessage("cust.delete.0000"));
        return res;
    }

}
