package kr.co.kmac.bbs.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.kmac.bbs.dto.BbsDto;
import kr.co.kmac.bbs.service.BbsService;
import kr.co.kmac.common.dto.AttachFileDto;
import kr.co.kmac.common.util.FileUtil;
import kr.co.kmac.common.util.ResponseUtil;
import kr.co.kmac.common.util.validation.PostMethod;
import kr.co.kmac.coreframework.common.response.ResponseObject;
import kr.co.kmac.coreframework.controller.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 게시판 관리/작업 Controller
 * 
 * @ClassName BbsController.java
 * @Description 게시판
 * @bbsor mjkim
 * @since 2023. 11. 13.
 */
@Tag(name = "BbsController", description = "게시판관리 API")
@RestController
@RequestMapping("/kmacvoc/v1/bbs")
public class BbsController extends BaseController
{
    @Autowired
    private BbsService service;

    /**
     * 게시판 목록 조회
     * 
     * @param param 게시판 검색 조건 포함 객체
     * @return 결과 행 및 페이지 정보를 포함한 PaginatedResponse 객체
     */
    @Operation(summary = "GET 게시판 목록 조회", description = "게시판 목록 조회")
    @GetMapping("/list")
    public ResponseObject<BbsDto.ListResponse> getBbsList(HttpServletRequest req, BbsDto.Request param)
    {
        return ResponseUtil.getResponse(req, service.getBbsList(param));
    }
	
    /**
     * 게시판 상세 조회
     * 
     * @param bbsSeq 게시판 검색 조건 포함 객체
     * @return 게시판 객체
     */
    @Operation(summary = "GET 게시판 상세 조회", description = "bbsSeq로 게시판 정보 1건 조회")
    @GetMapping("/{bbsSeq}")
    public ResponseObject<BbsDto.Info> getBbs(HttpServletRequest req
            , @Parameter(description = "게시판일련번호") @PathVariable(name = "bbsSeq") int bbsSeq)
    {
        return ResponseUtil.getResponse(req, service.getBbs(bbsSeq));
    }

    /**
     * 게시판 등록
     *ss
     * @param param 등록할 게시판 객체
     * @return 등록결과 객체
     */
    @Operation(summary = "게시판 등록", description = "게시판 정보를 등록한다.")
    @PostMapping("/add")
    public ResponseObject insertBbs(MultipartHttpServletRequest req, @Validated(PostMethod.class) BbsDto.Info param) throws Exception
    {
        _attachFile(param, req);
        return ResponseUtil.getResponse(req, service.insertBbs(param));
    }

    /**
     * 게시판 수정
     *
     * @param param 수정할 게시판 객체
     * @return 수정결과 객체
     */
    @Operation(summary = "게시판 수정", description = "게시판 정보를 수정한다.")
    @PostMapping("/modify")
    public ResponseObject updateBbs(MultipartHttpServletRequest req, @Validated(PostMethod.class) BbsDto.Info param) throws Exception
    {
        _attachFile(param, req);
        return ResponseUtil.getResponse(req, service.updateBbs(param));
    }

    /**end
     * 게시판 삭제
     * 
     * @param bbsSeq 삭제할 게시판 key
     */
    @Operation(summary = "게시판 삭제", description = "게시판정보를 삭제한다.")
    @PostMapping("/remove/{bbsSeq}")
    public ResponseObject deleteBbs(HttpServletRequest req
            , @Parameter(description = "게시판일련번호") @PathVariable(name = "bbsSeq") int bbsSeq)
    {
        return ResponseUtil.getResponse(req, service.deleteBbs(bbsSeq));
    }

    /**
     * 게시판 파일첨부 공통처리
     *
     * @param param 파일업로드 후 셋팅할 파일정보 객체
     * @param mRequest
     */
    private void _attachFile(BbsDto.Info param, MultipartHttpServletRequest mRequest) throws Exception {
        List<AttachFileDto.Info> fileList = null;
        List<MultipartFile> files = mRequest.getFiles("files");
        if(files.size() > 0 && StringUtils.isNotEmpty(files.get(0).getOriginalFilename())) {
            fileList = FileUtil.uploadFiles(files);
            param.setFileList(fileList);
        }
    }
}
