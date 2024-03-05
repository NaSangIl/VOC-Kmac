package kr.co.kmac.common.util;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * @author GBDEV1
 * @class LogUtil
 * @description 로그 출력 Util
 * @since 2022-11-11
 */
public class LogUtil {

    private Logger LOGGER;

    // 객체 생성
    public LogUtil(Class<?> tClass) {
        LOGGER = LoggerFactory.getLogger(tClass);
    }

    /**
     * 메시지 출력 Format
     * @param key               : 메시지 구분 키값
     * @param code              : 오류코드
     * @param message           : 메시지
     * @param ex                : Exception
     * @return                  : 메시지
     */
    private String format(String key, String code, String message, Exception ex) {
        String result = "";
        try {
            result += "[" + Thread.currentThread().getStackTrace()[3].getMethodName() + "] ";
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(StringUtils.isNotEmpty(code)) result += "[" + code + "]";
        if(StringUtils.isNotEmpty(key)) result += "(" + key + ")";
        if(StringUtils.isNotEmpty(message)) result += " " + message;
        if(ObjectUtils.isNotEmpty(ex)) result +=  " - " + ExceptionUtils.getStackTrace(ex);
        return result;
    }

    /**
     * Info Level 로그 출력
     * @param message           : 메시지
     */
    public void info(String message) {
        LOGGER.info(format(null, null, message, null));
    }

    /**
     * Info Level 로그 출력
     * @param key               : 메시지 구분 키값
     * @param message           : 메시지
     */
    public void info(String key, String message) {
        LOGGER.info(format(key, null, message, null));
    }

    /**
     * Info Debug 로그 출력
     * @param message           : 메시지
     */
    public void debug(String message) {
        LOGGER.debug(format(null, null, message, null));
    }

    /**
     * Info Debug 로그 출력
     * @param key               : 메시지 구분 키값
     * @param message           : 메시지
     */
    public void debug(String key, String message) {
        LOGGER.debug(format(key, null, message, null));
    }


    /**
     * Error Debug 로그 출력
     * @param message           : 메시지
     */
    public void error(String message) {
        LOGGER.debug(format(null, null, message, null));
    }

    /**
     * Error Debug 로그 출력
     * @param code              : 오류코드
     * @param message           : 메시지
     */
    public void error(String code, String message) {
        LOGGER.debug(format(null, code, message, null));
    }

    /**
     * Error Debug 로그 출력
     * @param key               : 메시지 구분 키값
     * @param code              : 오류코드
     * @param message           : 메시지
     */
    public void error(String key, String code, String message) {
        LOGGER.debug(format(key, code, message, null));
    }


    /**
     * Error Debug 로그 출력
     * @param code              : 오류코드
     * @param ex                : Exception
     */
    public void error(String code, Exception ex) {
        LOGGER.debug(format(null, code, null, ex));
    }

    /**
     * Error Debug 로그 출력
     * @param code              : 오류코드
     * @param message           : 메시지
     * @param ex                : Exception
     */
    public void error(String code, String message, Exception ex) {
        LOGGER.debug(format(null, code, message, ex));
    }

    /**
     * Error Debug 로그 출력
     * @param key               : 메시지 구분 키값
     * @param code              : 오류코드
     * @param message           : 메시지
     * @param ex                : Exception
     */
    public void error(String key, String code, String message, Exception ex) {
        LOGGER.debug(format(key, code, message, ex));
    }
}

