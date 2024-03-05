package kr.co.kmac.system.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.kmac.common.util.validation.PostMethod;
import kr.co.kmac.coreframework.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

public class CodeDto {
    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper= false)
    @Schema(description = "코드 조회 request", name="CodeSearchRequest")
    public static class Request extends BaseDto.Request {
        @Schema(description = "코드유형")
        private String codeType;

        @Schema(description = "코드")
        private String code;

        @Schema(description = "코드명")
        private String codeNm;

        @Schema(description = "참조값1")
        private String refVal1;

        @Schema(description = "참조값2")
        private String refVal2;

        @Schema(description = "참조값3")
        private String refVal3;
    }

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper= false)
    @Schema(description = "코드 정보객제", name="CodeInfo")
    public static class Info extends BaseDto.Base {
        @Schema(description = "코드일련번호")
        private int codeSeq;

        @Schema(description = "코드유형")
        @NotBlank(message="코드유형: 필수 항목입니다.", groups=PostMethod.class)
        private String codeType;

        @Schema(description = "코드")
        @NotBlank(message="코드: 필수 항목입니다.", groups=PostMethod.class)
        private String code;

        @Schema(description = "코드명")
        @Size(max=300, message="코드명: 300자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String codeNm;

        @Schema(description = "참조값1")
        @Size(max=100, message="참조값1: 100자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String refVal1;

        @Schema(description = "참조값2")
        @Size(max=100, message="참조값2: 100자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String refVal2;

        @Schema(description = "참조값3")
        @Size(max=100, message="참조값3: 100자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String refVal3;

        @Schema(description = "정렬순서")
        private int dispOrder;

        @Schema(description = "사용여부")
        @Size(max=1, message="사용여부: 1자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String useYn;

    }

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper= false)
    @Schema(description = "dropdown용 공통코드", name="CommonCode")
    public static class CommonCode {
        @Schema(description = "코드명")
        private String name;
        @Schema(description = "코드값")
        private String value;

        @Schema(description = "참조값1")
        private String refVal1;

        @Schema(description = "참조값2")
        private String refVal2;

        @Schema(description = "참조값3")
        private String refVal3;
    }

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper= false)
    @Schema(description = "코드 처리 결과", name="CodeResponse")
    public static class Response extends BaseDto.Result {

    }

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper= false)
    @Schema(description = "코드 조회목록 결과", name="CodeListResponse")
    public static class ListResponse extends BaseDto.ListResult {
        List<Info> list;
    }
}
