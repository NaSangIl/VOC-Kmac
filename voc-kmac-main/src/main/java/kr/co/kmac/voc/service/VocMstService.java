package kr.co.kmac.voc.service;

import kr.co.kmac.bbs.dto.BbsDto;
import kr.co.kmac.common.constants.CommonConstants;
import kr.co.kmac.common.dto.AttachFileDto;
import kr.co.kmac.common.service.AttachFileService;
import kr.co.kmac.common.util.MessageUtil;
import kr.co.kmac.coreframework.common.exception.ApiBusinessException;
import kr.co.kmac.coreframework.service.BaseService;
import kr.co.kmac.voc.dto.VocCustDto;
import kr.co.kmac.voc.dto.VocMstDto;
import kr.co.kmac.voc.mapper.VocCustMapper;
import kr.co.kmac.voc.mapper.VocHistMapper;
import kr.co.kmac.voc.mapper.VocMstMapper;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * VOC마스터 서비스 클래스
 *
 * @ClassName VocMstService.java
 * @Description VOC마스터 서비스 클래스
 * @author mjkim
 * @since 2023. 9. 18.
 */
@Service
public class VocMstService extends BaseService
{
    // VOC마스터 조작 위한 DAO 객체
    @Autowired
    private VocMstMapper mapper;
    @Autowired
    private VocCustMapper custMapper;

    @Autowired
    private AttachFileService attachFileService;

    @Autowired
    private VocHistMapper vocHistMapper;

    @Autowired
    private VocHistService vocHistService;

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
     * VOC마스터 목록 조회
     *
     * @param param VOC마스터 검색 조건 포함 객체
     * @return 결과 행 및 페이지 정보를 포함한 PaginatedResponse 객체
     */
    public VocMstDto.ListResponse getVocMstList(VocMstDto.Request param)
    {
        long total = mapper.getVocMstListCount(param);
        return VocMstDto.ListResponse
                .<VocMstDto.Info>builder()
                .totalCount(total)
                .list(mapper.getVocMstList(param))
                .build();
	}

    /**
     * VOC마스터 상세 조회
     *
     * @param vocSeq VOC마스터 pk
     * @return VOC마스터 객체
     */
    public VocMstDto.DetailResponse getVocMst(int vocSeq)
    {
        VocMstDto.DetailResponse res = VocMstDto.DetailResponse.builder().build();
        VocMstDto.Info vocMst = mapper.getVocMst(vocSeq);
        if(ObjectUtils.isEmpty(vocMst)) {
            throw new ApiBusinessException("error.9200","VOC 정보를 찾을 수 없습니다.");
        }
        BeanUtils.copyProperties(vocMst, res);

        // 고객정보
        VocCustDto.Info vocCust = custMapper.getVocCust(res.getCustSeq());
        res.setCustNm(vocCust.getCustNm());
        res.setCustNo(vocCust.getCustNo());
        res.setTelNo(vocCust.getTelNo());
        res.setEmailAddr(vocCust.getEmailAddr());
        res.setVocCustInfo(vocCust);

        // 파일정보
        AttachFileDto.Request fReq = AttachFileDto.Request.builder().build();
        res.setFileList1(attachFileService.getAttachFileList("P0", new Integer(vocSeq).toString()));
        res.setFileList2(attachFileService.getAttachFileList("C0", new Integer(vocSeq).toString()));

        // VOC 히스토리정보
        res.setHistList(vocHistMapper.getVocHistList(vocSeq));

        return res;
    }

    /**
     * 메인화면 VOC현황 조회
     *
     * @param param VOC마스터 객체
     * @return VOC마스터 객체
     */
    public VocMstDto.StatusInfo getVocStates(VocMstDto.Request param) {
        return mapper.getVocStates(param);
    }
    
    /**
     * 메인화면 VOC현황 및 추이 조회
     *
     * @param param VOC마스터 객체
     * @return VOC마스터 객체
     */
    public List<VocMstDto.ProgressInfo> getVocProgress(VocMstDto.Request param) {
    	return mapper.getVocProgress(param);
    }
    
