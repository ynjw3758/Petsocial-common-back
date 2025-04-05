package com.pets.platform.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface Setting_Mapper {

	/*---비굥개 계정인지 조회--*/
	public boolean search_private(String userid);
	
	/*---회원가입 후 설정---*/
	public void Initsetting(Map<String, Object> info);
}
