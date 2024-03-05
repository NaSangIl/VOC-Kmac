package kr.co.kmac.common.util;

import kr.co.kmac.common.constants.CommonConstants;
import kr.co.kmac.coreframework.common.response.ResponseMessage;
import kr.co.kmac.coreframework.common.response.ResponseObject;
import kr.co.kmac.coreframework.dto.BaseDto;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author mjkim
 * @class ResponseUtil
 * @description 응답값 공통 Util
 * @since 2022/02/23
 */
@Component
public class ResponseUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResponseUtil.class);

    /**
     * 기본 성공 응답
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResponseObject<T> getResponse(HttpServletRequest request, T data) {
        return getResponse(request, data, null);
    }

    /**
     * 메시지 설정 성공 응답
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResponseObject<T> getResponse(HttpServletRequest request, T data, String messageKey) {

        if(StringUtils.isEmpty(messageKey)) {
            return ResponseObject.<T>builder()
                    .api(request.getRequestURI())
                    .data(data)
                    .build();
        }
        else {
            ResponseMessage responseMessage = getResponseMessage(messageKey, null);
            return ResponseObject.<T>builder()
                    .api(request.getRequestURI())
                    .success(CommonConstants.DEFAULT_SUCCESS.equals(responseMessage.getMessageCode()) ? true : false )
                    .messageCode(responseMessage.getMessageCode())
                    .message(responseMessage.getMessage())
                    .data(data)
                    .build();
        }
    }

    public static <T> ResponseObject<T> getErrorResponse(String apiUrl, BaseDto.Result result, T data) {
        String defaultErrorCode = "error.1008";
        ResponseMessage responseMessage = null;
        String errorCode = result.getRtnCode();
        String errorMessage = result.getRtnMessage();
        String[] messageArgs = result.getMessageArgs();

        if(StringUtils.isEmpty(errorCode)) errorCode = defaultErrorCode;

        if(StringUtils.contains(errorCode, "error.")) {
            responseMessage = ResponseUtil.getResponseMessage(errorCode, messageArgs);
            if(!StringUtils.isEmpty(errorMessage)) {
                responseMessage.setMessage(errorMessage);
            }
        }
        else {
            responseMessage = ResponseMessage.builder()
                    .success(CommonConstants.DEFAULT_SUCCESS.equals(responseMessage.getMessageCode()) ? true : false )
                    .messageCode(errorCode)
                    .message(MessageUtil.getMessage(errorCode, messageArgs))
                    .build();

            // 메시지 코드 부분에 코드가 아닌 메시지 추가한 경우
            String message = MessageUtil.getMessage(errorCode);

            if(!StringUtils.isEmpty(errorMessage)) {
                responseMessage.setMessageCode(CommonConstants.BASE_MESSAGE_ERROR);
                responseMessage.setMessage(errorMessage);
//                responseMessage.setMessage(errorMessage + "(errorCode : " + errorCode + ")");
            }
        }
        return ResponseObject.<T>builder()
                .api(apiUrl)
                .success(responseMessage.isSuccess())
                .messageCode(responseMessage.getMessageCode())
                .message(responseMessage.getMessage())
                .data(data)
                .build();
    }

    /**
     *  메시지 Object 생성 로직
     * @param messageKey    : 메시지 키
     * @param messageArgs   : 메시지 파라미터
     * @return
     */
    private static ResponseMessage getResponseMessage(String messageKey, String[] messageArgs) {

        ResponseMessage responseMessage = null;

        try {
            String messageCode = MessageUtil.getCode(messageKey);
            String message = MessageUtil.getMessage(messageKey, messageArgs);

            responseMessage = ResponseMessage.builder()
                    .messageCode(messageCode)
                    .message(message)
                    .build();

        } catch (NoSuchMessageException nsme) {
            LOGGER.error(ExceptionUtils.getStackTrace(nsme));
        } catch (Exception e) {
            LOGGER.error(ExceptionUtils.getStackTrace(e));
        }
        return responseMessage;
    }
}