    /**
     * 메인화면 VOC 처리현황 조회
     *
     * @param param VOC마스터 객체
     * @return VOC마스터 객체
     */
    public VocMstDto.ActProgressInfo getVocActProgress(VocMstDto.Request param) {
    	return mapper.getVocActProgress(param);
    }
    
    /**
     * 메인화면 VOC유형별 접수현황 조회(1Level)
     *
     * @param param VOC마스터 객체
     * @return VOC마스터 객체
     */
    public List<VocMstDto.VocTypeStatusInfo> getVocTypeStates1Lev(VocMstDto.Request param) {
    	return mapper.getVocTypeStates1Lev(param);
    }
    
    /**
     * 메인화면 VOC유형별 접수현황 조회(2Level)
     *
     * @param param VOC마스터 객체
     * @return VOC마스터 객체
     */
    public List<VocMstDto.VocTypeStatusInfo> getVocTypeStates2Lev(VocMstDto.Request param) {
    	return mapper.getVocTypeStates2Lev(param);
    }
    
    /**
     * 메인화면 VOC유형별 접수현황 조회(3Level)
     *
     * @param param VOC마스터 객체
     * @return VOC마스터 객체
     */
    public List<VocMstDto.VocTypeStatusInfo> getVocTypeStates3Lev(VocMstDto.Request param) {
    	return mapper.getVocTypeStates3Lev(param);
    }    
    
    /**
     * 메인화면 VOC처리유형별 접수현황 조회(1Level)
     *
     * @param param VOC마스터 객체
     * @return VOC마스터 객체
     */
    public List<VocMstDto.VocActTypeStatusInfo> getVocActTypeStates1Lev(VocMstDto.Request param) {
    	return mapper.getVocActTypeStates1Lev(param);
    }
    
    /**
     * 메인화면 VOC처리유형별 접수현황 조회(2Level)
     *
     * @param param VOC마스터 객체
     * @return VOC마스터 객체
     */
    public List<VocMstDto.VocActTypeStatusInfo> getVocActTypeStates2Lev(VocMstDto.Request param) {
    	return mapper.getVocActTypeStates2Lev(param);
    }    
    
    /**
     * 메인화면 공지사항 조회
     *
     * @param param VOC마스터 객체
     * @return VOC마스터 객체
     */
    public List<BbsDto.MainNoticeListInfo> getMainNoticeList(BbsDto.Request param) {
    	return mapper.getMainNoticeList(param);
    }       
    
