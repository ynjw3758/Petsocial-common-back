package com.pets.platform.Service;

import java.io.UnsupportedEncodingException;



import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;


import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pets.platform.certification.Certification;
import com.pets.platform.jwt.TokenProvider;
import com.pets.platform.mapper.Setting_Mapper;
import com.pets.platform.mapper.User_Mapper;

import jakarta.servlet.http.Cookie;




@Service
public class UserInfo_Service {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private Setting_Mapper setting;
	
	@Autowired
	private User_Mapper userinfo;
	
    @Autowired
    private PasswordEncoder passwordEncoder; 
    
    @Autowired
    private TokenProvider Token;
    
    @Autowired
    private Certification certifi;
    
    public Map<String, Object> follower(Map<String, Object> info){
    	
    	Map<String,Object> response = new HashMap<String, Object>();
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String fileDate = sdf.format(date);
    	if(info.get("type").equals("connect")) {
    		logger.info("팔로우 신청 :");
    		info.put("relationdate", fileDate);
    		userinfo.Follower_connect(info);
    		
    	}
    	else {
    		logger.info("팔로우 취소");
    		logger.info("넘어온 데이터 :" + info);
    		userinfo.Folloer_cancel(info);
    		
    		
    	}
    	response.put("resultcode", 200);
    	response.put("resultmsg", "팔로워 취소 완료");
    	
    	return response;
    }
    
    //비밀번호 재설정
    public Map<String, Object> reset_password(Map<String, Object> user_info){
    	logger.info("비밀번호 리셋");
    	Map<String, Object> result_data = new HashMap<String , Object>();
    	String new_pass = "";
    	new_pass =passwordEncoder.encode(user_info.get("New_password").toString());
    	logger.info("새로운 비밀번호 : " + new_pass);
    	user_info.put("New_password", new_pass);
    	userinfo.reset_password(user_info);
    	
    	
    	return result_data;
    	}
    
    //핸드폰 인증 
    public Map<String, Object> findpw(Map<String, Object> info){
    	logger.info("사용자 핸드폰인증번호 ");
    	Map<String, Object> result_info = new HashMap<String, Object>();
    	Map<String, Object> receive_info = new HashMap<String, Object>();
    	
    	try {
			receive_info = certifi.SendMassage(info);
			logger.info("인증번호 결과 :" + receive_info);
			result_info.put("resultcode" , 200);
			result_info.put("resulmsg" , "인증번호 발송");
			result_info.put("certifi_number", receive_info.get("number").toString());
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block\
			logger.error("error : " + e.getClass()+  " , " + e.getMessage());
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			logger.error("error : " + e.getClass()+  " , " + e.getMessage());
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			logger.error("error : " + e.getClass()+  " , " + e.getMessage());
			e.printStackTrace();
		}

    	
    	return result_info;
    	
    }
    
    
	//사용자 id찾기
	public Map<String , Object> findid(Map<String , Object> info){
		
	Map<String , Object> result_data = new HashMap<String , Object>();
	      logger.info("사용자 id찾기");
	      result_data = userinfo.findId(info);
	      logger.info("사용자 아이디 조회 결과 :" + result_data);
	      if(result_data !=null ) {
	    	  logger.info("아이디 찾기 성공");
	    	  result_data.put("resultCode", 200);
	    	  result_data.put("resultMsg", "아이디 찾기 완료");
	    	  result_data.put("id", result_data.get("id").toString());
	    	  
	      }
	      else {
	    	  logger.info("아이디 찾기 실패");
	    	  result_data.put("resultCode", 400);
	    	  result_data.put("resultMsg", "아이디 찾기 실패");
	    	  result_data.put("id", "null");
	      }
	return result_data;
	}
	
	//일반 로그인
	public Map<String , Object> Login(Map<String , Object> info){
		Map<String, Object> result_data = new HashMap<String, Object>();
		Map<String, Object> search_data = new HashMap<String, Object>();
		Map<String, Object> log_data = new HashMap<String, Object>();
		logger.info("로그인 메소드");
		
		boolean pass_check= false;
		String Hash_Password = "";
		Hash_Password = passwordEncoder.encode(info.get("password").toString());
		info.put("hash_pass", Hash_Password);
		logger.info("뎅터 조회 : " + info);
		log_data=userinfo.search_pass_id(info);
		logger.info("로그인 데이터 조회 : " + log_data);
		
		if(log_data == null) {
			logger.info("아이디가 존재하지않습니다");
			result_data.put("resultCode", 400);
			result_data.put("resultMsg", "아이디 및 패스워드가 존재하지않습니다");
			return result_data;
		}
		
		pass_check=passwordEncoder.matches(info.get("password").toString(), log_data.get("password").toString());
		if(pass_check== true) {
			logger.info("로그인 성공 ");
			Map<String, Object> tokeninfo = new HashMap<String, Object>();
			tokeninfo = Token.CreateToken(info.get("id").toString());
			logger.info("토큰 결과  : " + tokeninfo);
			
			
			result_data.put("resultCode", 200);
			result_data.put("resultMsg", "로그인 성공 ");
			result_data.put("id", log_data.get("id").toString());
			result_data.put("NickName", log_data.get("nickname").toString());
			result_data.put("access_token", tokeninfo.get("access_token").toString());
			result_data.put("refresh_token", tokeninfo.get("refresh_token").toString());
			//result_data.put("Cookie", tokeninfo.get("Cookie").toString());
			result_data.put("exp", tokeninfo.get("exp").toString());
		}
		else {
			logger.info("비밀번호 불일치 ");
			result_data.put("resultCode", 400);
			result_data.put("resultMsg", "아이디 및 패스워드가 존재하지않습니다");
		}

		
	return result_data;
	}
	
