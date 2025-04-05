package com.pets.platform.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@Document(collection = "content_info")
@NoArgsConstructor
@AllArgsConstructor
public class Content_info {
	
	//@Id
	private String id;
	
	private String comment;
	
	private String favorite;
	
	public void setFavorite(String favorite) {
		this.favorite = favorite;
	}
	
	public String getFavorite() {
		return favorite;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	

	
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public String getCommnet() {
		return comment;
	}
	
	 @Override
	    public String toString() {
	        return "id :" + id  +" ,comment :" + comment;
	    } 
	


}
