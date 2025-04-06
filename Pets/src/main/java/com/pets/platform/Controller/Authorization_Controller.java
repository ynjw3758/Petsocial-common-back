package com.pets.platform.Controller;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pets.platform.jwt.TokenProvider;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", exposedHeaders = "Authorization")
@RestController
@RequestMapping("/Pets-social")

public class Authorization_Controller {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private TokenProvider token;
	
	@PostMapping("/token/refresh")
	public ResponseEntity<Map<String, Object>>refresh_vali(@RequestBody Map<String, Object> info){
		Map<String, Object> result =new HashMap<>();
		boolean refresh_check =false;
		String tokens=info.get("refresh_token").toString();
		String id=info.get("id").toString();
		refresh_check = token.refresh_validate(tokens, id);
		logger.info("리프레쉬 만료 시간 체크 결과 :" +refresh_check );
		if(refresh_check == true) {
			logger.info("리프레쉬 토큰 유효함 엑세스 토큰 ");
			Map<String, Object>token_info = new HashMap<>();
			token_info = token.create_access(id);
			logger.info("생성된 access_token :" + token_info);
			result.put("code", 200);
			result.put("msg", "엑세스 토큰 재발급 성공");
			result.put("data", token_info);
			return ResponseEntity.status(HttpStatus.OK).body(result);
			
		}else {
			result.put("code", 401);
			result.put("msg", "다시 로그인 해야함");
			result.put("data", "null");
		}
		
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
		
	}
	
	@GetMapping("/acccheck")
	public ResponseEntity<Map<String , Object>> accesscheck(HttpServletRequest request){
		ResponseEntity<Map<String, Object>> check_token;
		Map<String, Object> result =new HashMap<String, Object>();
			check_token =token.access_check(request);
			logger.info("토큰 결과 :" + check_token.getStatusCode());
			if(check_token.getStatusCode() == HttpStatus.UNAUTHORIZED) {
				logger.info("토큰 시간 만료");
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
			}
			logger.info("토큰 인증 완료");
			result.put("resultcode", 200);
			result.put("resultmsg", true);
			
			return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	@GetMapping("/valid-accesstoken")
	public ResponseEntity<Map<String, Object>> validtoken(HttpServletRequest req, @RequestParam("id") String id) {
		logger.info("헤더 auth 토큰 데이터 확인");
		logger.info("token : " + req.getHeader("Authorization"));
		Map<String, Object> result_data = new HashMap<String, Object>();
		result_data = token.ValidToken(req.getHeader("Authorization").toString(), id);
		if (req.getHeader("Authorization").toString().equals("")) {
			logger.info("사용자 인증 실패 토큰이 존재하지 않습니다");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}

		if (result_data.get("resultcode").equals(403)) {
			logger.info("요청한 데이터와 서버 데이터가 맞지 않습니다");
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(result_data);
		}

		return ResponseEntity.ok().body(result_data);
	}

}
