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

public class CustomCodeMstDto {
    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper= false)
    @Schema(description = "회사지정코드 마스터 조회 request", name="CustomCodeMstSearchRequest")
    public static class Request extends BaseDto.Request {
        @Schema(description = "회사지정코드 구분:[VOC_TYPE:VOC유형,VOC_ACT_TYPE:VOC처리유형]")
        private String customType;
        @Schema(description = "업종코드")
        private String industryCd;
        @Schema(description = "회사지정코드그룹명")
        private String customNm;
        @Schema(description = "사용여부")
        private String useYn;
        @Schema(description = "회사지정코드그룹코드")
        private String customGrpCd;
    }

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper= false)
    @Schema(description = "회사지정코드마스터 정보객제", name="CustomCodeMstInfo")
    public static class Info extends BaseDto.Base {
        @Schema(description = "회사지정코드마스터일련번호")
        private int customMstSeq;

        @Schema(description = "회사지정코드 구분:[VOC_TYPE:VOC유형,VOC_ACT_TYPE:VOC처리유형]")
        private String customType;

        @Schema(description = "유형그룹코드")
        private String customGrpCd;

        @Schema(description = "유형그룹명")
        @NotBlank(message="유형그룹명: 필수 항목입니다.", groups= PostMethod.class)
        @Size(max=100, message="유형그룹명: 100자를 넘을 수 없습니다.", groups=PostMethod.class)private String customGrpNm;

        @Schema(description = "업종코드")
        @NotBlank(message="업종코드: 필수 항목입니다.", groups= PostMethod.class)
        @Size(max=20, message="업종코드: 20자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String industryCd;

        @Schema(description = "업종명")
        private String industryNm;

        @Schema(description = "유형그룹설명")
        @NotBlank(message="유형그룹설명: 필수 항목입니다.", groups= PostMethod.class)
        @Size(max=1000, message="유형그룹설명: 1000자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String customDesc;

        @Schema(description = "사용여부")
        private String useYn;
    }

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper= false)
    @Schema(description = "회사지정코드마스터 처리 결과", name="CustomCodeResponse")
    public static class Response extends BaseDto.Result {

    }

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper= false)
    @Schema(description = "회사지정코드마스터 조회목록 결과", name="CustomCodeListResponse")
    public static class ListResponse extends BaseDto.ListResult {
        List<Info> list;
    }
}
