package com.koreait.login_final.member;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

	private final MemberRepository memberRepository;
	
	/*
	 * 1. 멤버 회원등록 ( addForm ) 
	 *  "/member/add" 의 사용자 요청을 받으면 
	 *  "members/addMemberForm" 으로 이동하고 
	 *  사용자가 입력폼에 입력한 값을 
	 *  memberRepository.save 통해 로그인 멤버 정보 저장 
	 *  
	 *  정보 저장이 완료되면 redirect 실행 
	 */
	
	@GetMapping("/add")
	public String addForm( @ModelAttribute("member") Member member) {
		return "members/addMemberForm";
	}
	
	@PostMapping("/add")
	public String save( @ModelAttribute Member member) {
		memberRepository.save(member);
		return "redirect:/";
	}
}
