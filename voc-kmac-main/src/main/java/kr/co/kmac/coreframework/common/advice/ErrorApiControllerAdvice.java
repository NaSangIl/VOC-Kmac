package kr.co.kmac.coreframework.common.advice;

import kr.co.kmac.common.util.LogUtil;
import kr.co.kmac.common.util.MessageUtil;
import kr.co.kmac.common.util.ResponseUtil;
import kr.co.kmac.coreframework.common.exception.*;
import kr.co.kmac.coreframework.common.response.ResponseObject;
import kr.co.kmac.coreframework.dto.BaseDto;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mjkim
 * @since 2022/02/23
 */
@RestControllerAdvice()
@Order(Ordered.LOWEST_PRECEDENCE)
public class ErrorApiControllerAdvice {

    private final LogUtil LOG = new LogUtil(this.getClass());
    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorApiControllerAdvice.class);

    /**
     * 공통 응답객체 생성
     * @param request
     * @param errorCode
     * @param errorMessage
     * @param messageArgs
     * @param data
     * @return
     */
    public ResponseObject<Object> getErrorResponse(HttpServletRequest request, String errorCode, String errorMessage, String[] messageArgs, Object data) {
        return ResponseUtil.getErrorResponse(
                request.getRequestURI()
                , BaseDto.Result.builder().rtnCode(errorCode).rtnMessage(errorMessage).messageArgs(messageArgs).build()
                , data);
    }

    /**
     * [200] 비지니스 오류처리 (응답은 200이다)
     * message.properties의 오류 코드를 사용한다.
     * error.properties는 사용하지 않느다.
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(ApiBusinessException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseObject<Object> handlerApiBusinessException(HttpServletRequest request, ApiBusinessException ex) {
        LOG.info(MessageUtil.getMessage(ex.getErrorCode(), ex.getMessageArgs()));
        return getErrorResponse(request, ex.getErrorCode(), ex.getErrorMessage(), ex.getMessageArgs(), ex.getData());
    }



    /**
     * [400] 잘못된 파라미터 타입으로 호출한 경우
     * @param request
     * @param ex
     * @return
     */

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseObject handlerMethodArgumentTypeMismatchException(HttpServletRequest request, MethodArgumentTypeMismatchException ex) {
        LOG.error(ex.getErrorCode(), "잘못되 파라미터 타입 호출", ex);
        return getErrorResponse(request, ex.getErrorCode(), "error.1001", null, null);
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseObject handlerBindException(HttpServletRequest request, BindException ex) {
        LOG.error("1045", "400 BAD_REQUEST 오류", ex);
        String errorCode = "error.1045";
        List<String> messageList = new ArrayList<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            messageList.add(MessageUtil.getMessage(errorCode, new String[] {fieldError.getField(), fieldError.getDefaultMessage()}));
            break;
        }
        return getErrorResponse(request, errorCode, StringUtils.join(messageList, ", "), null, null);
    }

    /**
     * [400] 벨리데이션 오류
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseObject handlerMethodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException ex) {
        LOG.error("1045", "400 @Vaild 유효성 검증 오류", ex);
        String errorCode = "error.1045";
        List<String> messageList = new ArrayList<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            messageList.add(MessageUtil.getMessage(errorCode, new String[] {fieldError.getField(), fieldError.getDefaultMessage()}));
        }
        return getErrorResponse(request, errorCode, StringUtils.join(messageList, ", "), null, null);
    }

    /**
     * [404] Custom 404 오류
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(ApiBadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseObject handlerApiBadRequestException(HttpServletRequest request, ApiBadRequestException ex) {
        LOG.error(ex.getErrorCode(), ex.getErrorMessage(), ex);
        return getErrorResponse(request, ex.getErrorCode(), ex.getMessage(), ex.getMessageArgs(), null);
    }

    /**
     * [404] Path 파라미터 맵핑 실패 오류
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(MissingPathVariableException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseObject<Object> handleMissingPathVariableException (HttpServletRequest request, MissingPathVariableException ex) {
        LOG.error("1040", "404 Path 파라미터 맵핑 실패", ex);
        String errorCode = "error.1040";
        return getErrorResponse(request, errorCode, MessageUtil.getMessage(errorCode, new String[]{ex.getVariableName()}), null, null);
    }

    /**
     * [404] 해당 API 가 존재하지 않을 경우
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseObject handleNoHandlerFoundException(HttpServletRequest request, NoHandlerFoundException ex) {
        LOG.error("1039", "404 해당 API가 존재하지 않습니다.", ex);
        LOGGER.error(ExceptionUtils.getStackTrace(ex));
        return getErrorResponse(request, "error.1039", null, null, null);
    }

    /**
     * [405] Method를 잘못 호출한 경우
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ResponseObject handleHttpRequestMethodNotSupportedException(HttpServletRequest request, HttpRequestMethodNotSupportedException ex) {
        LOG.error("1039", "405 Method를 잘못 호출.", ex);
        return getErrorResponse(request, "error.1039", null, new String[]{StringUtils.join(ex.getSupportedMethods())}, null);
    }

    /**
     * [415] 미디어 타입을 지원하지 않을 경우
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    public ResponseObject handleHttpMediaTypeNotSupportedException(HttpServletRequest request, HttpMediaTypeNotSupportedException ex){
        LOG.error("1007", "415 미디어타입 미지원 오류", ex);
        return getErrorResponse(request, "error.1007", null, null, null);
    }

    /**
     * [500] 공통 Api RuntimeException
     * @param request 서버 HttpServletRequest
     * @param ex 발생 오류
     * @return ResponseErrorObject 오류 객체
     */
    @ExceptionHandler(ApiRuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseObject handleApiRuntimeException(HttpServletRequest request, ApiRuntimeException ex) {
        LOG.error(ex.getErrorCode(), ex.getErrorMessage(), ex);
        return getErrorResponse(request, "error.1007", null, ex.getMessageArgs(), null);
    }

    /**
     * [503]API 폐지 오류
     * @param request 서버 HttpServletRequest
     * @param ex 발생 오류
     * @return ResponseErrorObject 오류 객체
     */
    @ExceptionHandler(ApiVersionRemoveException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ResponseObject handleApiVersionRemoveException(HttpServletRequest request, ApiVersionRemoveException ex) {
        LOG.error(ex.getErrorCode(), ex.getErrorMessage(), ex);
        return getErrorResponse(request, ex.getErrorCode(), null, null, null);
    }

    /**
     * 공통 Api Exception
     * error.properties 사용
     * @param request 서버 HttpServletRequest
     * @param ex 발생 오류
     * @return ResponseErrorObject 오류 객체
     */
    @ExceptionHandler(ApiException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseObject handleApiException(HttpServletRequest request, ApiException ex) {
        LOG.error(ex.getErrorCode(), ex.getErrorMessage(), ex);
        return getErrorResponse(request, ex.getErrorCode(), null, null, null);
    }

    /**
     * [500] 기본 RuntimeException
     * @param request 서버 HttpServletRequest
     * @param ex 발생 오류
     * @return ResponseErrorObject 오류 객체
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseObject handleRuntimeException(HttpServletRequest request, RuntimeException ex) {
        LOG.error("1008", "내부서버 오류", ex);
        return getErrorResponse(request, "error.1008", null, null, null);
    }

    /**
     * 기본 Exception
     * @param request 서버 HttpServletRequest
     * @param ex 발생 오류
     * @return ResponseErrorObject 오류 객체
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseObject handleException(HttpServletRequest request, Exception ex) {
        LOG.error("1008", "내부서버 오류", ex);
        return getErrorResponse(request, "error.1008", null, null, null);
    }
}
