package kr.co.kmac.coreframework.intf.internal;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import kr.co.kmac.coreframework.common.response.ResponseStatusObject;

public class BaseInterfaceInternal {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	protected final ObjectMapper mapper = new ObjectMapper();
	
	protected RestTemplate restTemplate;
	
	public BaseInterfaceInternal(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
		mapper.registerModule(new JavaTimeModule());
	}
	
	protected <T> T get(URI uri, Class<T> toValueType) {
		return mapper.convertValue(getForEntity(uri), toValueType);
	}
	
	protected <T> T get(URI uri, TypeReference<T> toValueTypeRef) {
		return mapper.convertValue(getForEntity(uri), toValueTypeRef);
	}
	
	private JsonNode getForEntity(URI uri) {
		return convertValue(restTemplate.getForEntity(uri, ObjectNode.class));
	}
	
	protected <T> T post(URI uri, @Nullable Object param, Class<T> toValueType) {
		return mapper.convertValue(postForEntity(uri, param), toValueType);
	}
	
	protected <T> T post(URI uri, @Nullable Object param, TypeReference<T> toValueTypeRef) {
		return mapper.convertValue(postForEntity(uri, param), toValueTypeRef);
	}
	
	private JsonNode postForEntity(URI uri, @Nullable Object param) {
		return convertValue(restTemplate.postForEntity(uri, param, ObjectNode.class));
	}
	
	private JsonNode convertValue(ResponseEntity<ObjectNode> response) {
		JsonNode data = null;
		
		try {
			ObjectNode node = response.getBody();
			
			ResponseStatusObject responseStatus = mapper.convertValue(node.get("rspStatus"), ResponseStatusObject.class);
			if("0000".equals(responseStatus.getRspCode())) {
				data = node.get("data");
			} else {
				logger.error("REST CALL ERROR uri={}, code={}, message={}", responseStatus.getReqUri(), responseStatus.getRspCode(), responseStatus.getRspMessage());
			}
		} catch(Exception ex) {
			logger.error("REST CALL EXCEPTION", ex);
		}
		
		return data;
	}
}
