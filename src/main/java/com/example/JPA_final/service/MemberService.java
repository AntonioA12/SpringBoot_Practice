package com.example.JPA_final.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.JPA_final.domain.Member;
import com.example.JPA_final.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

	// 회원가입 서비스 
	@Transactional
	public Long join(Member member) throws IllegalAccessException {
		
	 validateMemberCheck(member);
	 
	 memberRepository.save(member);
	 return member.getId();
	}

	// 중복회원 검증 
	private void validateMemberCheck(Member member) throws IllegalAccessException {
		List<Member> findMembers = memberRepository.findByName(member.getName());
		
		if( ! findMembers.isEmpty()) {
			throw new IllegalAccessException("이미 존재하는 회원입니다.");
		}
	}

	// 회원 목록 조회 
	@Transactional
	public List<Member> findMember() {
		return memberRepository.findAll();
	}
	
	// 회원 1건 조회 
	@Transactional
	public Member findOne(Long memberid) {
		return memberRepository.findOne(memberid);
	}
}
