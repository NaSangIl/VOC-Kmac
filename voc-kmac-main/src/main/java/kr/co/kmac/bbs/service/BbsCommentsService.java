package kr.co.kmac.bbs.service;

import kr.co.kmac.bbs.dto.BbsCommentsDto;
import kr.co.kmac.bbs.mapper.BbsCommentsMapper;
import kr.co.kmac.common.util.MessageUtil;
import kr.co.kmac.coreframework.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 게시판댓글 서비스 클래스
 * 
 * @ClassName BbsCommentsService.java
 * @Description 게시판댓글 서비스 클래스
 * @author mjkim
 * @since 2023. 11. 13.
 */
@Service
public class BbsCommentsService extends BaseService
{
    // 게시판댓글 조작 위한 DAO 객체
    @Autowired
    private BbsCommentsMapper mapper;

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
     * 게시판댓글 목록 조회
     * 
     * @param param 게시판댓글 검색 조건 포함 객체
     * @return 결과 행 및 페이지 정보를 포함한 PaginatedResponse 객체
     */
    public BbsCommentsDto.ListResponse getBbsCommentsList(BbsCommentsDto.Request param)
    {
        long total = mapper.getBbsCommentsListCount(param);
        return BbsCommentsDto.ListResponse
                .<BbsCommentsDto.Info>builder()
                .totalCount(total)
                .list(mapper.getBbsCommentsList(param))
                .build();
	}

    /**
     * 게시판댓글 상세 조회
     * 
     * @param bbsCommentsSeq 게시판댓글 pk
     * @return 게시판댓글 객체
     */
    public BbsCommentsDto.Info getBbsComments(int bbsCommentsSeq)
    {
        BbsCommentsDto.Info res = mapper.getBbsComments(bbsCommentsSeq);
        return mapper.getBbsComments(bbsCommentsSeq);
    }
    
    /**
     * 게시판댓글 등록(insert)
     * 
     * @param param 등록할 게시판댓글 객체
     * @return 적용된 행 수
     */
    @Transactional
    public BbsCommentsDto.Response insertBbsComments(BbsCommentsDto.Info param) {
        BbsCommentsDto.Response res = BbsCommentsDto.Response.builder().build();

        res.setRtnCnt(mapper.insertBbsComments(param));
        res.setRtnMessage(MessageUtil.getMessage("common.insert.0000"));
        return res;
    }

    /**
     * 게시판댓글 수정(update)
     *
     * @param param 저장할 게시판댓글 객체
     * @return 적용된 행 수
     */
    @Transactional
    public BbsCommentsDto.Response updateBbsComments(BbsCommentsDto.Info param) {
        BbsCommentsDto.Response res = BbsCommentsDto.Response.builder().build();

        res.setRtnCnt(mapper.updateBbsComments(param));
        res.setRtnMessage(MessageUtil.getMessage("common.update.0000"));
        return res;
    }

	
    /**
     * 게시판댓글 삭제
     * 
     * @param bbsCommentsSeq 게시판댓글 pk
     * @return 적용된 행 수
     */
    @Transactional
    public BbsCommentsDto.Response deleteBbsComments(int bbsCommentsSeq) {
        BbsCommentsDto.Response res = BbsCommentsDto.Response.builder().build();

        res.setRtnCnt(mapper.deleteBbsComments(bbsCommentsSeq));
        res.setRtnMessage(MessageUtil.getMessage("common.delete.0000"));
        return res;
    }
}
