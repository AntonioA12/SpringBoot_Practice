package com.koreait.login_final.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;


@Repository
public class MemberRepository {

	private static Map<Long, Member> store = new HashMap<>();
	private static long sequence = 0L;
	
	/*
	 * 1. 멤버 회원등록 
	 * 	Member DTO 값을 HashMap 타입으로 변환하고 
	 *  사용자가 입력한 데이터를 MemberDTO 에 저장 
	 *  ( ! 중복회원을 방지하기 위해 sequence++ 추가 ) 
	 */
	public Member save(Member member) {
		member.setId(sequence++);
		store.put(member.getId(), member);
		return member;
	}

	/*
	 * 2. 로그인 하기 
	 * 
	 * 사용자의 로그인 정보를  LoginController -> LoginService 통해
	 * 받아오고, 해당 로그인 정보가 일치한지 검증하는 로직 
	 * 
	 *  MemberRepository 에서 사용자가 입력한 String LoginId 값을 
	 *  통해 전체 조회하고 
	 *  
	 *  조회한 값 = 사용자가 입력한 값이 일치한다면 
	 *  해당 정보를 리턴 
	 */
	
	public Member findById(Long id) {
		return store.get(id);
	}
	
	public List<Member> findAll(){
		return new ArrayList<Member>(store.values());
	}
	
	public Member findByLoginId(String loginId) {
		List<Member> all = findAll();
		for( Member m : all) {
			if (m.getLoginId().equals(loginId)) {
				return m;
			}
		}
		// 사용자가 입력한 값이 일치하지 않는다면 
		// null 값 리턴 
		return null;
	}

}
