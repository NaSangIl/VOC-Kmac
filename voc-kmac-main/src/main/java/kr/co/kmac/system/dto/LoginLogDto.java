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

public class LoginLogDto {
    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper= false)
    @Schema(description = "로그인로그 조회 request", name="LoginLogSearchRequest")
    public static class Request extends BaseDto.Request {
        @Schema(description = "회사코드")
        private String companyCd;
        @Schema(description = "사용자아이디")
        private String userId;
        @Schema(description = "로그인검색시작일:YYYY-MM-DD")
        private String loginDtStart;

        @Schema(description = "로그인검색종료일:YYYY-MM-DD")
        private String loginDtEnd;

    }

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper= false)
    @Schema(description = "로그인로그 정보객제", name="LoginLogInfo")
    public static class Info extends BaseDto.Base {
        @Schema(description = "시스템로그인로그일련번호")
        @NotBlank(message="시스템로그인로그일련번호: 필수 항목입니다.")
        private int loginLogSeq;

        @NotBlank(message = "회사 코드: 필수 항목입니다.", groups= PostMethod.class)
        @Schema(description = "회사 코드")
        private String companyCd;

        @Schema(description = "사용자아이디")
        @NotBlank(message="사용자아이디: 필수 항목입니다.", groups=PostMethod.class)
        @Size(max=50, message="사용자아이디: 50자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String userId;
        @Schema(description = "로그인일시")
        @NotBlank(message="로그인일시: 필수 항목입니다.", groups=PostMethod.class)
        private String loginDt;
        @Schema(description = "로그아웃일시")
        @NotBlank(message="로그아웃일시: 필수 항목입니다.", groups=PostMethod.class)
        private String logoutDt;
        @Schema(description = "아이피주소")
        @Size(max=100, message="아이피주소: 100자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String ipAddr;
        @Schema(description = "사용자디바이스")
        @Size(max=100, message="사용자디바이스: 100자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String device;
        @Schema(description = "로그인성공여부")
        @Size(max=1, message="로그인성공여부: 1자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String successYn;
        @Schema(description = "오류메시지")
        @Size(max=1000, message="오류메시지: 1000자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String errMsg;
    }

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper= false)
    @Schema(description = "로그인로그 처리 결과", name="LoginLogResponse")
    public static class Response extends BaseDto.Result {

    }

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper= false)
    @Schema(description = "로그인로그 조회목록 결과", name="LoginLogListResponse")
    public static class ListResponse extends BaseDto.ListResult {
        List<Info> list;
    }
}
