package kr.co.kmac.coreframework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.kmac.common.constants.CommonConstants;
import kr.co.kmac.common.util.LoginInfoUtil;
import kr.co.kmac.system.dto.UserDto;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.Serializable;

/**
 * @author mjkim
 * @class BaseDto
 * @description 기본 파라미터
 * @since 2022/03/04
 */
public class BaseDto {

    @Data
    @SuperBuilder(toBuilder = true)
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper= false)
    @Schema(description = "결과 파라미터")
    public static class Result implements Serializable {
        @Builder.Default
        @Schema(description = "결과코드")
        private String rtnCode = CommonConstants.DEFAULT_SUCCESS;
        @Schema(description = "결과수")
        private int rtnCnt;
        @Schema(description = "결과key")
        private int rtnKey;
        @Schema(description = "결과메시지")
        private String rtnMessage;
        @Schema(description = "메시지 파라미터")
        private String[] messageArgs;

    }

    @Data
    @SuperBuilder(toBuilder = true)
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper= false)
    @Schema(description = "결과 파라미터")
    public static class ListResult implements Serializable {
        @Schema(description = "현재페이지번호")
        int pageNumber;

        @Schema(description = "페이지당 출력할 데이터수")
        int pageSize;

        @Schema(description = "조회된 데이터수")
        long totalCount;
    }

    @Data
    @SuperBuilder(toBuilder = true)
    //@NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper= false)
    public static class Base implements Serializable {
        @Schema(description = "등록자")
        private String regUserNo;

        @Schema(description = "등록자명")
        private String regUserNm;

        @Schema(description = "등록일시")
        private String regDt;

        @Schema(description = "수정자")
        private String modUserNo;

        @Schema(description = "수정자명")
        private String modUserNm;

        @Schema(description = "수정일시")
        private String modDt;

        @Schema(description = "insert 후 반환되는 키")
        private int returnKey;

        @Schema(description = "로그인회사코드")
        private String loginCompanyCd;

        public Base() {
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            try {
                UserDto.LoginInfo sessionInfo = LoginInfoUtil.getLoginUserInfo(requestAttributes.getRequest());
                this.regUserNo = String.valueOf(sessionInfo.getUserSeq());
                this.modUserNo = String.valueOf(sessionInfo.getUserSeq());
                this.loginCompanyCd = String.valueOf(sessionInfo.getCompanyCd());
            } catch (Exception e) {
                this.regUserNo = null;
                this.modUserNo = null;
                this.loginCompanyCd = null;
            }
        }
    }

    @Data
    @SuperBuilder(toBuilder = true)
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper= false)
    public static class Request implements Serializable {
        @Schema(description = "검색시작번호")
        int offset;
        @Schema(description = "페이지당 출력할 데이터수")
        int length;

        @Schema(description = "로그인회사코드")
        String loginCompanyCd;
    }
}
