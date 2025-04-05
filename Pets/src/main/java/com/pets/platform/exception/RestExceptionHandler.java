package com.pets.platform.exception;

import org.apache.ibatis.javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;



public class RestExceptionHandler {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	
 
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<SampleErrorResponse> handleException(NotFoundException e) {
    	SampleErrorResponse error = new SampleErrorResponse();
        logger.error("test:" + e.getClass());
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage("data not found");
        error.setComment("request된 파라미터로 찾을 수 있는 데이터가 없습니다.");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND); // body, status code
    }
    
 @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<SampleErrorResponse> handleException(RuntimeException ex) {
    	SampleErrorResponse error = new SampleErrorResponse();
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage("parameter has empty value");
        error.setComment("request된 파라미터 정보가 없습니다.");
        logger.error("일치하는 파라미터가 없습니다");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST); // body, status code
    }
 

    
	
    //요청 데이터가 없거나 
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex) {
    	ErrorResponse error = new ErrorResponse();
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage("parameter has empty value");
        error.setComment("request된 파라미터 정보가 없습니다.");
        logger.error("일치하는 파라미터가 없습니다");
    		    	
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    
    /*
    //500
    @ExceptionHandler
    public ResponseEntity<SampleErrorResponse> handleException(ConversionNotSupportedException e) {
    	ErrorResponse error = new ErrorResponse();
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage("internal server error (database error)");
        error.setComment("API 실행 중 시스템에서 발생한 에러입니다.");
        
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST); // body, status code
    }
*/

}
