package com.koreait.login_final.member;

import lombok.Data;

@Data
public class Member {

	/*
	 *  로그인 사용자의 정보를 담을 자바 클래스 
	 *  ID 넘버 , 회원 ID, 회원 이름, 회원 패스워드 
	 */
	
	private Long id;
	private String loginId;
	private String name;
	private String password;
}
