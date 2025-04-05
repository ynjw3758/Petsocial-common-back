package com.pets.platform.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class Generation_Services {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
    private static final String BASE_PATH = "Z:\\FileUpload\\";
    private static final String URL_PREFIX = "http://localhost:8080/static/"; // 개발 환경
    
    
    
	public String generation_url(String path) {

		
        if (path == null) return null;
        String relative = path.replace(BASE_PATH, "").replace("\\", "/");
        return URL_PREFIX + relative;
	}

}
