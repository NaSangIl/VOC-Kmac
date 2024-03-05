package kr.co.kmac.bbs.service;

import kr.co.kmac.bbs.dto.BbsDto;
import kr.co.kmac.bbs.mapper.BbsMapper;
import kr.co.kmac.common.service.AttachFileService;
import kr.co.kmac.common.util.MessageUtil;
import kr.co.kmac.coreframework.common.exception.ApiBusinessException;
import kr.co.kmac.coreframework.service.BaseService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 게시판 서비스 클래스
 * 
 * @ClassName BbsService.java
 * @Description 게시판 서비스 클래스
 * @author mjkim
 * @since 2023. 11. 13.
 */
@Service
public class BbsService extends BaseService
{
    // 게시판 조작 위한 DAO 객체
    @Autowired
    private BbsMapper mapper;

    @Autowired
    private AttachFileService attachFileService;

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
     * 게시판 목록 조회
     * 
     * @param param 게시판 검색 조건 포함 객체
     * @return 결과 행 및 페이지 정보를 포함한 PaginatedResponse 객체
     */
    public BbsDto.ListResponse getBbsList(BbsDto.Request param)
    {
        long total = mapper.getBbsListCount(param);
        return BbsDto.ListResponse
                .<BbsDto.Info>builder()
                .totalCount(total)
                .list(mapper.getBbsList(param))
                .build();
	}

    /**
     * 게시판 상세 조회
     * 
     * @param bbsSeq 게시판 pk
     * @return 게시판 객체
     */
    public BbsDto.Info getBbs(int bbsSeq)
    {
        // 조회수 수정
        mapper.updateHit(bbsSeq);

        // 게시판 상세 조회
        BbsDto.Info res = mapper.getBbs(bbsSeq);

        // 파일정보
        res.setFileList(attachFileService.getAttachFileList("BBS", new Integer(bbsSeq).toString()));

        return res;
    }
    
    /**
     * 게시판 등록(insert)
     * 
     * @param param 등록할 게시판 객체
     * @return 적용된 행 수
     */
    @Transactional
    public BbsDto.Response insertBbs(BbsDto.Info param) {
        BbsDto.Response res = BbsDto.Response.builder().build();

        res.setRtnCnt(mapper.insertBbs(param));
        param.setBbsSeq(param.getReturnKey());

        // 첨부파일정보 저장
        _insertAttachFile(param);

        res.setRtnMessage(MessageUtil.getMessage("common.insert.0000"));
        return res;
    }

    /**
     * 게시판 수정(update)
     *
     * @param param 저장할 게시판 객체
     * @return 적용된 행 수
     */
    @Transactional
    public BbsDto.Response updateBbs(BbsDto.Info param) {
        BbsDto.Response res = BbsDto.Response.builder().build();

        res.setRtnCnt(mapper.updateBbs(param));

        // 첨부파일정보 저장
        _insertAttachFile(param);

        res.setRtnMessage(MessageUtil.getMessage("common.update.0000"));
        return res;
    }

	
    /**
     * 게시판 삭제
     * 
     * @param bbsSeq 게시판 pk
     * @return 적용된 행 수
     */
    @Transactional
    public BbsDto.Response deleteBbs(int bbsSeq) {
        BbsDto.Response res = BbsDto.Response.builder().build();

        res.setRtnCnt(mapper.deleteBbs(bbsSeq));
        res.setRtnMessage(MessageUtil.getMessage("common.delete.0000"));

        // 첨부파일 삭제
        attachFileService.deleteAttachFileByRef("BBS", new Integer(bbsSeq).toString());
        return res;
    }

    /**
     * 게시판 첨부파일정보 저장 공통처리
     *
     * @param param 저장할 파일정보 객체
     */
    private void _insertAttachFile(BbsDto.Info param) {
        if(ObjectUtils.isNotEmpty(param.getFileList()) && param.getFileList().size() > 0) {
            param.getFileList().forEach(i -> {
                if(param.getBbsSeq() < 1) {
                    throw new ApiBusinessException("error.1047", new String[] {"게시판번호"});
                }

                i.setFileRefKey(new Integer(param.getBbsSeq()).toString());
                i.setFileRefDiv("BBS");
                attachFileService.insertAttachFile(i);
            });
        }
    }
}
