package kr.co.kmac.coreframework.common.exception;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

/**
 * @author mjkim
 * @class ApiException
 * @description
 * @since 2022/02/24
 */
@Getter
public class ApiException extends Exception {
    private static final long serialVersionUID = 7271738962423046293L;

    private final String errorCode;

    private final String errorMessage;

    public ApiException(String errorCode) {
        super();
        this.errorCode = (StringUtils.contains(errorCode, "error")) ? errorCode : "error." + errorCode;
        this.errorMessage = "";
    }

    public ApiException(String errorCode, Throwable ex) {
        super(ex);
        this.errorCode = (StringUtils.contains(errorCode, "error")) ? errorCode : "error." + errorCode;
        this.errorMessage = ExceptionUtils.getStackTrace(ex);
    }

    public ApiException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = (StringUtils.contains(errorCode, "error")) ? errorCode : "error." + errorCode;
        this.errorMessage = errorMessage;
    }

    public ApiException(String errorCode, String errorMessage, Throwable ex) {
        super(errorMessage, ex);
        this.errorCode = (StringUtils.contains(errorCode, "error")) ? errorCode : "error." + errorCode;
        this.errorMessage = errorMessage;
    }
}
