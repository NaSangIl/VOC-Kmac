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

public class AuthDto {
    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper= false)
    @Schema(description = "권한 조회 request", name="AuthSearchRequest")
    public static class Request extends BaseDto.Request {
        @Schema(description = "권한코드")
        private String authCd;
        @Schema(description = "권한명")
        private String authNm;
        @Schema(description = "사용여부")
        private String useYn;
        @Schema(description = "회사코드")
        private String companyCd;
        @Schema(description = "시스템관리자여부")
        private boolean sysAdmin;
    }

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper= false)
    @Schema(description = "권한 정보객제", name="AuthInfo")
    public static class Info extends BaseDto.Base {
        @Schema(description = "권한일련번호")
        private int authSeq;

        @Schema(description = "권한코드")
        @NotBlank(message="권한코드: 필수 항목입니다.", groups=PostMethod.class)
        @Size(max=20, message="권한코드: 20자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String authCd;
        @Schema(description = "권한명")
        @Size(max=200, message="권한명: 200자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String authNm;
        @Schema(description = "권한설명")
        @Size(max=500, message="권한설명: 500자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String authDesc;
        @Schema(description = "사용여부")
        @Size(max=1, message="사용여부: 1자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String useYn;
        @Schema(description = "시스템권한여부")
        @Size(max=1, message="시스템권한여부: 1자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String systemYn;

        @Schema(description = "권한사용자수")
        private int authUserCnt;

        @Schema(description = "회사코드")
        private String companyNm;
    }

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper= false)
    @Schema(description = "권한 처리 결과", name="AuthResponse")
    public static class Response extends BaseDto.Result {

    }

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper= false)
    @Schema(description = "권한 조회목록 결과", name="AuthListResponse")
    public static class ListResponse extends BaseDto.ListResult {
        List<Info> list;
    }
}
