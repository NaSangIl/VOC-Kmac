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

public class MenuDto {
    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper= false)
    @Schema(description = "메뉴 조회 request", name="MenuSearchRequest")
    public static class Request extends BaseDto.Request {
        @Schema(description = "메뉴아이디")
        private String menuId;
        @Schema(description = "메뉴명")
        private String menuNm;
        @Schema(description = "메뉴URL")
        private String menuUrl;
    }

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper= false)
    @Schema(description = "메뉴 정보객제", name="MenuInfo")
    public static class Info extends BaseDto.Base {
        @Schema(description = "메뉴일련번호")
        @NotBlank(message="메뉴일련번호: 필수 항목입니다.")
        private int menuSeq;

        @Schema(description = "회사명")
        private String companyNm;
        @Schema(description = "메뉴아이디")
        @NotBlank(message="메뉴아이디: 필수 항목입니다.", groups=PostMethod.class)
        @Size(max=10, message="메뉴아이디: 10자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String menuId;
        @Schema(description = "메뉴명")
        @NotBlank(message="메뉴명: 필수 항목입니다.", groups=PostMethod.class)
        @Size(max=100, message="메뉴명: 100자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String menuNm;
        @Schema(description = "메뉴레벨")
        private int menuLevl;
        @Schema(description = "메뉴URL")
        @Size(max=300, message="메뉴URL: 300자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String menuUrl;
        @Schema(description = "메뉴설명")
        @Size(max=500, message="메뉴설명: 500자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String menuDesc;
        @Schema(description = "상위메뉴 아이디")
        @Size(max=10, message="상위메뉴 아이디: 10자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String parentMenuId;
        @Schema(description = "상위메뉴명")
        private String parentMenuNm;
        @Schema(description = "메뉴순서")
        private int menuOrdr;
        @Schema(description = "사용여부")
        @Size(max=1, message="사용여부: 1자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String useYn;
        @Schema(description = "메뉴여부")
        @Size(max=1, message="메뉴여부: 1자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String menuYn;
    }

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper= false)
    @Schema(description = "메뉴구성 정보객제")
    public static class MenuInfo {
        @Schema(description = "메뉴일련번호")
        private int menuSeq;
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
        @Schema(description = "상위메뉴명")
        private String parentMenuNm;
        @Schema(description = "메뉴순서")
        private int menuOrdr;
        @Schema(description = "서브메뉴목록")
        List<Info> subMenulist;
    }

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper= false)
    @Schema(description = "권한메뉴 조회 request", name="MenuAuthSearchRequest")
    public static class AuthRequest {
        @Schema(description = "회사코드")
        private String companyCd;
        @Schema(description = "로그인사용자 권한목록")
        private String[] userAuthCodeList;
        @Schema(description = "메뉴URL")
        private String menuUrl;
        @Schema(description = "메뉴여부")
        private String menuYn;
    }
    @Data
    @SuperBuilder
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper= false)
    @Schema(description = "메뉴 처리 결과", name="MenuResponse")
    public static class Response extends BaseDto.Result {

    }

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper= false)
    @Schema(description = "메뉴 조회목록 결과", name="MenuListResponse")
    public static class ListResponse extends BaseDto.ListResult {
        List<Info> list;
    }
}