    /**
     * VOC 접수(insert)
     *
     * @param param 접수처리할 VOC마스터 객체
     * @return 적용된 행 수
     */
    @Transactional
    public VocMstDto.Response insertVocMst(VocMstDto.Info param) {
        VocMstDto.Response res = VocMstDto.Response.builder().build();

        //익명고객인 경우, 익명고객정보가 등록되어 있는지 확인
        if(StringUtils.equals(param.getAnonymCustYn(), "Y")) {
            VocCustDto.Request custParam = VocCustDto.Request.builder().build();
            custParam.setCompanyCd(param.getCompanyCd());
            custParam.setCustNo(param.getCompanyCd()+"_99999");
            custParam.setAnonymCustYn("Y");
            custParam.setOffset(-1);
            List<VocCustDto.Info> custList = custMapper.getVocCustList(custParam);
            if(custList.size() > 0) param.setCustSeq(custList.get(0).getCustSeq());
        }

        // 신규고객일 경우, 고객정보 등록
        if(param.getCustSeq() == 0) {
            VocCustDto.Info vocCust = VocCustDto.Info.builder().build();
            vocCust.setCompanyCd(param.getCompanyCd());
            vocCust.setCustNm(param.getCustNm());
            vocCust.setTelNo(param.getTelNo());
            vocCust.setEmailAddr(param.getEmailAddr());
            if(StringUtils.equals(param.getAnonymCustYn(), "Y")) vocCust.setCustNo(param.getCompanyCd()+"_99999"); //익명고객

            custMapper.insertVocCust(vocCust);
            param.setCustSeq(vocCust.getReturnKey());
        }

        param.setVocStatusCd("P0"); // 처리중 상태

        // 즉시처리인 경우 처리항목 필수 체크
        if("Y".equals(param.getImmeActYn())) {
            if(ObjectUtils.isEmpty(param.getVocActTypeCd1())) {
                throw new ApiBusinessException("error.1047", new String[] {"처리유형코드"});
            }

            if(ObjectUtils.isEmpty(param.getVocActDt())) {
                throw new ApiBusinessException("error.1047", new String[] {"처리일시"});
            }

            if(ObjectUtils.isEmpty(param.getVocActUserNo())) {
                throw new ApiBusinessException("error.1047", new String[] {"처리자"});
            }

            if(ObjectUtils.isEmpty(param.getVocActCont())) {
                throw new ApiBusinessException("error.1047", new String[] {"처리내용"});
            }

            param.setVocStatusCd("C0"); // 처리완료 상태
        }

        res.setRtnCnt(mapper.insertVocMst(param));
        res.setRtnMessage(MessageUtil.getMessage("voc.insert.0000"));
        res.setRtnKey(param.getReturnKey());
        param.setVocSeq(param.getReturnKey());

        // 첨부파일정보 저장
        _insertAttachFile(param);

        // 히스토리 저장
        vocHistService.insertVocHist(param, "01", CommonConstants.VOC_RCPT);

        return res;
    }

    /**
     * VOC마스터 수정(update)
     *
     * @param param 수정할 VOC마스터 객체
     * @return 적용된 행 수
     */
    @Transactional
    public VocMstDto.Response updateVocMst(VocMstDto.Info param) {
        VocMstDto.Response res = VocMstDto.Response.builder().build();

        if(param.getVocSeq()==0) {
            throw new ApiBusinessException("error.9200","VOC 정보를 찾을 수 없습니다.");
        }

        String rtnMsg = MessageUtil.getMessage("voc.update.0000");
        String histTypeCd = "02";

        res.setRtnCnt(mapper.updateVocMst(param));
        res.setRtnMessage(rtnMsg);

        // 첨부파일정보 저장
        _insertAttachFile(param);

        // 히스토리 저장
        vocHistService.insertVocHist(param, histTypeCd, CommonConstants.VOC_SAVE);

        return res;
    }

    /**
     * VOC마스터 완료상신(update)
     *
     * @param param 수정할 VOC마스터 객체
     * @return 적용된 행 수
     */
    @Transactional
    public VocMstDto.Response procApproval(VocMstDto.Info param) {
        VocMstDto.Response res = VocMstDto.Response.builder().build();

        if(param.getVocSeq()==0) {
            throw new ApiBusinessException("error.9200","VOC 정보를 찾을 수 없습니다.");
        }

        String rtnMsg = MessageUtil.getMessage("voc.approval.0000");
        String histTypeCd = "03"; //상신
        param.setVocStatusCd("A0"); //완료상신

        res.setRtnCnt(mapper.updateVocMst(param));
        res.setRtnMessage(rtnMsg);

        // 히스토리 저장
        vocHistService.insertVocHist(param, histTypeCd, CommonConstants.VOC_APPR);

        return res;
    }

    /**
     * VOC마스터 반려(update)
     *
     * @param param 수정할 VOC마스터 객체
     * @return 적용된 행 수
     */
    @Transactional
    public VocMstDto.Response procReject(VocMstDto.Info param) {
        VocMstDto.Response res = VocMstDto.Response.builder().build();

        if(param.getVocSeq()==0) {
            throw new ApiBusinessException("error.9200","VOC 정보를 찾을 수 없습니다.");
        }

        String rtnMsg = MessageUtil.getMessage("voc.reject.0000");
        String histTypeCd = "04";   //반려
        param.setVocStatusCd("P0"); //처리중

        res.setRtnCnt(mapper.updateVocMstStatus(param));
        res.setRtnMessage(rtnMsg);

        // 히스토리 저장
        vocHistService.insertVocHist(param, histTypeCd, param.getRejectMemo());

        return res;
    }

