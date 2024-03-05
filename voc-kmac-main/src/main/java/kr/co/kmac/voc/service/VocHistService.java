package kr.co.kmac.voc.service;

import kr.co.kmac.coreframework.service.BaseService;
import kr.co.kmac.voc.dto.VocHistDto;
import kr.co.kmac.voc.dto.VocMstDto;
import kr.co.kmac.voc.mapper.VocHistMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * VOC이력 서비스 클래스
 * 
 * @ClassName VocHistService.java
 * @Description VOC이력 서비스 클래스
 * @author mjkim
 * @since 2023. 9. 18.
 */
@Service
public class VocHistService extends BaseService
{
    // VOC이력 조작 위한 DAO 객체
    @Autowired
    private VocHistMapper mapper;

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
     * VOC이력 목록 조회
     * 
     * @param vocSeq VOC이력 검색 조건 포함 객체
     * @return 결과 행 및 페이지 정보를 포함한 PaginatedResponse 객체
     */
    public VocHistDto.ListResponse getVocHistList(int vocSeq)
    {
        List<VocHistDto.Info> list = mapper.getVocHistList(vocSeq);
        return VocHistDto.ListResponse
                .<VocHistDto.Info>builder()
                .totalCount(list.size())
                .list(list)
                .build();
	}

    /**
     * VOC이력 상세 조회
     * 
     * @param custSeq VOC이력 pk
     * @return VOC이력 객체
     */
    public VocHistDto.Info getVocHist(int custSeq)
    {
        return mapper.getVocHist(custSeq);
    }
    
    /**
     * VOC이력 등록(insert)
     * 
     * @param param 등록할 VOC이력 객체
     * @return 적용된 행 수
     */
    public void insertVocHist(VocMstDto.Info param, String histTypeCd, String eventType) {
        VocHistDto.Info req = VocHistDto.Info.builder().build();

        req.setVocSeq(param.getVocSeq());
        req.setHistTypeCd(histTypeCd);
        req.setRegUserNo(param.getRegUserNo());

        String histCont = "" ;
        if(StringUtils.equals(histTypeCd, "04")) {  //반려인 경우 반려사유 저장
            histCont = eventType;
        } else {
            histCont = "VOC가 " + eventType + "되었습니다." ;
        }
        req.setHistCont(histCont);

        mapper.insertVocHist(req);
    }

}
