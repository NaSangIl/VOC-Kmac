package kr.co.kmac.common.util;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * @author mjkim
 * @class MessageUtil
 * @description 메시지 조회 Util
 * @since 2022/02/23
 */
@Component
public class MessageUtil {
    private static Logger LOGGER = LoggerFactory.getLogger(MessageUtil.class);

    private static MessageSourceAccessor messageSourceAccessor;

    @Autowired
    public void setMessageSourceAccessor(MessageSourceAccessor messageSourceAccessor) {
        MessageUtil.messageSourceAccessor = messageSourceAccessor;
    }

    /**
     * 현재 설정된 Locale 정보 조회
     * @return
     */
    public static Locale getLocale() {
        LocaleContext localeContext = LocaleContextHolder.getLocaleContext();
        return ObjectUtils.isEmpty(localeContext) ? Locale.getDefault() : localeContext.getLocale();
    }

    /**
     * 현재 Locale이 checkLocale과 일치하는가?
     * @param checkLocale           : 체크할 Locale 정보
     * @return
     */
    public static boolean checkLocale(String checkLocale) {
        return getLocale().getLanguage().equals(new Locale(checkLocale).getLanguage());
    }

    /**
     * 메시지 코드의 코드 리턴
     * @param messageKey
     * @return
     */
    public static String getCode(String messageKey) {
        return messageKey.split("[.]")[1];
    }

    /**
     * Error 메시지의 메시지 코드의 코드 리턴 (500|서버오류 => 500 리턴)
     * @param messageKey
     * @return
     */
    public static String getSpCode(String messageKey) {
        String message = "";
        try {
            message = messageSourceAccessor.getMessage(messageKey);
            return message.split("[|]")[0];
        } catch (NoSuchMessageException ignored) {
        }
        return message;
    }

    /**
     * 해당 코드의 메시지 리턴
     * @param key
     * @return
     */
    public static String getMessage(String key) {
        String message = "";
        try {
            message = messageSourceAccessor.getMessage(key);
            if(message.contains("|")) message = message.split("[|]")[1];
        } catch (NoSuchMessageException ignored) {
        }
        return message;
    }

    /**
     * 해당 코드의 메시지 리턴
     * @param key
     * @param locale
     * @return
     */
    public static String getMessage(String key, String locale) {
        String message = "";
        try {
            message = messageSourceAccessor.getMessage(key, new Locale(locale));
            if(message.contains("|")) message = message.split("[|]")[1];
        } catch (NoSuchMessageException ignored) {
        }
        return message;
    }

    /**
     * 해당 코드의 메시지 리턴
     * @param key
     * @param messageArgs
     * @return
     */
    public static String getMessage(String key, String[] messageArgs) {
        String message = "";
        try {
            message = messageSourceAccessor.getMessage(key, messageArgs);
            if(message.contains("|")) message = message.split("[|]")[1];
        } catch (NoSuchMessageException ignored) {
        }
        return message;
    }

    /**
     * 해당 코드의 메시지 리턴
     * @param key
     * @param messageArgs
     * @param locale
     * @return
     */
    public static String getMessage(String key, String[] messageArgs, String locale) {
        String message = "";
        try {
            message = messageSourceAccessor.getMessage(key, messageArgs, new Locale(locale));
            if(message.contains("|")) message = message.split("[|]")[1];
        } catch (NoSuchMessageException ignored) {
        }
        return message;
    }
}
