package com.example.JPA_final.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.JPA_final.domain.Address;
import com.example.JPA_final.domain.Member;
import com.example.JPA_final.dto.MemberForm;
import com.example.JPA_final.service.MemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;
	
	/*
	 *  회원가입 / 로그인 정보를 담고 
	 *  중복확인 요청을 수행하는 영역
	 */
	
	@GetMapping("/member/new")
	public String createForm(Model model) {
		model.addAttribute("memberForm", new MemberForm());
		return "member/createMemberForm";
	}
	
	@PostMapping("/members/new")
	public String create(@Valid MemberForm form, BindingResult result) throws IllegalAccessException {
		
		//error 발생시 회원가입 페이지로 이동 
		if( result.hasErrors()) {
			return "members/createMemberForm";
		}
		
		// 정상 로직 수행시 Service 영역으로 정상 이동
		// 사용자의 회원가입 정보 , 주소 정보를 담고 DB 테이블에 저장한다. 
		Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());
		Member member = new Member();
		member.setName(form.getName());
		member.setAddress(address);
		
		memberService.join(member);
		return "redirect:/";
	}
	
	/*
	 *  MemberRepository 에 있는 회원가입 정보를 찾고
	 *  List 형태로 응답하여 사용자에게 전달하는 영역
	 */
	@GetMapping("/members")
	public String list(Model model) {
		List<Member> members = memberService.findMember();
		model.addAttribute("members", members);
		return "members/memberList_";
		
	}
}
