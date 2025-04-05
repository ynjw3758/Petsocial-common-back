package com.pets.platform.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class MatFollow_Info {
	
	@NotEmpty(message="필수")
	private String id;
	
	@NotEmpty(message="필수")
	private String nickname;
	
	private String img;	

}
