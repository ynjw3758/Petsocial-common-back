package com.pets.platform.exception;

import lombok.Data;

@Data
public class ErrorResponse {
	private int status;
	private int resultCode;
    private String message;
    private String comment;
  
	public int getCode() {
		return resultCode;
	}
    
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public void setCode(int resultCode) {
		this.resultCode = resultCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
    
}
