package com.pets.platform.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@Document(collection = "content_heart")
@NoArgsConstructor
@AllArgsConstructor
public class Content_heart {
	
	@Id
	private String id;
	
	private String userid;
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	
	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	public String getUserid() {
		return userid;
	}
	
	 @Override
	    public String toString() {
	        return "contentid :" +id +" ,userid :" + userid ;
	    } 
	

}
