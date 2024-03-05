package kr.co.kmac.coreframework.common.response;

import java.time.LocalDateTime;
import java.util.List;

public class ResponseStatusObject {
	
	private String rspCode;
	
	private String rspMessage;
	
	private LocalDateTime rspTime;
	
	private String reqUri;
	
	private List<ResponseErrorObject> errors;
	
	public String getRspCode() {
		return rspCode;
	}

	public void setRspCode(String rspCode) {
		this.rspCode = rspCode;
	}

	public String getRspMessage() {
		return rspMessage;
	}

	public void setRspMessage(String rspMessage) {
		this.rspMessage = rspMessage;
	}

	public LocalDateTime getRspTime() {
		return rspTime;
	}

	public void setRspTime(LocalDateTime rspTime) {
		this.rspTime = rspTime;
	}

	public String getReqUri() {
		return reqUri;
	}

	public void setReqUri(String reqUri) {
		this.reqUri = reqUri;
	}

	public List<ResponseErrorObject> getErrors() {
		return errors;
	}

	public void setErrors(List<ResponseErrorObject> errors) {
		this.errors = errors;
	}
}
