package kr.co.kmac.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@OpenAPIDefinition(
        info = @Info(title = "KMAC VOC SYSTEM API 명세서",
                description = "",
                version = "v1"))
@Configuration
public class SwaggerConfiguration implements WebMvcConfigurer {
	protected final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Value("${server.domain}")
	String serverUrl;
	
    @Bean
    OpenApiCustomiser openApiCustomiser() {
    	
    	Server serverInfo = new Server();
    	serverInfo.setUrl(serverUrl);
    	serverInfo.setDescription("API 명세서");
    	
    	List<Server> sl = new ArrayList<>();
    	sl.add(serverInfo);
    	
        return openApi -> {
            openApi.getInfo().version("v3");
            openApi.servers(sl);
        };
    }
    
    @Bean
    public GroupedOpenApi customTestOpenAPi() {

    	String[] paths = {"/**"};
        
        return GroupedOpenApi
                .builder()
                .group("All Api")
                .pathsToMatch(paths)
                .addOpenApiCustomiser(openApiCustomiser()).build();
    }
    
}
