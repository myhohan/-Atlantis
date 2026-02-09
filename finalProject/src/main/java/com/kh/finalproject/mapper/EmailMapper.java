package com.kh.finalproject.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface EmailMapper {
	/** 기존 이메일에 대한 인증키 수정
	 * @param map (email, authKey)
	 * @return int 행의 갯수
	 */
	@Mapper

	    
	    // XML의 #{email} -> @Param("email")
	    // XML의 #{authKey} -> @Param("authKey")
	    int updateAuthKey(@Param("email") String email, @Param("authKey") String authKey);
	
	/** 이메일과 인증번호 새로 삽입
	 * @param map (email, authKey)
	 * @return int 행의 갯수
	 */
	int insertAuthKey(Map<String, String> map);
	
	/** 입력받은 이메일, 인증번호가 있는지 조회
	 * @param map
	 * @return
	 */
	int checkAuthKey(Map<String, String> map);
}
