package kr.co.kmac.coreframework.common.exception;

import kr.co.kmac.common.util.MessageUtil;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

/**
 * @author mjkim
 * @class ApiBadRequestException
 * @description 파라미터 체크 오류에 사용하는 Exception
 * @since 2022/04/13
 */
@Getter
public class ApiBadRequestException extends RuntimeException {
    private static final long serialVersionUID = 6126777411794399948L;

    private final String errorCode;

    private final String errorMessage;

    private final String[] messageArgs;

    public ApiBadRequestException() {
        super();
        this.errorCode = "0000";
        this.errorMessage = "";
        this.messageArgs = null;
    }

    public ApiBadRequestException(String errorCode) {
        super();
        this.errorCode = (StringUtils.contains(errorCode, "error")) ? errorCode : "error." + errorCode;
        this.errorMessage = "";
        this.messageArgs = null;
    }

    public ApiBadRequestException(String errorCode, String[] messageArgs) {
        super();
        this.errorCode = (StringUtils.contains(errorCode, "error")) ? errorCode : "error." + errorCode;
        this.errorMessage = MessageUtil.getMessage(errorCode, messageArgs);
        this.messageArgs = messageArgs;
    }

    public ApiBadRequestException(String errorCode, Throwable ex) {
        super(ex);
        this.errorCode = (StringUtils.contains(errorCode, "error")) ? errorCode : "error." + errorCode;
        this.errorMessage = ExceptionUtils.getStackTrace(ex);
        this.messageArgs = null;
    }

    public ApiBadRequestException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = (StringUtils.contains(errorCode, "error")) ? errorCode : "error." + errorCode;
        this.errorMessage = errorMessage;
        this.messageArgs = null;
    }

    public ApiBadRequestException(String errorCode, String errorMessage, Throwable ex) {
        super(errorMessage, ex);
        this.errorCode = (StringUtils.contains(errorCode, "error")) ? errorCode : "error." + errorCode;
        this.errorMessage = errorMessage;
        this.messageArgs = null;
    }

    public ApiBadRequestException(String errorCode, String errorMessage, String[] messageArgs) {
        super(errorMessage);
        this.errorCode = (StringUtils.contains(errorCode, "error")) ? errorCode : "error." + errorCode;
        this.errorMessage = errorMessage;
        this.messageArgs = messageArgs;
    }

    public ApiBadRequestException(ApiBadRequestException e) {
        super(e.getErrorMessage());
        this.errorCode = (StringUtils.contains(e.getErrorCode(), "error")) ? e.getErrorCode() : "error." + e.getErrorCode();
        this.errorMessage = e.getErrorMessage();
        this.messageArgs = e.getMessageArgs();
    }
}
