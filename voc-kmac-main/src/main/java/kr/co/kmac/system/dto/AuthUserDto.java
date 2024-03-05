package kr.co.kmac.system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.kmac.coreframework.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

public class AuthUserDto {
    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper= false)
    @Schema(description = "권한사용자매핑 조회 request", name="AuthUserSearchRequest")
    public static class Request extends BaseDto.Request {
        @Schema(description = "권한일련번호")
        private int authSeq;
        @Schema(description = "회사코드")
        private String companyCd;
    }

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper= false)
    @Schema(description = "권한사용자매핑 정보객제", name="AuthUserInfo")
    public static class Info extends BaseDto.Base {
        @Schema(description = "권한일련번호")
        private int authSeq;

        @Schema(description = "사용자일련번호")
        private int userSeq;

        @Schema(description = "회사코드")
        private String companyCd;

        @Schema(description = "사용자아이디")
        private String userId;

        @Schema(description = "사용자명")
        private String userNm;

        @Schema(description = "부서명")
        private String deptNm;

        @Schema(description = "직급명")
        private String titleNm;
    }

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper= false)
    @Schema(description = "권한사용자매핑 처리 결과", name="AuthUserResponse")
    public static class Response extends BaseDto.Result {

    }

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper= false)
    @Schema(description = "권한사용자매핑 조회목록 결과", name="AuthUserListResponse")
    public static class ListResponse extends BaseDto.ListResult {
        List<Info> list;
    }
}
