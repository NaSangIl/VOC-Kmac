package kr.co.kmac.statistics.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.kmac.coreframework.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

public class StatisticsDto {
    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper= false)
    @Schema(description = "현황 조회 request")
    public static class Request {
        @Schema(description = "회사 코드")
        private String companyCd;
        @Schema(description = "기간구분")
        private String periodType;
        @Schema(description = "등록년도")
        private String regiYear;
        @Schema(description = "등록월")
        private String regiMonth;
        @Schema(description = "등록검색시작일:YYYY-MM-DD")
        private String regDtStart;
        @Schema(description = "등록검색종료일:YYYY-MM-DD")
        private String regDtEnd;
        @Schema(description = "접수채널코드")
        public String rcptChnnCd;
        @Schema(description = "VOC유형코드_대")
        public String vocTypeCd1;
        @Schema(description = "VOC유형코드_중")
        public String vocTypeCd2;
        @Schema(description = "VOC유형코드_소")
        public String vocTypeCd3;

        @Schema(description = "VOC유형코드1 조건여부")
        public String vocTypeCd1Yn;
        @Schema(description = "VOC유형코드2 조건여부")
        public String vocTypeCd2Yn;
        @Schema(description = "VOC유형코드3 조건여부")
        public String vocTypeCd3Yn;

        @Schema(description = "처리유형코드1")
        public String vocActTypeCd1;
        @Schema(description = "처리유형코드2")
        public String vocActTypeCd2;
        @Schema(description = "처리유형코드2 조건여부")
        public String vocActTypeCd2Yn;
    }

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper= false)
    @Schema(description = "현황 정보객제")
    public static class Info extends BaseDto.Base {

        @Schema(description = "기간구분:년도/년월/일자")
        private String periodType;
        @Schema(description = "VOC유형코드_대")
        public String vocTypeNm1;
        @Schema(description = "VOC유형코드_중")
        public String vocTypeNm2;
        @Schema(description = "VOC유형코드_소")
        public String vocTypeNm3;

        @Schema(description = "접수채널명")
        public String rcptChnnNm;

        @Schema(description = "처리유형명1")
        public String vocActTypeNm1;
        @Schema(description = "처리유형명2")
        public String vocActTypeNm2;

        @Schema(description = "처리기간")
        public String actPeriod;

        @Schema(description = "전체접수건수")
        private int totalCnt;
        @Schema(description = "전체전년대비")
        private int totalYoyCnt;

        @Schema(description = "칭찬접수건수")
        private int complimentCnt;
        @Schema(description = "칭찬접수비율")
        private float complimentRate;
        @Schema(description = "칭찬전년대비")
        private int complimentYoyCnt;

        @Schema(description = "불만접수건수")
        private int complaintCnt;
        @Schema(description = "불만비율")
        private float complaintRate;
        @Schema(description = "불만전년대비")
        private int complaintYoyCnt;

        @Schema(description = "제안접수건수")
        private int suggestionCnt;
        @Schema(description = "제안비율")
        private float suggestionRate;
        @Schema(description = "제안전년대비")
        private int suggestionYoyCnt;

        @Schema(description = "문의접수건수")
        private int inquiryCnt;
        @Schema(description = "문의비율")
        private float inquiryRate;
        @Schema(description = "문의전년대비")
        private int inquiryYoyCnt;
    }

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper= false)
    @Schema(description = "현황 조회목록 결과")
    public static class ListResponse extends BaseDto.ListResult {
        List<Info> list;
    }
}
