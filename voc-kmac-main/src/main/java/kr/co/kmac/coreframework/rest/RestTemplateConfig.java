package kr.co.kmac.coreframework.rest;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * 
 * @ClassName  RestTemplateConfig.java
 * @Description CustomRestTemplateCustomize 클래스를 이용하여 RestTemplateBuilder 생성 
 * @author mjkim
 * @since 2021. 5. 28.
 *
 */
@Configuration
public class RestTemplateConfig {
	
	@Value("${rest-api.timeout.read:3000}")
	private long READ_TIMEOUT;
	
	@Value("${rest-api.timeout.connect:3000}")
	private long CONNECT_TIMEOUT;
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
		return restTemplateBuilder
				.setConnectTimeout(Duration.ofMillis(CONNECT_TIMEOUT))
				.setReadTimeout(Duration.ofMillis(READ_TIMEOUT))
				.build();
	}
}
