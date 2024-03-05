package kr.co.kmac.common.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.kmac.common.dto.AttachFileDto;
import kr.co.kmac.common.service.AttachFileService;
import kr.co.kmac.common.util.FileUtil;
import kr.co.kmac.common.util.ResponseUtil;
import kr.co.kmac.coreframework.common.response.ResponseObject;
import kr.co.kmac.coreframework.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 공통파일 관리/작업 Controller
 * 
 * @ClassName AttachFilController.java
 * @Description 첨부파일
 * @author mjkim
 * @since 2023. 9. 18.
 */
@Tag(name = "AttachFilController", description = "공통파일 관리 API")
@RestController
@RequestMapping("/kmacvoc/v1/file")
public class AttachFilController extends BaseController
{
    @Autowired
    private AttachFileService service;

    /**
     * 첨부파일 삭제
     *
     * @param fileSeq 삭제할 첨부파일 객체
     */
    @Operation(summary = "첨부파일 삭제", description = "첨부파일 정보를 삭제한다.")
    @PostMapping("/remove/{fileSeq}")
    public ResponseObject deleteAttachFile(HttpServletRequest req
            , @Parameter(description = "파일번호") @PathVariable(name = "fileSeq") int fileSeq)
    {
        return ResponseUtil.getResponse(req, service.deleteAttachFile(fileSeq));
    }


    /**
     * 파일 download
     *
     * @param fileSeq 다운로드할 파일 키
     */
    @Operation(summary = "파일 다운로드", description = "파일을 다운로드한다.")
    @GetMapping("/download/{fileSeq}")
    public ResponseEntity<Resource> downloadFile(HttpServletResponse res
            , @Parameter(description = "파일번호") @PathVariable(name = "fileSeq") int fileSeq) throws Exception
    {
        AttachFileDto.Info file;
        file = service.getAttachFile(fileSeq);
        FileUtil.downloadFile(res, file);

        return null;
    }
}
