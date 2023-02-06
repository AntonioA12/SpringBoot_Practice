package com.example.JPA_final.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.JPA_final.domain.Member;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

	@Autowired // 어노테이션 선언 생략가능 
	private final EntityManager em;
	
	// 신규 회원 저장 - > JPA 테이블에 추가된다.
	public void save(Member member) {
		em.persist(member);
	}

	// 이름으로 회원 찾기 
	public List<Member> findByName(String name) {
		return em.createQuery("select m from Member m where m.name = :name", Member.class)
				.setParameter("name", name)
				.getResultList();
	}

	// 회원정보 전체찾기 - JPA 회원 테이블 전체 조회 
	public List<Member> findAll() {
		return em.createQuery("select m from Member m", Member.class)
				.getResultList();
	}

	// 1건 조회 - id 를 통해 JPA 회원테이블 1건 조회 
	public Member findOne(Long memberid) {
		return em.find(Member.class, memberid);
	}
}
