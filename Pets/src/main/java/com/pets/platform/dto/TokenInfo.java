package com.pets.platform.dto;

import lombok.Data;

@Data
public class TokenInfo {
	
	private String accessToeken;
	private String RefreshToeken;
	
	public void SetAccessToken(String accessToeken) {
		this.accessToeken = accessToeken;
	}
	
	public void SetRefreshToken(String RefreshToken) {
		this.RefreshToeken = RefreshToken;
	}
	
	public String getAccessToken() {
		return accessToeken;
	}
	
	public String getRefreshToken() {
		return RefreshToeken;
	}
	
	
	

}
