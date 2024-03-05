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

public class UserDto {
    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper= false)
    @Schema(description = "사용자 조회 request", name="UserSearchRequest")
    public static class Request extends BaseDto.Request {
        @Schema(description = "회사코드")
        private String companyCd;
        @Schema(description = "사용자아이디")
        private String userId;
        @Schema(description = "사용자명")
        private String userNm;
        @Schema(description = "전화번호")
        private String telNo;
        @Schema(description = "이메일주소")
        private String emailAddr;
        @Schema(description = "재직여부")
        private String employmentYn;

    }

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper= false)
    @Schema(description = "사용자 정보객제", name="UserInfo")
    public static class Info extends BaseDto.Base {

        @Schema(description = "사용자일련번호")
        @NotBlank(message="사용자일련번호: 필수 항목입니다.")
        private int userSeq;
        @Schema(description = "회사코드")
        @Size(max=20, message="회사코드: 20자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String companyCd;

        @Schema(description = "회사명")
        private String companyNm;
        @Schema(description = "사용자아이디")
        @NotBlank(message="사용자아이디: 필수 항목입니다.", groups=PostMethod.class)
        @Size(max=50, message="사용자아이디: 50자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String userId;
        @Schema(description = "사용자명")
        @Size(max=100, message="사용자명: 100자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String userNm;
        @Schema(description = "부서코드")
        @Size(max=20, message="부서코드: 20자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String deptCd;
        @Schema(description = "부서명")
        @Size(max=200, message="부서명: 200자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String deptNm;
        @Schema(description = "직급코드")
        @Size(max=20, message="직급코드: 20자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String titleCd;
        @Schema(description = "직급명")
        @Size(max=200, message="직급명: 200자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String titleNm;
        @Schema(description = "입사일자")
        @Size(max=10, message="입사일자: 10자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String joinDt;
        @Schema(description = "재직여부")
        @Size(max=1, message="재직여부: 1자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String employmentYn;
        @Schema(description = "전화번호")
        @Size(max=100, message="전화번호: 100자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String telNo;
        @Schema(description = "휴대전화번호")
        @Size(max=100, message="휴대전화번호: 100자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String cellNo;
        @Schema(description = "이메일주소")
        @Size(max=500, message="이메일주소: 500자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String emailAddr;
        @Schema(description = "비밀번호")
        @Size(max=100, message="비밀번호: 100자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String pw;
        @Schema(description = "비밀번호변경일")
        @Size(max=8, message="비밀번호변경일: 8자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String pwChgDt;
        @Schema(description = "사용자계정잠금여부")
        @Size(max=1, message="사용자계정잠금여부: 1자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String accountLockYn;
        @Schema(description = "사용자권한코드")
        @Size(max=100, message="사용자권한코드: 100자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String userAuthCodes;
        @Schema(description = "사용여부")
        @Size(max=1, message="사용여부: 1자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String useYn;

        @Schema(description = "사용자아이디")
        private String userIdForCheck;
        @Schema(description = "사용자회사로고이미지")
        private String logoImgPath;
    }

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper= false)
    @Schema(description = "사용자 처리 결과", name="UserResponse")
    public static class Response extends BaseDto.Result {

    }

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper= false)
    @Schema(description = "사용자 조회목록 결과", name="UserListResponse")
    public static class ListResponse extends BaseDto.ListResult {
        List<Info> list;
    }

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper= false)
    @Schema(description = "로그인 request", name="LoginSearchRequest")
    public static class LoginRequest {
        @Schema(description = "회사코드")
        @NotBlank(message="회사코드를 입력해 주세요.", groups=PostMethod.class)
        private String companyCd;

        @Schema(description = "사용자아이디")
        @NotBlank(message="사용자아이디를 입력해 주세요.", groups=PostMethod.class)
        private String userId;

        @Schema(description = "패스워드")
        @NotBlank(message="패스워드를 입력해 주세요", groups=PostMethod.class)
        private String pw;

    }

    @Data
    @SuperBuilder
    @EqualsAndHashCode(callSuper= false)
    @Schema(description = "로그인정보", name="LoginInfo")
    public static class LoginInfo extends UserDto.Info {
        @Schema(description = "로그인사용자 권한목록")
        private String[] userAuthCodeList;
    }
}
