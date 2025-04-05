package com.pets.platform.exception;

import lombok.Data;

@Data
public class SampleErrorResponse {
	private int status;
    private String message;
    private String comment;
  
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
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
