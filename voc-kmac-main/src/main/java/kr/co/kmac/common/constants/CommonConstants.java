package kr.co.kmac.common.constants;

import org.springframework.stereotype.Component;

/**
 * @author mjkim
 * @class CommonConstants
 * @description 키오스크 API 기본 상수 설정
 * @since 2022/02/25
 */
@Component
public class CommonConstants {
    private CommonConstants() {}
    // 시스템 설정
    public static String SYSTEM_MODE;       // 시스템 디버그 모드

    // 세션정보
    public static final String LOGIN_USER_INFO = "loginUserInfo";

    // LOGIN/LOGIUT COMMON URLs
    public static final String URL_LOGIN          = "/";
    public static final String URL_LOGIN_SUCCESS  = "/main";
    public static final String URL_LOGIN_FAILURE  = URL_LOGIN + "?error=true";
    public static final String URL_LOGOUT         = "/logout";
    public static final String URL_LOGOUT_SUCCESS = URL_LOGIN;

    // 공통 리턴
    public static final String SERVICE_AREA = "voc";
    public static final String SERVICE_URL = "/" + SERVICE_AREA;
    // 공통 응답 코드
    public static final String DEFAULT_KEY = "error.";
    public static final String DEFAULT_SUCCESS = "0000";
    public static final String BASE_SERVER_ERROR = "1008";
    public static final String BASE_MESSAGE_ERROR = "COMMON_99999";

      // 공통 Header
    public static final String X_HTTP_METHOD_OVERRIDE = "X-HTTP-Method-Override";

    public static String VOC_SERVICE_TYPE = "VOC";   // VOC 서비스 유형 [일반, 프리미엄...]

    public static String VOC_RCPT = "등록";
    public static String VOC_SAVE = "변경";
    public static String VOC_APPR = "상신";
    public static String VOC_REJECT = "반려";
    public static String VOC_CANCEL = "취소";
    public static String VOC_DELETE = "삭제";
    public static String VOC_FINISH = "완료";

/*

    @Value("${image.path.poster}")
    public void setMovieImgUrl(String movieImgUrl) {
        MOVIE_IMG_URL = movieImgUrl;
    }

    @Value("${encrypt.aes128key}")
    public void setCommonAes128Key(String commonAes128Key) {
        COMMON_AES128_KEY = commonAes128Key;
    }*/
}
