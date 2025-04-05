package com.pets.platform.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@Document(collection = "single_comment")
@NoArgsConstructor
@AllArgsConstructor
public class Single_Comment {
	
	@Id
	private String id;
	
	private String user;
	private String text_info;
	private String uuid;
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	
	public void setUserid(String user) {
		this.user = user;
	}
	
	public String getUserid() {
		return user;
	}
	
	public void setTextinfo(String text_info) {
		this.text_info = text_info;
	}
	
	public String getTextinfo() {
		return text_info;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String getUuid() {
		return uuid;
	}
	
	 @Override
	    public String toString() {
	        return "id :" + id +" ,userid :" + user+" ,text :" + text_info + " ,uuid :" + uuid;
	    } 

}
