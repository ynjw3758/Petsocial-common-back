package com.pets.platform.Service;

import java.util.HashMap;

import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pets.platform.jwt.TokenProvider;
//import com.pets.platform.kafka.Kafka_service;
import com.pets.platform.mapper.User_Mapper;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class Content_Service {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/*
	@Autowired
    private Kafka_service kafka;
	*/
	@Autowired
	private TokenProvider jwt;
	
	@Autowired
	private User_Mapper mapper;
	
	
	public Map<String ,Object>content_Like(Map<String, Object> info , HttpServletRequest request){
		Map<String, Object> result_data = new HashMap<String, Object>();
		Map<String, Object> token_info = new HashMap<String, Object>();
		boolean exp_check= false;
		String access_token="";
		access_token =request.getHeader("Authorization").toString(); 
		logger.info("access_token :" + access_token);
		exp_check = jwt.exp_validateToken(access_token);
        if(exp_check == false) {
			
			logger.info("토큰 시간 만료");
			return result_data;
		}
		token_info =jwt.kakao_validtoken(access_token);
		logger.info("토큰 정보 결과 :" + token_info);
		if(token_info == null) {
			logger.info("access_token 데이터 이상");
			result_data.put("resultcode", 403);
			result_data.put("resultmsg", "access_token is strange");
			result_data.put("resultdata", "");
			return result_data;
		}
		else {
			logger.info("access_token 정보 조회 성공");
			boolean time_check = false;
			time_check = jwt.exp_validateToken(access_token);
			if(time_check == false) {
				logger.info("access_token 시간 만료 재발급");
				result_data.put("resultcode", 401);
				result_data.put("resultmsg", "access_token is finished");
				result_data.put("resultdata", "");
				return result_data;
			}
			else {
				logger.info("access_token 시간 유효");
				boolean id_check = false;
				id_check = mapper.Search_Id(token_info.get("userid").toString());
				if(id_check == true) {
					logger.info("사용자 확인");
					
					//kafka.ContetLike(info);
					
				}
				else {
				   logger.info("사용자 id를 찾을 수없습니다");
				   result_data.put("resultcode", 400);
				   result_data.put("resultmsg", "사용자 id를 찾을 수 없습니다");
				   result_data.put("resultdata", "");
				   return result_data;
				}
				
			}
		}
		
		return result_data;
	}
	
	
	

	
	public Map<String, Object>upload(Map<String, Object> upload_info , List<MultipartFile> files ,
			HttpServletRequest request ,String text , String opendinfo){
		logger.info("파일 업로드 서비스 메소드");
		logger.info("upload_info :" , upload_info);
		logger.info("files :" , files);
		Map<String, Object> token_info = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		boolean exp_check= false;

		String access_token="";
		access_token =request.getHeader("Authorization").toString(); 
		logger.info("access_token :" + access_token);
		exp_check = jwt.exp_validateToken(access_token);
		logger.info("토큰 만료 시간 :" + exp_check);
		if(exp_check == false) {
			
			logger.info("토큰 시간 만료");
			return result;
		}
		token_info =jwt.kakao_validtoken(access_token);
		logger.info("토큰 정보 결과 :" + token_info);
		if(token_info == null) {
			logger.info("access_token 데이터 이상");
			result.put("resultcode", 403);
			result.put("resultmsg", "access_token is strange");
			result.put("resultdata", "");
			return result;
		}
		else {
			logger.info("access_token 정보 조회 성공");
			boolean time_check = false;
			time_check = jwt.exp_validateToken(access_token);
			if(time_check == false) {
				logger.info("access_token 시간 만료 재발급");
				result.put("resultcode", 401);
				result.put("resultmsg", "access_token is finished");
				result.put("resultdata", "");
				return result;
			}
			else {
				logger.info("access_token 시간 유효");
				boolean id_check = false;
				id_check = mapper.Search_Id(token_info.get("userid").toString());
				if(id_check == true) {
					logger.info("사용자 확인");
					if(upload_info ==  null) {
						
						/*
						kafka.FileUpload(null , files, text , access_token , 
								token_info.get("userid").toString() , opendinfo);
								*/
					}
					else {
						/*
						kafka.FileUpload(upload_info , files, text , access_token, 
								token_info.get("userid").toString() , opendinfo);
								*/
					}
					
				}
				else {
				   logger.info("사용자 id를 찾을 수없습니다");
				   result.put("resultcode", 400);
				   result.put("resultmsg", "사용자 id를 찾을 수 없습니다");
				   result.put("resultdata", "");
				   return result;
				}
				
			}
		}
		//Map<String, Object> upload_data = new HashMap<String, Object>();
		
		return result;
		
	}
	
	
	

}