    /**
     * VOC 완료(update)
     *
     * @param param 완료처리할 VOC마스터 객체
     * @return 적용된 행 수
     */
    @Transactional
    public VocMstDto.Response finishVocMst(VocMstDto.Info param) {
        VocMstDto.Response res = VocMstDto.Response.builder().build();

        if(param.getVocSeq()==0) {
            throw new ApiBusinessException("error.9200","VOC 정보를 찾을 수 없습니다.");
        }

        param.setVocStatusCd("C0"); // 처리완료 상태

        res.setRtnCnt(mapper.updateVocMstStatus(param));
        res.setRtnMessage(MessageUtil.getMessage("voc.finish.0000"));

        // 히스토리 저장
        vocHistService.insertVocHist(param, "05", CommonConstants.VOC_FINISH);

        return res;
    }

    /**
     * VOC 취소(update)
     *
     * @param param 저장 할 VOC마스터 객체
     * @return 적용된 행 수
     */
    @Transactional
    public VocMstDto.Response cancelVocMst(VocMstDto.Info param) {
        VocMstDto.Response res = VocMstDto.Response.builder().build();

        if(param.getVocSeq()==0) {
            throw new ApiBusinessException("error.9200","VOC 정보를 찾을 수 없습니다.");
        }

        param.setVocStatusCd("P0"); // 처리중 상태

        res.setRtnCnt(mapper.updateVocMst(param));
        res.setRtnMessage(MessageUtil.getMessage("voc.cancel.0000"));

        // 히스토리 저장
        vocHistService.insertVocHist(param, "09", CommonConstants.VOC_CANCEL);

        return res;
    }


    /**
     * VOC마스터 삭제
     *
     * @param param 삭제 할 조건객체
     * @return 적용된 행 수
     */
    @Transactional
    public VocMstDto.Response deleteVocMst(VocMstDto.Info param) {
        VocMstDto.Response res = VocMstDto.Response.builder().build();

        if(param.getVocSeq()==0) {
            throw new ApiBusinessException("error.9200","VOC 정보를 찾을 수 없습니다.");
        }

        res.setRtnCnt(mapper.deleteVocMst(param));
        res.setRtnMessage(MessageUtil.getMessage("voc.delete.0000"));

        // 히스토리 저장
        vocHistService.insertVocHist(param, "09", CommonConstants.VOC_DELETE);

        return res;
    }

    /**
     * VOC 첨부파일정보 저장 공통처리
     *
     * @param param 저장할 파일정보 객체
     */
    private void _insertAttachFile(VocMstDto.Info param) {
        //VOC 첨부파일
        if(ObjectUtils.isNotEmpty(param.getFileList1()) && param.getFileList1().size() > 0) {
            param.getFileList1().forEach(i -> {
                if(param.getVocSeq() < 1) {
                    throw new ApiBusinessException("error.1047", new String[] {"VOC번호"});
                }

                i.setFileRefKey(new Integer(param.getVocSeq()).toString()); // VOC 키 셋팅
                i.setFileRefDiv("P0");                    // VOC 상태코드 셋팅
                attachFileService.insertAttachFile(i);
            });
        }

        //VOC 처리 첨부파일
        if(ObjectUtils.isNotEmpty(param.getFileList2()) && param.getFileList2().size() > 0) {
            param.getFileList2().forEach(i -> {
                if(param.getVocSeq() < 1) {
                    throw new ApiBusinessException("error.1047", new String[] {"VOC번호"});
                }

                i.setFileRefKey(new Integer(param.getVocSeq()).toString()); // VOC 키 셋팅
                i.setFileRefDiv("C0");                    // VOC 상태코드 셋팅
                attachFileService.insertAttachFile(i);
            });
        }
    }
}
