package com.koreait.login_final.loginWeb;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.koreait.login_final.loginWeb.session.SessionConst;
import com.koreait.login_final.member.Member;
import com.koreait.login_final.member.MemberRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {

	private final MemberRepository memberRepository;
	
	@GetMapping
	public String homeV4(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
	Member loginMember , Model model) {
		
		if( loginMember == null) {
			return "home";
		}
		
		model.addAttribute("member", loginMember);
		return "loginHome";
	}
}
