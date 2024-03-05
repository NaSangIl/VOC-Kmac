package kr.co.kmac.system.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.kmac.common.dto.AttachFileDto;
import kr.co.kmac.common.util.ConvertTextUtil;
import kr.co.kmac.common.util.FileUtil;
import kr.co.kmac.common.util.ResponseUtil;
import kr.co.kmac.common.util.validation.PostMethod;
import kr.co.kmac.coreframework.common.exception.ApiBusinessException;
import kr.co.kmac.coreframework.common.response.ResponseObject;
import kr.co.kmac.coreframework.controller.BaseController;
import kr.co.kmac.system.dto.CustomCodeDto;
import kr.co.kmac.system.dto.CustomCodeMstDto;
import kr.co.kmac.system.service.CustomCodeService;

/**
 * 회사지정코드 관리/작업 Controller
 *
 * @ClassName CustomCodeController.java
 * @Description 회사지정코드
 * @customor mjkim
 * @since 2023. 9. 18.
 */
@Tag(name = "CustomCodeController", description = "회사지정코드관리 API")
@RestController
@RequestMapping("/kmacvoc/v1/custom")
public class CustomCodeController extends BaseController
{
    @Autowired
    private CustomCodeService service;

    @Autowired
    private ResourceLoader resourceLoader;

    public static String voctypePath;   //VOC유형업로드양식 다운로드 경로
    public static String acttypePath;   //처리유형업로드양식 다운로드 경로

    @Value("${excel.template.voctype-path}")    //업로드양식다운로드 패스
    public void setVoctypePath(String value) {
        voctypePath = value;
    }
    @Value("${excel.template.acttype-path}")    //업로드양식다운로드 패스
    public void setActtypePath(String value) {
        acttypePath = value;
    }

    /**
     * 회사지정코드마스터 목록 조회
     *
     * @param param 회사지정코드마스터 검색 조건 포함 객체
     * @return 결과 행 및 페이지 정보를 포함한 PaginatedResponse 객체
     */
    @Operation(summary = "GET 회사지정코드마스터 목록 조회", description = "회사지정코드마스터 목록 조회")
    @GetMapping("/mst/list")
    public ResponseObject<CustomCodeMstDto.ListResponse> getCustomCodeMstList(HttpServletRequest req, CustomCodeMstDto.Request param)
    {
        return ResponseUtil.getResponse(req, service.getCustomCodeMstList(param));
    }

    /**
     * 회사지정코드 목록 조회
     *
     * @param param 회사지정코드 검색 조건 포함 객체
     * @return 결과 행 및 페이지 정보를 포함한 PaginatedResponse 객체
     */
    @Operation(summary = "GET 회사지정코드 목록 조회", description = "회사지정코드 목록 조회")
    @GetMapping("/list")
    public ResponseObject<CustomCodeDto.ListResponse> getCustomCodeList(HttpServletRequest req, CustomCodeDto.Request param)
    {
        return ResponseUtil.getResponse(req, service.getCustomCodeList(param));
    }

    /**
     * 회사지정코드 dropbox용 목록 조회
     *
     * @param param 회사지정코드 검색 조건 포함 객체
     * @return 결과 행 및 페이지 정보를 포함한 PaginatedResponse 객체
     */
    @Operation(summary = "GET 회사지정코드 dropbox용 목록 조회", description = "회사지정코드 dropbox용 목록 조회")
    @GetMapping("/code/list")
    public ResponseObject<CustomCodeDto.ListResponse> getCustomCodeCodeList(HttpServletRequest req, CustomCodeDto.Request param)
    {
        return ResponseUtil.getResponse(req, service.getCustomCodeCodeList(param));
    }

    /**
     * 회사지정코드마스터 상세 조회
     *
     * @param customMstSeq 회사지정코드마스터 검색 조건 포함 객체
     * @return 회사지정코드 객체
     */
    @Operation(summary = "GET 회사지정코드마스터 상세 조회", description = "customMstSeq로 회사지정코드마스터 정보 1건 조회")
    @GetMapping("/mst/{customMstSeq}")
    public ResponseObject<CustomCodeMstDto.Info> getCustomCodeMst(HttpServletRequest req
            , @Parameter(description = "회사지정코드마스터일련번호") @PathVariable(name = "customMstSeq") int customMstSeq)
    {
        return ResponseUtil.getResponse(req, service.getCustomCodeMst(customMstSeq));
    }

    /**
     * 회사지정코드 상세 조회
     *
     * @param customSeq 회사지정코드 검색 조건 포함 객체
     * @return 회사지정코드 객체
     */
    @Operation(summary = "GET 회사지정코드 상세 조회", description = "customSeq로 회사지정코드 정보 1건 조회")
    @GetMapping("/{customSeq}")
    public ResponseObject<CustomCodeDto.Info> getCustomCode(HttpServletRequest req
            , @Parameter(description = "회사지정코드일련번호") @PathVariable(name = "customSeq") int customSeq)
    {
        return ResponseUtil.getResponse(req, service.getCustomCode(customSeq));
    }

