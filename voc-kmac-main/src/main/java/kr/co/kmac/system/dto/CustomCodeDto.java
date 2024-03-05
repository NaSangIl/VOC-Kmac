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

public class CustomCodeDto {
    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper= false)
    @Schema(description = "회사지정코드 조회 request", name="CustomCodeSearchRequest")
    public static class Request extends BaseDto.Request {
        @Schema(description = "회사지정코드마스터일련번호")
        private int customMstSeq;

        @Schema(description = "회사일련번호")
        private int companySeq;

        @Schema(description = "회사코드")
        private String companyCd;

        @Schema(description = "회사지정코드 구분:[VOC_TYPE:VOC유형,VOC_ACT_TYPE:VOC처리유형]")
        private String customType;

        @Schema(description = "회사지정코드레벨")
        private int customLevel;

        @Schema(description = "상위회사지정코드코드")
        private String upperCustomCd;
    }

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper= false)
    @Schema(description = "회사지정코드 정보객제", name="CustomCodeInfo")
    public static class Info extends BaseDto.Base {
        @Schema(description = "회사지정코드일련번호")
        private int customSeq;

        @Schema(description = "회사일련번호")
        private int companySeq;

        @Schema(description = "회사지정코드마스터일련번호")
        private int customMstSeq;

        @Schema(description = "회사지정코드 구분:[VOC_TYPE:VOC유형,VOC_ACT_TYPE:VOC처리유형]")
        private String customType;

        @Schema(description = "회사지정코드")
        @NotBlank(message="코드: 필수 항목입니다.", groups= PostMethod.class)
        @Size(max=20, message="코드: 20자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String customCd;

        @Schema(description = "회사지정코드명")
        @NotBlank(message="유형명: 필수 항목입니다.", groups= PostMethod.class)
        @Size(max=100, message="유형명: 100자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String customNm;

        @Schema(description = "회사지정코드레벨")
        private int customLevel;

        @Schema(description = "상위회사지정코드코드")
        private String upperCustomCd;

        @Schema(description = "정렬순서")
        private int dispOrder;

        @Schema(description = "사용여부")
        private String useYn;

        @Schema(description = "회사지정코드코드:대")
        private String customCd1;
        @Schema(description = "회사지정코드명:대")
        private String customNm1;
        @Schema(description = "회사지정코드코드:중")
        private String customCd2;
        @Schema(description = "회사지정코드명:중")
        private String customNm2;
        @Schema(description = "회사지정코드코드:소")
        private String customCd3;
        @Schema(description = "회사지정코드명:소")
        private String customNm3;
    }

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper= false)
    @Schema(description = "회사지정코드 처리 결과", name="CustomCodeResponse")
    public static class Response extends BaseDto.Result {

    }

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper= false)
    @Schema(description = "회사지정코드 조회목록 결과", name="CustomCodeListResponse")
    public static class ListResponse extends BaseDto.ListResult {
        List<Info> list;
    }
}
