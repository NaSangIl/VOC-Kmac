package kr.co.kmac.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * 
 * @ClassName  DatabaseConfig.java
 * @Description Mysql DB 설정을 위한 설정 class
 * @author mjkim
 * @since 2021. 5. 28.
 *
 */
@Configuration
@EnableTransactionManagement
public class DatabaseConfig {

	@Bean
	@Qualifier("dataSource")
	@ConfigurationProperties(prefix="spring.datasource.hikari")
	public DataSource dataSource() {
		return DataSourceBuilder.create()
				.type(HikariDataSource.class)
				.build();
	}
}