    /**
     * 회사지정코드마스터 등록
     * @param param 등록할 회사지정코드마스터 객체
     * @return 등록결과 객체
     */
    @Operation(summary = "회사지정코드마스터 등록", description = "회사지정코드마스터 정보를 등록한다.")
    @PostMapping("/mst/add")
    public ResponseObject insertCustomCodeMst(HttpServletRequest req, @RequestBody @Validated(PostMethod.class) CustomCodeMstDto.Info param)
    {
        return ResponseUtil.getResponse(req, service.insertCustomCodeMst(param));
    }

    /**
     * 회사지정코드 등록
     * @param param 등록할 회사지정코드 객체
     * @return 등록결과 객체
     */
    @Operation(summary = "회사지정코드 등록", description = "회사지정코드 정보를 등록한다.")
    @PostMapping("/add")
    public ResponseObject insertCustomCode(HttpServletRequest req, @RequestBody @Validated(PostMethod.class) CustomCodeDto.Info param)
    {
        return ResponseUtil.getResponse(req, service.insertCustomCode(param));
    }

    /**
     * 회사지정코드마스터 수정
     *
     * @param param 수정할 회사지정코드마스터 객체
     * @return 수정결과 객체
     */
    @Operation(summary = "회사지정코드마스터 수정", description = "회사지정코드마스터 정보를 수정한다.")
    @PostMapping("/mst/modify")
    public ResponseObject updateCustomCodeMst(HttpServletRequest req, @RequestBody @Validated(PostMethod.class) CustomCodeMstDto.Info param)
    {
        return ResponseUtil.getResponse(req, service.updateCustomCodeMst(param));
    }

    /**
     * 회사지정코드 수정
     *
     * @param param 수정할 회사지정코드 객체
     * @return 수정결과 객체
     */
    @Operation(summary = "회사지정코드 수정", description = "회사지정코드 정보를 수정한다.")
    @PostMapping("/modify")
    public ResponseObject updateCustomCode(HttpServletRequest req, @RequestBody @Validated(PostMethod.class) CustomCodeDto.Info param)
    {
        return ResponseUtil.getResponse(req, service.updateCustomCode(param));
    }

    /**end
     * 회사지정코드마스터 삭제
     *
     * @param customMstSeq 삭제할 회사지정코드마스터 key
     */
    @Operation(summary = "회사지정코드마스터 삭제", description = "회사지정코드마스터 삭제한다.")
    @PostMapping("/mst/remove/{customMstSeq}")
    public ResponseObject deleteCustomCodeMst(HttpServletRequest req
            , @Parameter(description = "회사지정코드마스터일련번호") @PathVariable(name = "customMstSeq") int customMstSeq)
    {
        return ResponseUtil.getResponse(req, service.deleteCustomCodeMst(customMstSeq));
    }

    /**end
     * 회사지정코드 삭제
     *
     * @param customSeq 삭제할 회사지정코드 key
     */
    @Operation(summary = "회사지정코드 삭제", description = "회사지정코드정보를 삭제한다.")
    @PostMapping("/remove/{customSeq}")
    public ResponseObject deleteCustomCode(HttpServletRequest req
            , @Parameter(description = "회사지정코드일련번호") @PathVariable(name = "customSeq") int customSeq)
    {
        return ResponseUtil.getResponse(req, service.deleteCustomCode(customSeq));
    }

    /**
     * 양식파일 download
     * @param customType 지정코드구분[VOC_TYPE:VOC유형,VOC_ACT_TYPE:VOC처리유형]
     */
    @Operation(summary = "양식파일 다운로드", description = "양식파일을 다운로드한다.")
    @GetMapping("/download/{customType}")
    public ResponseEntity<Resource> downloadFile(HttpServletResponse res, @Parameter(description = "지정코드구분") @PathVariable(name = "customType") String customType)
    {
        String fileNm, filePath;
        if(StringUtils.equals(customType, "VOC_TYPE")) {
            fileNm = "VOC유형업로드양식.xlsx";
            filePath = voctypePath;
        } else {
            fileNm = "처리유형업로드양식.xlsx";
            filePath = acttypePath;
        }
        AttachFileDto.Info file = AttachFileDto.Info.builder().build();
        file.setFilePath(filePath);
        file.setFileNm(fileNm);
        FileUtil.downloadFile(res, file);

        return null;
    }

