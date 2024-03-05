package kr.co.kmac.coreframework.common.exception;

import kr.co.kmac.common.util.MessageUtil;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

/**
 * @author mjkim
 * @class ApiRuntimeException
 * @description 마이크로 서비스에서 사용하는 Core Exception
 * @since 2022/02/23
 */
@Getter
public class ApiRuntimeException  extends RuntimeException{
    private static final long serialVersionUID = -2553907066689878420L;

    private final String errorCode;

    private final String errorMessage;

    private final String[] messageArgs;

    public ApiRuntimeException() {
        super();
        this.errorCode = "0000";
        this.errorMessage = "";
        this.messageArgs = null;
    }

    public ApiRuntimeException(String errorCode) {
        super();
        this.errorCode = (StringUtils.contains(errorCode, "error")) ? errorCode : "error." + errorCode;
        this.errorMessage = "";
        this.messageArgs = null;
    }

    public ApiRuntimeException(String errorCode, String[] messageArgs) {
        super();
        this.errorCode = (StringUtils.contains(errorCode, "error")) ? errorCode : "error." + errorCode;
        this.errorMessage = MessageUtil.getMessage(errorCode, messageArgs);
        this.messageArgs = messageArgs;
    }

    public ApiRuntimeException(String errorCode, Throwable ex) {
        super(ex);
        this.errorCode = (StringUtils.contains(errorCode, "error")) ? errorCode : "error." + errorCode;
        this.errorMessage = ExceptionUtils.getStackTrace(ex);
        this.messageArgs = null;
    }

    public ApiRuntimeException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = (StringUtils.contains(errorCode, "error")) ? errorCode : "error." + errorCode;
        this.errorMessage = errorMessage;
        this.messageArgs = null;
    }

    public ApiRuntimeException(String errorCode, String errorMessage, Throwable ex) {
        super(errorMessage, ex);
        this.errorCode = (StringUtils.contains(errorCode, "error")) ? errorCode : "error." + errorCode;
        this.errorMessage = errorMessage;
        this.messageArgs = null;
    }

    public ApiRuntimeException(String errorCode, String errorMessage, String[] messageArgs) {
        super(errorMessage);
        this.errorCode = (StringUtils.contains(errorCode, "error")) ? errorCode : "error." + errorCode;
        this.errorMessage = errorMessage;
        this.messageArgs = messageArgs;
    }

    public ApiRuntimeException(ApiRuntimeException e) {
        super(e.getErrorMessage());
        this.errorCode = (StringUtils.contains(e.getErrorCode(), "error")) ? e.getErrorCode() : "error." + e.getErrorCode();
        this.errorMessage = e.getErrorMessage();
        this.messageArgs = e.getMessageArgs();
    }
}