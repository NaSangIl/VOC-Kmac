package kr.co.kmac.system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.kmac.coreframework.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

public class AuthMenuDto {
    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper= false)
    @Schema(description = "권한메뉴매핑 조회 request", name="AuthMenuSearchRequest")
    public static class Request extends BaseDto.Request {
        @Schema(description = "권한코드")
        private String authCd;

        @Schema(description = "회사코드")
        private String companyCd;
    }

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper= false)
    @Schema(description = "권한메뉴매핑 정보객제", name="AuthMenuInfo")
    public static class Info extends BaseDto.Base {
        @Schema(description = "메뉴일련번호")
        private int menuSeq;
        @Schema(description = "권한코드")
        private String authCd;
        @Schema(description = "회사 코드")
        private String companyCd;
        @Schema(description = "회사명")
        private String companyNm;
        @Schema(description = "메뉴아이디")
        private String menuId;
        @Schema(description = "메뉴명")
        private String menuNm;
        @Schema(description = "메뉴레벨")
        private int menuLevl;
        @Schema(description = "메뉴URL")
        private String menuUrl;
        @Schema(description = "상위메뉴 아이디")
        private String parentMenuId;
        @Schema(description = "메뉴여부")
        private String menuYn;
        @Schema(description = "메뉴일련번호")
        private int[] menuSeqs;
    }

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper= false)
    @Schema(description = "권한메뉴매핑 처리 결과", name="AuthMenuResponse")
    public static class Response extends BaseDto.Result {

    }

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper= false)
    @Schema(description = "권한메뉴매핑 조회목록 결과", name="AuthMenuListResponse")
    public static class ListResponse extends BaseDto.ListResult {
        List<Info> list;
    }
}
