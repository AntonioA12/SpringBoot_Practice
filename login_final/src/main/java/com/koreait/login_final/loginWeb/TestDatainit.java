package com.koreait.login_final.loginWeb;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.koreait.login_final.item.Item;
import com.koreait.login_final.item.ItemRepository;
import com.koreait.login_final.member.Member;
import com.koreait.login_final.member.MemberRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TestDatainit {

	private final MemberRepository memberRepository;
	private final ItemRepository itemRepository;
	
	/*
	 *  테스트 데이터 
	 *  서버가 실행 동시에 데이터가 할당된다 ( 초기화 값 ) 
	 */
	
	@PostConstruct
	public void init() {
		itemRepository.save(new Item("testA", 10000, 100));
		itemRepository.save(new Item("testB", 20000, 200));
		
		Member member = new Member();
		member.setLoginId("test");
		member.setPassword("test!");
		member.setName("관리자");
		
		memberRepository.save(member);
	}
}
