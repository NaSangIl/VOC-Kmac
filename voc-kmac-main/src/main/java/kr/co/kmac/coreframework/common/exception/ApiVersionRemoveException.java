package kr.co.kmac.coreframework.common.exception;

import lombok.Getter;
import org.apache.commons.lang3.exception.ExceptionUtils;

/**
 * @author mjkim
 * @class ApiVersionRemoveException
 * @description API 해당 버전 폐기 처리 오류
 * @since 2022/02/24
 */
@Getter
public class ApiVersionRemoveException extends RuntimeException {
    private static final long serialVersionUID = -5463279261040945401L;

    private final String errorCode;

    private final String errorMessage;

    public ApiVersionRemoveException() {
        this.errorCode = "1008";
        this.errorMessage = "";
    }

    public ApiVersionRemoveException(String errorCode) {
        super();
        this.errorCode = errorCode;
        this.errorMessage = "";
    }

    public ApiVersionRemoveException(String errorCode, Throwable ex) {
        super(ex);
        this.errorCode = errorCode;
        this.errorMessage = ExceptionUtils.getStackTrace(ex);
    }

    public ApiVersionRemoveException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public ApiVersionRemoveException(String errorCode, String errorMessage, Throwable ex) {
        super(errorMessage, ex);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
