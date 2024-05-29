package kr.co.kmac.voc.dto;

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

public class VocCustDto {
    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper= false)
    @Schema(description = "VOC고객 조회 request", name="VocCustSearchRequest")
    public static class Request extends BaseDto.Request {
        @Schema(description = "고객일련번호")
        private String custSeq;

        @Schema(description = "회사 코드")
        private String companyCd;

        @Schema(description = "고객번호")
        private String custNo;

        @Schema(description = "고객명")
        private String custNm;

        @Schema(description = "고객명_영문")
        private String custNmEn;

        @Schema(description = "전화번호")
        private String telNo;

        @Schema(description = "이메일")
        private String emailAddr;

        @Schema(description = "VOC건수")
        private String vocCnt;

        @Schema(description = "등록시작일")
        private String regDtStart;

        @Schema(description = "등록종료일")
        private String regDtEnd;

        @Schema(description = "익명고객여부")
        private String anonymCustYn;
        
        @Schema(description = "VOC건수정렬여부")
        private String vocCntSortYn;     
    }

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper= false)
    @Schema(description = "VOC고객 정보객제", name="VocCustInfo")
    public static class Info extends BaseDto.Base {

        @Schema(description = "고객일련번호")
        private int custSeq;

        @Schema(description = "회사 코드")
        private String companyCd;

        @Schema(description = "회사명")
        private String companyNm;

        @Schema(description = "고객번호")
        @Size(max=20, message="고객번호: 20자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String custNo;

        @Schema(description = "고객명")
        @Size(max=100, message="고객명: 100자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String custNm;

        @Schema(description = "고객명_영문")
        @Size(max=100, message="고객명_영문: 100자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String custNmEn;

        @Schema(description = "전화번호")
        @Size(max=100, message="전화번호: 100자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String telNo;

        @Schema(description = "이메일")
        @Size(max=500, message="이메일: 500자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String emailAddr;

        @Schema(description = "삭제여부")
        @Size(max=1, message="삭제여부: 1자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String delYn;

        @Schema(description = "VOC건수")
        private int vocCnt;

        @Schema(description = "민감VOC건수")
        private int sensVocCnt;
   
    }

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper= false)
    @Schema(description = "VOC고객 처리 결과", name="VocCustResponse")
    public static class Response extends BaseDto.Result {

    }

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper= false)
    @Schema(description = "VOC고객 조회목록 결과", name="VocCustListResponse")
    public static class ListResponse extends BaseDto.ListResult {
        List<Info> list;
    }
}