    @PostMapping("/batchReadExcel")
    @ResponseBody
    public ResponseObject readExcel(MultipartHttpServletRequest mRequest, CustomCodeDto.Info param) throws IOException {
        MultipartFile file = mRequest.getFile("excelFile");

        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        extension = extension.toLowerCase();
        if (!extension.equals("xlsx") && !extension.equals("xls") && !extension.equals("csv")) {
            throw new ApiBusinessException("error.9200","엑셀파일만 업로드 가능합니다.");
        }

        if(StringUtils.isEmpty(param.getCustomType()) || param.getCustomMstSeq() <=0 ) {
        	throw new ApiBusinessException("error.9200","유형그룹정보가 존재하지 않습니다.");
        }

        XSSFWorkbook workbook = null;

        if (extension.equals("xlsx")) {
            workbook = new XSSFWorkbook(file.getInputStream());
        } else if (extension.equals("xls")) {
            workbook = new XSSFWorkbook(file.getInputStream());
        } else if (extension.equals("csv")) {
            workbook = new XSSFWorkbook(file.getInputStream());
        }

        Sheet worksheet = workbook.getSheetAt(0);
        List<CustomCodeDto.Info> dataList = new ArrayList<>();


        int distOrder1=1, distOrder2=1, distOrder3=1;

        String upperCustomCd;
        String beforeLevel1 = "";
        String beforeLevel2 = "";
        String beforeLevel3 = "";
        //엑셀 ROW 루프
        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
        	CustomCodeDto.Info data = CustomCodeDto.Info.builder().build();
            Row row = worksheet.getRow(i);
            if(null != row) {
                if(!ObjectUtils.isEmpty(row.getCell(0)) && !StringUtils.isEmpty(String.valueOf(row.getCell(0))) ) {
                	data = CustomCodeDto.Info.builder().build();
                	data.setCustomType(param.getCustomType());
                	data.setCustomMstSeq(param.getCustomMstSeq());
                    data.setUpperCustomCd("");
                    data.setCustomLevel(1);
                    data.setCustomCd(ConvertTextUtil.getExcelCellValue(row.getCell(0)).toString());
                    //1레벨일때만 등록
                    if(!beforeLevel1.equals(data.getCustomCd())) {
                    	data.setDispOrder(distOrder1++);
                    	distOrder2 = 1; //2레벨 초기화
                    	distOrder3 = 1; //3레벨 초기화
                    	beforeLevel1 = data.getCustomCd();
                    	if(!ObjectUtils.isEmpty(row.getCell(1)) && !StringUtils.isEmpty(String.valueOf(row.getCell(1))) ) {
                    		data.setCustomNm(ConvertTextUtil.getExcelCellValue(row.getCell(1)).toString());
                    	}
                    	dataList.add(data);
                    }

                    upperCustomCd = data.getCustomCd();
                    if(!ObjectUtils.isEmpty(row.getCell(2)) && !StringUtils.isEmpty(String.valueOf(row.getCell(2))) ) {
                    	data = CustomCodeDto.Info.builder().build();
                    	data.setCustomType(param.getCustomType());
                    	data.setCustomMstSeq(param.getCustomMstSeq());
                    	data.setUpperCustomCd(upperCustomCd);
                    	data.setCustomLevel(2);
                    	data.setCustomCd(ConvertTextUtil.getExcelCellValue(row.getCell(2)).toString());
                    	//2레벨일때만 등록
                    	if(!beforeLevel2.equals(data.getCustomCd())) {
                    		data.setDispOrder(distOrder2++);
                    		distOrder3 = 1; //3레벨 초기화
                    		beforeLevel2 = data.getCustomCd();
                    		if(!ObjectUtils.isEmpty(row.getCell(3)) && !StringUtils.isEmpty(String.valueOf(row.getCell(3))) ) {
                    			data.setCustomNm(ConvertTextUtil.getExcelCellValue(row.getCell(3)).toString());
                    		}
                    		dataList.add(data);
                    	}

                    	if(StringUtils.equals(param.getCustomType(), "VOC_TYPE")) { //VOC유형일 경우만 해당
                            upperCustomCd = data.getCustomCd();
                            if (!ObjectUtils.isEmpty(row.getCell(4)) && !StringUtils.isEmpty(String.valueOf(row.getCell(4)))) {
                            	data = CustomCodeDto.Info.builder().build();
                            	data.setCustomType(param.getCustomType());
                            	data.setCustomMstSeq(param.getCustomMstSeq());
                                data.setUpperCustomCd(upperCustomCd);
                                data.setCustomLevel(3);
                                data.setCustomCd(ConvertTextUtil.getExcelCellValue(row.getCell(4)).toString());

                                //3레벨일때만 등록
                            	if(!beforeLevel3.equals(data.getCustomCd())) {
                            		data.setDispOrder(distOrder3++);
                            		beforeLevel3 = data.getCustomCd();
                            		if (!ObjectUtils.isEmpty(row.getCell(5)) && !StringUtils.isEmpty(String.valueOf(row.getCell(5)))) {
                                    	data.setCustomNm(ConvertTextUtil.getExcelCellValue(row.getCell(5)).toString());
                                    }
                                    dataList.add(data);
                            	}

                            }
                        }

                    }

                }

            }
        }

        return ResponseUtil.getResponse(mRequest, service.batchCustomCode(dataList));
    }
}
