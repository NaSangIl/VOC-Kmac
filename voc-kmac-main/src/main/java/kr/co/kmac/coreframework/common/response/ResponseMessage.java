package kr.co.kmac.coreframework.common.response;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author mjkim
 * @class Response
 * @description 메시지 필터
 * @since 2022/02/23
 */
@Data
@Builder(toBuilder = true)
@EqualsAndHashCode(callSuper = false)
public class ResponseMessage {
    private boolean success;
    private String messageCode;
    private String message;
}
