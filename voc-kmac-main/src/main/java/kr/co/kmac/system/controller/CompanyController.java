package kr.co.kmac.system.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.kmac.common.dto.AttachFileDto;
import kr.co.kmac.common.util.FileUtil;
import kr.co.kmac.common.util.ResponseUtil;
import kr.co.kmac.coreframework.common.response.ResponseObject;
import kr.co.kmac.coreframework.controller.BaseController;
import kr.co.kmac.system.dto.CompanyDto;
import kr.co.kmac.system.service.CompanyService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

/**
 * 회사 관리/작업 Controller
 *
 * @ClassName CompanyController.java
 * @Description 회사
 * @companyor mjkim
 * @since 2023. 9. 18.
 */
@Tag(name = "CompanyController", description = "회사관리 API")
@RestController
@RequestMapping("/kmacvoc/v1/company")
public class CompanyController extends BaseController
{
    @Autowired
    private CompanyService service;

    @Value("${spring.servlet.multipart.location}")
    private String rootPath;

    @Value("logo/")
    private String logoImgPath;

    /**
     * 회사 목록 조회
     *
     * @param param 회사 검색 조건 포함 객체
     * @return 결과 행 및 페이지 정보를 포함한 PaginatedResponse 객체
     */
    @Operation(summary = "GET 회사 목록 조회", description = "회사 목록 조회")
    @GetMapping("/list")
    public ResponseObject<CompanyDto.ListResponse> getCompanyList(HttpServletRequest req, CompanyDto.Request param)
    {
        return ResponseUtil.getResponse(req, service.getCompanyList(param));
    }

    /**
     * 회사 상세 조회
     *
     * @param companySeq 회사 검색 조건 포함 객체
     * @return 회사 객체
     */
    @Operation(summary = "GET 회사 상세 조회", description = "companySeq로 회사 정보 1건 조회")
    @GetMapping("/{companySeq}")
    public ResponseObject<CompanyDto.Info> getCompany(HttpServletRequest req
            , @Parameter(description = "회사코드") @PathVariable(name = "companySeq") int companySeq)
    {
        return ResponseUtil.getResponse(req, service.getCompany(companySeq));
    }

    /**
     * 회사 등록
     *
     * @param param 등록할 회사 객체
     * @return 등록결과 객체
     */
    @Operation(summary = "회사 등록", description = "회사 정보를 등록한다. 회사로고 이미지: 'file' 변수에 셋팅")
    @PostMapping(value="/add")
    public ResponseObject insertCompany(MultipartHttpServletRequest req, CompanyDto.Info param) throws Exception
    {
        _fileUpload(req, param);
        return ResponseUtil.getResponse(req, service.insertCompany(param));
    }

    /**
     * 회사 수정
     *
     * @param param 수정할 회사 객체
     * @return 수정결과 객체
     */
    @Operation(summary = "회사 수정", description = "회사 정보를 수정한다. 회사로고 이미지: 'file' 변수에 셋팅")
    @PostMapping("/modify")
    public ResponseObject updateCompany(MultipartHttpServletRequest req, CompanyDto.Info param) throws Exception
    {
        _fileUpload(req, param);
        return ResponseUtil.getResponse(req, service.updateCompany(param));
    }

    /**end
     * 회사 삭제
     *
     * @param companySeq 삭제할 회사 key
     */
    @Operation(summary = "회사 삭제", description = "회사정보를 삭제한다.")
    @PostMapping("/remove/{companySeq}")
    public ResponseObject deleteCompany(HttpServletRequest req
            , @Parameter(description = "회사코드") @PathVariable(name = "companySeq") int companySeq)
    {
        return ResponseUtil.getResponse(req, service.deleteCompany(companySeq));
    }


    /**
     * 회사로고파일 download
     *
     * @param companyLogoFileNm 다운로드할 회사로고파일명
     */
    @Operation(summary = "회사로고파일 다운로드", description = "회사로고파일을 다운로드한다.")
    @GetMapping("/download")
    public ResponseEntity<Resource> downloadFile(HttpServletResponse res, String companyLogoFileNm, String companyLogoPath) throws Exception
    {
        logger.info("companyLogoPath=>"+companyLogoPath);
        AttachFileDto.Info file = AttachFileDto.Info.builder().build();
        file.setFilePath(companyLogoPath);
        file.setFileNm(companyLogoFileNm);
        FileUtil.downloadFile(res, file);

        return null;
    }

    /**
     * 회사로고파일 upload
     *
     * @param req
     * @param param
     */
    private void _fileUpload(MultipartHttpServletRequest req, CompanyDto.Info param) throws Exception {
        MultipartFile file = req.getFile("logoFile");
        if(file != null && StringUtils.isNotEmpty(file.getOriginalFilename())) {
            logger.info(req.getServletContext().getContextPath());
            String fileNm = file.getOriginalFilename();
            String filePath = rootPath + logoImgPath + param.getCompanyCd() + "." + fileNm.substring(fileNm.lastIndexOf(".") + 1);
            File f = new File(filePath);
            file.transferTo(f);

            param.setCompanyLogoPath(f.getAbsolutePath());
            param.setCompanyLogoFileNm(fileNm);
        }
    }
}
