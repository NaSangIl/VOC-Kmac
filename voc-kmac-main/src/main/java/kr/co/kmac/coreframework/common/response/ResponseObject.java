package kr.co.kmac.coreframework.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.kmac.common.constants.CommonConstants;
import kr.co.kmac.common.util.MessageUtil;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author mjkim
 * @class ResponseObject
 * @description 공통 응답 성공 객체
 * @since 2022/02/23
 */
@Data
@Builder(toBuilder = true)
@EqualsAndHashCode(callSuper = false)
public class ResponseObject<T> {
    @Schema(description = "API 서비스 구분")
    @Builder.Default
    private final String serviceArea = CommonConstants.SERVICE_AREA;
    @Schema(description = "API URL 주소")
    private String api;
    @Schema(description = "성공여부")
    @Builder.Default
    private boolean success = true;
    @Schema(description = "메시지 코드")
    @Builder.Default
    private String messageCode = CommonConstants.DEFAULT_SUCCESS;
    @Schema(description = "메시지")
    @Builder.Default
    private String message = MessageUtil.getMessage(CommonConstants.DEFAULT_KEY + CommonConstants.DEFAULT_SUCCESS);
    @Schema(description = "응답 데이터 목록")
    private T data;
}
