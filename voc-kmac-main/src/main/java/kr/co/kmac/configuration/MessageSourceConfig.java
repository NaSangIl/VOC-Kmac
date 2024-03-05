package kr.co.kmac.configuration;

import kr.co.kmac.coreframework.configuration.ExposedResourceBundleMessageSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.Locale;

/**
 * @author mjkim
 * @class MessageSourceConfig
 * @description MessageSourceConfig에 대한 설명 작성
 * @since 2022/02/23
 */
@Configuration
public class MessageSourceConfig implements WebMvcConfigurer {
    @Value("${spring.messages.basename}")
    private String basename;

    @Value("${spring.messages.encoding}")
    private String encoding;

    /**
     * 2020.09.14 Soungjin Park 변경
     * ReloadableResourceBundleMessageSource 상속한 ExposedResourceBundleMessageSource 클래스로 교체.
     * 메시지 키 추출 및 변경여부 확인 메서드 추가된 클래스.
     *
     * @return ExposedResourceBundleMessageSource 객체
     */
    @Bean
    public MessageSource messageSource() {
        ExposedResourceBundleMessageSource messageSource = new ExposedResourceBundleMessageSource();
        messageSource.setBasenames(basename.split(","));
        messageSource.setDefaultEncoding(encoding);

        return messageSource;
    }

    @Bean
    public MessageSourceAccessor messageSourceAccessor() {
        return new MessageSourceAccessor(messageSource());
    }

    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver localeResolver = new CookieLocaleResolver();
        localeResolver.setCookieName("cookieName");
        localeResolver.setDefaultLocale(Locale.KOREA);
        return localeResolver;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        return localeChangeInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}
