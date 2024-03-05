package kr.co.kmac.voc.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.kmac.common.util.LoginInfoUtil;
import kr.co.kmac.coreframework.dto.BaseDto;
import kr.co.kmac.system.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

public class VocHistDto {
    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper= false)
    @Schema(description = "VOC이력 조회 request", name="VocHistSearchRequest")
    public static class Request extends BaseDto.Request {
        @Schema(description = "VOC일련번호")
        private int vocSeq;
    }

    @Data
    @SuperBuilder
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper= false)
    @Schema(description = "VOC이력 정보객제", name="VocHistInfo")
    public static class Info {

        @Schema(description = "이력일련번호")
        private int histSeq;

        @Schema(description = "VOC일련번호")
        private int vocSeq;

        @Schema(description = "이력구분코드")
        private String histTypeCd;

        @Schema(description = "이력구분명")
        private String histTypeNm;

        @Schema(description = "이력내용")
        private String histCont;

        @Schema(description = "등록자번호")
        private String regUserNo;

        @Schema(description = "등록자명")
        private String regUserNm;
        @Schema(description = "등록일시")
        private String regDt;

        public Info() {
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            try {
                UserDto.LoginInfo sessionInfo = LoginInfoUtil.getLoginUserInfo(requestAttributes.getRequest());
                this.regUserNo = String.valueOf(sessionInfo.getUserSeq());
            } catch (Exception e) {
                this.regUserNo = null;
            }
        }
    }

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper= false)
    @Schema(description = "VOC이력 처리 결과", name="VocHistResponse")
    public static class Response extends BaseDto.Result {

    }

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper= false)
    @Schema(description = "VOC이력 조회목록 결과", name="VocHistListResponse")
    public static class ListResponse extends BaseDto.ListResult {
        List<Info> list;
    }
}
