package kr.co.kmac.voc.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.kmac.common.dto.AttachFileDto;
import kr.co.kmac.common.util.validation.DeleteMethod;
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

public class VocMstDto {
    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper= false)
    @Schema(description = "VOC 조회 request", name="VocSearchRequest")
    public static class Request extends BaseDto.Request {
        @Schema(description = "회사 코드")
        private String companyCd;
        @Schema(description = "VOC 아이디")
        public String vocId;
        @Schema(description = "고객명")
        public String custNm;
        @Schema(description = "VOC구분코드")
        public String vocCaseCd;
        @Schema(description = "VOC유형코드_대")
        public String vocTypeCd1;
        @Schema(description = "VOC유형코드_중")
        public String vocTypeCd2;
        @Schema(description = "VOC유형코드_소")
        public String vocTypeCd3;
        @Schema(description = "접수채널코드")
        public String rcptChnnCd;
        @Schema(description = "발생장소코드")
        public String sourceCd;
        @Schema(description = "VOC제목")
        public String vocTitle;
        @Schema(description = "처리유형코드1")
        public String vocActTypeCd1;
        @Schema(description = "처리유형코드2")
        public String vocActTypeCd2;
        @Schema(description = "등록자")
        public String regUserNm;
        @Schema(description = "처리자")
        public String vocActUserNm;
        @Schema(description = "VOC상태코드")
        public String vocStatusCd;
        @Schema(description = "삭제여부")
        private String delYn;
        @Schema(description = "등록검색시작일:YYYY-MM-DD")
        private String regDtStart;
        @Schema(description = "등록검색종료일:YYYY-MM-DD")
        private String regDtEnd;
        @Schema(description = "처리검색시작일:YYYY-MM-DD")
        private String vocActDtStart;
        @Schema(description = "처리검색종료일:YYYY-MM-DD")
        private String vocActDtEnd;
        @Schema(description = "민감/특이여부")
        private String sensSpecYn;
        @Schema(description = "고객일련번호")
        private int custSeq;

    }

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper= false)
    @Schema(description = "VOC 정보객제", name="VocInfo")
    public static class Info extends BaseDto.Base {

        @Schema(description = "VOC일련번호")
        @NotBlank(message = "VOC일련번호(voc_seq): 필수 항목입니다.", groups= DeleteMethod.class)
        private int vocSeq;

        @NotBlank(message = "회사 코드: 필수 항목입니다.")
        @Schema(description = "회사 코드")
        private String companyCd;

        @Schema(description = "VOC 아이디")
        private String vocId;

        @Schema(description = "고객일련번호")
        private int custSeq;

        @Schema(description = "VOC구분코드")
        @Size(max=20, message="VOC구분코드: 20자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String vocCaseCd;

        @Schema(description = "VOC구분코드명")
        private String vocCaseNm;

        @Schema(description = "VOC유형코드_대")
        @Size(max=20, message="VOC유형코드_대: 20자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String vocTypeCd1;

        @Schema(description = "VOC유형코드명_대")
        private String vocTypeNm1;

        @Schema(description = "VOC유형코드_중")
        @Size(max=20, message="VOC유형코드_중: 20자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String vocTypeCd2;

        @Schema(description = "VOC유형코드명_중")
        private String vocTypeNm2;

        @Schema(description = "VOC유형코드_소")
        @Size(max=20, message="VOC유형코드_소: 20자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String vocTypeCd3;

        @Schema(description = "VOC유형코드명_소")
        private String vocTypeNm3;

        @Schema(description = "접수채널코드")
        @Size(max=20, message="접수채널코드: 20자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String rcptChnnCd;

        @Schema(description = "접수채널코드명")
        private String rcptChnnNm;

        @Schema(description = "발생장소코드")
        @Size(max=20, message="발생장소코드: 20자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String sourceCd;

        @Schema(description = "발생장소코드명")
        private String sourceNm;

        @Schema(description = "발생일시")
        private String sourceDt;

        @Schema(description = "고객회신요청여부")
        @Size(max=1, message="고객회신요청여부: 1자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String custReplyYn;

        @Schema(description = "고객회신요청방법코드")
        @Size(max=20, message="고객회신요청방법코드: 20자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String custReplyCd;

        @Schema(description = "고객회신요청방법코드명")
        private String custReplyNm;

        @Schema(description = "VOC제목")
        @Size(max=1000, message="VOC제목: 1000자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String vocTitle;

        @Schema(description = "VOC내용")
        private String vocCont;

        @Schema(description = "즉시처리여부")
        @Size(max=1, message="즉시처리여부: 1자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String immeActYn;

        @Schema(description = "처리유형코드1")
        @Size(max=20, message="처리유형코드1: 20자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String vocActTypeCd1;
        @Schema(description = "처리유형코드2")
        @Size(max=20, message="처리유형코드2: 20자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String vocActTypeCd2;
        @Schema(description = "처리유형코드명1")
        private String vocActTypeNm1;
        @Schema(description = "처리유형코드명2")
        private String vocActTypeNm2;

        @Schema(description = "처리일시")
        private String vocActDt;

        @Schema(description = "처리자")
        @Size(max=20, message="처리자: 20자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String vocActUserNo;

        @Schema(description = "처리자명")
        private String vocActUserNm;

        @Schema(description = "처리내용")
        private String vocActCont;

        @Schema(description = "VOC상태코드")
        @Size(max=20, message="VOC상태코드: 20자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String vocStatusCd;

        @Schema(description = "VOC상태코드명")
        private String vocStatusNm;

        @Schema(description = "익명고객여부")
        @Size(max=1, message="익명고객여부: 1자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String anonymCustYn;

        @Schema(description = "민감/특이여부")
        @Size(max=1, message="민감/특이여부: 1자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String sensSpecYn;

        @Schema(description = "반려사유")
        @Size(max=4000, message="반려사유: 4000자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String rejectMemo;

        @Schema(description = "삭제여부")
        @Size(max=1, message="삭제여부: 1자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String delYn;

        @Schema(description = "VOC유형코드명")
        private String vocTypeNm;

        // 고객정보-------------------
        @Schema(description = "고객명")
        @Size(max=100, message="고객명: 100자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String custNm;

        @Schema(description = "고객번호")
        @Size(max=20, message="고객번호: 20자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String custNo;

        @Schema(description = "전화번호")
        @Size(max=100, message="전화번호: 100자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String telNo;

        @Schema(description = "이메일")
        @Size(max=500, message="이메일: 500자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String emailAddr;
        //----------------------------

        @Schema(description = "첨부파일목록:voc파일")
        List<AttachFileDto.Info> fileList1;

        @Schema(description = "첨부파일목록:voc처리파일")
        List<AttachFileDto.Info> fileList2;

        @Schema(description = "고객정보")
        VocCustDto.Info vocCustInfo;
    }

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper= false)
    @Schema(description = "VOC현황객제", name="StatusInfo")
    public static class StatusInfo {
        @Schema(description = "불만접수건수")
        private int complaintCnt;
        @Schema(description = "칭찬접수건수")
        private int complimentCnt;
        @Schema(description = "제안접수건수")
        private int suggestionCnt;
        @Schema(description = "문의접수건수")
        private int inquiryCnt;
        @Schema(description = "평균처리기간")
        public float actPeriodAvg;
    }

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper= false)
    @Schema(description = "VOC 처리 결과", name="VocResponse")
    public static class Response extends BaseDto.Result {

    }

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper= false)
    @Schema(description = "VOC 조회목록 결과", name="VocListResponse")
    public static class ListResponse extends BaseDto.ListResult {
        List<Info> list;
    }


    @Data
    @SuperBuilder
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper= false)
    @Schema(description = "VOC 상세조회 결과", name="VocDetailResponse")
    public static class DetailResponse extends Info {
        @Schema(description = "VOC이력정보")
        List<VocHistDto.Info> histList;
    }

}
