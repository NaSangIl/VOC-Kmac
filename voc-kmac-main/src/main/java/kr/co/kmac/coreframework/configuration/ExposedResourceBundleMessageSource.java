package kr.co.kmac.coreframework.configuration;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.Locale;
import java.util.Set;

/**
 * 리소스번들의 키 집합 조회 메서드 및 리소스 파일 변경확인 메서드 포함한 클래스
 *
 * @ClassName  ExposedResourceBundleMessageSource.java
 * @Description 리소스번들의 키 목록 조회 메서드 및 리소스 파일 변경확인 메서드 포함한 클래스
 * @author mjkim
 * @since 2020. 9. 14
 *
 */
public class ExposedResourceBundleMessageSource extends ReloadableResourceBundleMessageSource {

    /**
     * 키 집합 조회
     * @param locale Request 의 로케일
     * @return 리소스번들의 키 집합.
     */
    public Set<String> keySet(Locale locale) {
        return getMergedProperties(locale).getProperties().stringPropertyNames();
    }

    /**
     * 리소스 파일의 최종 변경시간 조회
     * @param locale Request 의 로케일
     * @return 리소스 파일의 최종 변경시간
     */
    public long getFileTimestamp(Locale locale) {
        return getMergedProperties(locale).getFileTimestamp();
    }
}
