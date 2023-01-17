package com.koreait.login_final.loginWeb.login;

import org.springframework.stereotype.Service;

import com.koreait.login_final.member.Member;
import com.koreait.login_final.member.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginService {

	// loginService date -> MemberRepository 로 이동 
	private final MemberRepository memberRepository;
	
	/*
	 *  2. 로그인 하기 
	 *  로그인 Controller 영역에서 사용자가 입력한
	 *  id, pw 가 일치하면 해당 값을 MemberRepository 에 가져와 
	 *  반환 동시에 로그인 성공을 리턴 
	 *  
	 *  만약 사용자 정보 != MemberRepository 정보가 일치하지 않는다면
	 *  null 값 리턴 
	 */
	
	public Member login(String loginId, String password) {
		Member member = memberRepository.findByLoginId(loginId);
		
		// 로그인 성공시 
		if ( member != null && member.getLoginId().equals(loginId)) {
			return member;
		//로그인 실패 시 
		}else {
			return null;
		}
	}

}
