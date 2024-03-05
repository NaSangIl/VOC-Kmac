package kr.co.kmac.coreframework.common.exception;

import kr.co.kmac.common.util.MessageUtil;
import lombok.Getter;
import org.apache.commons.lang3.exception.ExceptionUtils;

/**
 * @author mjkim
 * @class ApiBusinessException
 * @description 비즈니스 오류 (시스템에서 발생한 오류가 아닌 비즈니스에 따른 오류이기 때문에
 *              HttpStatus.OK (200)로 처리한다.)
 * @since 2022/03/18
 */
@Getter
public class ApiBusinessException extends RuntimeException {
    private static final long serialVersionUID = -2553907066689878420L;

    private final String errorCode;

    private final String errorMessage;

    private final String[] messageArgs;

    private final Object data;

    public ApiBusinessException() {
        super();
        this.errorCode = "0000";
        this.errorMessage = "";
        this.messageArgs = null;
        this.data = null;
    }

    public ApiBusinessException(String errorCode) {
        super();
        this.errorCode = errorCode;
        this.errorMessage = "";
        this.messageArgs = null;
        this.data = null;
    }

    public ApiBusinessException(String errorCode, Object data) {
        super();
        this.errorCode = errorCode;
        this.errorMessage = "";
        this.messageArgs = null;
        this.data = data;
    }

    public ApiBusinessException(String errorCode, String[] messageArgs) {
        super();
        this.errorCode = errorCode;
        this.errorMessage = MessageUtil.getMessage(errorCode, messageArgs);
        this.messageArgs = messageArgs;
        this.data = null;
    }

    public ApiBusinessException(String errorCode, Throwable ex) {
        super(ex);
        this.errorCode = errorCode;
        this.errorMessage = ExceptionUtils.getStackTrace(ex);
        this.messageArgs = null;
        this.data = null;
    }

    public ApiBusinessException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.messageArgs = null;
        this.data = null;
    }

    public ApiBusinessException(String errorCode, String errorMessage, Throwable ex) {
        super(errorMessage, ex);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.messageArgs = null;
        this.data = null;
    }

    public ApiBusinessException(String errorCode, String errorMessage, String[] messageArgs) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.messageArgs = messageArgs;
        this.data = null;
    }

    public ApiBusinessException(String errorCode, String errorMessage, Object data) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.messageArgs = null;
        this.data = data;
    }

    public ApiBusinessException(ApiBusinessException e) {
        super(e.getErrorMessage());
        this.errorCode = e.getErrorCode();
        this.errorMessage = e.getErrorMessage();
        this.messageArgs = e.getMessageArgs();
        this.data = e.getData();
    }

    public ApiBusinessException(String errorCode, String[] messageArgs, Object data) {
        super();
        this.errorCode = errorCode;
        this.errorMessage = MessageUtil.getMessage(errorCode, messageArgs);
        this.messageArgs = messageArgs;
        this.data = data;
    }

}
