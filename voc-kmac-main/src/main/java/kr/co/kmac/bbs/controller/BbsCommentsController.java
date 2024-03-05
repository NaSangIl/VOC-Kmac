package kr.co.kmac.bbs.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.kmac.bbs.dto.BbsCommentsDto;
import kr.co.kmac.bbs.service.BbsCommentsService;
import kr.co.kmac.common.util.ResponseUtil;
import kr.co.kmac.coreframework.common.response.ResponseObject;
import kr.co.kmac.coreframework.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 게시판댓글 관리/작업 Controller
 * 
 * @ClassName BbsCommentsController.java
 * @Description 게시판댓글
 * @bbsor mjkim
 * @since 2023. 11. 13.
 */
@Tag(name = "BbsCommentsController", description = "게시판댓글관리 API")
@RestController
@RequestMapping("/kmacvoc/v1/bbs/comments")
public class BbsCommentsController extends BaseController
{
    @Autowired
    private BbsCommentsService service;

    /**
     * 게시판댓글 목록 조회
     * 
     * @param param 게시판댓글 검색 조건 포함 객체
     * @return 결과 행 및 페이지 정보를 포함한 PaginatedResponse 객체
     */
    @Operation(summary = "GET 게시판댓글 목록 조회", description = "게시판댓글 목록 조회")
    @GetMapping("/list")
    public ResponseObject<BbsCommentsDto.ListResponse> getBbsCommentsList(HttpServletRequest req, BbsCommentsDto.Request param)
    {
        return ResponseUtil.getResponse(req, service.getBbsCommentsList(param));
    }
	
    /**
     * 게시판댓글 상세 조회
     * 
     * @param bbsCommentsSeq 게시판댓글 검색 조건 포함 객체
     * @return 게시판댓글 객체
     */
    @Operation(summary = "GET 게시판댓글 상세 조회", description = "bbsCommentsSeq로 게시판댓글 정보 1건 조회")
    @GetMapping("/{bbsCommentsSeq}")
    public ResponseObject<BbsCommentsDto.Info> getBbsComments(HttpServletRequest req
            , @Parameter(description = "게시판댓글일련번호") @PathVariable(name = "bbsCommentsSeq") int bbsCommentsSeq)
    {
        return ResponseUtil.getResponse(req, service.getBbsComments(bbsCommentsSeq));
    }

    /**
     * 게시판댓글 등록
     *ss
     * @param param 등록할 게시판댓글 객체
     * @return 등록결과 객체
     */
    @Operation(summary = "게시판댓글 등록", description = "게시판댓글 정보를 등록한다.")
    @PostMapping("/add")
    public ResponseObject insertBbsComments(HttpServletRequest req, @RequestBody BbsCommentsDto.Info param)
    {
        return ResponseUtil.getResponse(req, service.insertBbsComments(param));
    }

    /**
     * 게시판댓글 수정
     *
     * @param param 수정할 게시판댓글 객체
     * @return 수정결과 객체
     */
    @Operation(summary = "게시판댓글 수정", description = "게시판댓글 정보를 수정한다.")
    @PostMapping("/modify")
    public ResponseObject updateBbsComments(HttpServletRequest req, @RequestBody BbsCommentsDto.Info param)
    {
        return ResponseUtil.getResponse(req, service.updateBbsComments(param));
    }

    /**end
     * 게시판댓글 삭제
     * 
     * @param bbsCommentsSeq 삭제할 게시판댓글 key
     */
    @Operation(summary = "게시판댓글 삭제", description = "게시판댓글정보를 삭제한다.")
    @PostMapping("/remove/{bbsCommentsSeq}")
    public ResponseObject deleteBbsComments(HttpServletRequest req
            , @Parameter(description = "게시판댓글일련번호") @PathVariable(name = "bbsCommentsSeq") int bbsCommentsSeq)
    {
        return ResponseUtil.getResponse(req, service.deleteBbsComments(bbsCommentsSeq));
    }
}
