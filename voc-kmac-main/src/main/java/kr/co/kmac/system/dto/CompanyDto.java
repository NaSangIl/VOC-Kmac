package kr.co.kmac.system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.kmac.common.util.validation.PostMethod;
import kr.co.kmac.coreframework.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

public class CompanyDto {
    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper= false)
    @Schema(description = "회사 조회 request", name="CompanySearchRequest")
    public static class Request extends BaseDto.Request {
        @Schema(description = "회사코드")
        private String companyCd;
        @Schema(description = "회사명_국문")
        private String companyNm;
        @Schema(description = "회사명_영문")
        private String companyNmEn;
        @Schema(description = "사용시작일From")
        private String useStartDtFr;
        @Schema(description = "사용시작일To")
        private String useStartDtTo;
        @Schema(description = "사용종료일From")
        private String useEndDtFr;
        @Schema(description = "사용종료일To")
        private String useEndDtTo;
        @Schema(description = "사용여부")
        private String useYn;
        @Schema(description = "회사코드:중복확인용")
        private String companyCdForCheck;
    }

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper= false)
    @Schema(description = "회사 정보객제", name="CompanyInfo")
    public static class Info extends BaseDto.Base {
        @Schema(description = "회사일련번호")
        private int companySeq;
        @Schema(description = "회사코드")
        private String companyCd;

        @Schema(description = "회사명")
        @NotBlank(message="회사명: 필수 항목입니다.", groups=PostMethod.class)
        @Size(max=300, message="회사명: 300자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String companyNm;
        @Schema(description = "회사명_영문")
        @Size(max=300, message="회사명_영문: 300자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String companyNmEn;
        @Schema(description = "사용시작일")
        @Size(max=8, message="사용시작일: 8자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String useStartDt;
        @Schema(description = "사용종료일")
        @Size(max=8, message="사용종료일: 8자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String useEndDt;
        @Schema(description = "회사로고경로")
        @Size(max=500, message="회사로고경로: 500자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String companyLogoPath;

        @Schema(description = "회사로고파일명")
        @Size(max=100, message="회사로고파일명: 100자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String companyLogoFileNm;

        @Schema(description = "사용여부")
        @Size(max=1, message="사용여부: 1자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String useYn;
    }

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper= false)
    @Schema(description = "회사 처리 결과", name="CompanyResponse")
    public static class Response extends BaseDto.Result {

    }

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper= false)
    @Schema(description = "회사 조회목록 결과", name="CompanyListResponse")
    public static class ListResponse extends BaseDto.ListResult {
        List<Info> list;
    }
}