	//중복 이메일 체크
	public Map<String, Object> check_email(String email){
		Map<String , Object> result_response = new HashMap<String, Object>();
		logger.info("이메일 중복 확인");
		boolean email_check = false;
		email_check =userinfo.Search_email(email);
		logger.info("중복 아이디 확인 결과 :" + email_check);
		if(email_check == true) {
			logger.info("이메일 중복");
			result_response.put("resultCode", 400);
			result_response.put("resultMsg", "중복  이메일");
		}
		else {
			logger.info("이메일 사용 가능");
			result_response.put("resultCode", HttpStatus.OK);
			result_response.put("resultMsg", "이메일 사용 가능");
		}
		
		return result_response;
	}
	
    //중복 아이디 체크
	public Map<String, Object> check_id(String id) {
		Map<String , Object> result_response = new HashMap<String, Object>();
		logger.info("중복 아이디 검색");
		logger.info("중복 확인 id: " + id);
		boolean id_check = false;
		id_check =userinfo.Search_Id(id);
		logger.info("중복 아이디 확인 결과 :" + id_check);
		if(id_check == true) {
			logger.info("중복 아이디");
			result_response.put("resultCode", 400);
			result_response.put("resultMsg", "중복 id");
		}
		else {
			logger.info("아이디 사용 가능");
			result_response.put("resultCode", HttpStatus.OK);
			result_response.put("resultMsg", "아이디 사용 가능");
		}
		
		return result_response;
	}
	//중복 닉네임 체크
	public Map<String, Object> check_nickname(String nickname) {
		Map<String , Object> result_response = new HashMap<String, Object>();
		logger.info("중복 닉네임 검색");
		boolean nickname_check = false;
		nickname_check = userinfo.Search_Nickname(nickname);
		logger.info("중복 닉네임 확인 결과 : " + nickname_check);
		
		if(nickname_check == true) {
			logger.info("중복 닉네임");
			result_response.put("resultCode", 400);
			result_response.put("resultMsg", "중복 닉네임");
		}
		else {
			logger.info("닉네임 사용 가능");
			result_response.put("resultCode", 200);
			result_response.put("resultMsg", "닉네임 사용 가능");
		}
		return result_response;
	}
	
	//회원가입
	public Map<String, Object> receive_info(Map<String, Object> user_info){
		Map<String , Object> result_response = new HashMap<String, Object>();
		Map<String , Object> setting_Init = new HashMap<String, Object>();
		logger.info("회원가입 메소드 ");
		logger.info("회원가입 비밀번호 암호화 전 : " + user_info.get("Password").toString());
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String create_date = sdf.format(date);
		
		String Hash_Password = "";
		Hash_Password = passwordEncoder.encode(user_info.get("Password").toString());
		logger.info("암호화 후 데이터 : " + Hash_Password);
		user_info.put("Password", Hash_Password);
		user_info.put("log_kind", "N");
		String uuid = uuid();
		logger.info("uuid : " + uuid);
		user_info.put("uuid", uuid);
		user_info.put("Phone", "01097977894");
		user_info.put("createdate", create_date);
		setting_Init.put("userid", user_info.get("Id").toString());
		setting_Init.put("date", create_date);
		userinfo.Sign_insert(user_info);
		setting.Initsetting(setting_Init);
		 result_response.put("resultCode", 200);
		 result_response.put("resultMsg", "회원 가입 완료");;
		return result_response;
	}
	
	//친구 리스트 가져오기
	public Map<String ,Object> freindly_list(String id){
		List<Map<String, Object>> friend_list = new ArrayList<Map<String, Object>>();
		Map<String ,Object> data = new HashMap<String, Object>();
		logger.info("아이디 : " + id);
		friend_list=userinfo.Matfalow_List(id);
		
		logger.info("맞팔로워 조회ㅏ :" + friend_list);
		if(friend_list.size() !=0) {
			data.put("code", 200);
			data.put("msg", "success");
			data.put("data", friend_list);
		}
		else {
			data.put("code", 201);
			data.put("msg", "empty");
			data.put("data", "null");
		}
		
		return data;
	}
	
	//uuid 생성
	public String uuid() {
		
		String uuid = "";
		uuid=UUID.randomUUID().toString();
		return uuid;
	}
	

}
