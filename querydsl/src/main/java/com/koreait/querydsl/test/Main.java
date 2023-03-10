package com.koreait.querydsl.test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.koreait.querydsl.entity.Member;
import com.koreait.querydsl.entity.QMember;
import com.koreait.querydsl.entity.Team;
import com.querydsl.jpa.impl.JPAQueryFactory;

public class Main {
	
	static JPAQueryFactory queryFactory;
	
	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		queryFactory = new JPAQueryFactory(em);
		
		tx.begin();
		
		try {
			
			Team teamA = new Team("teamA");
			Team teamB = new Team("teamB");
			em.persist(teamA);
			em.persist(teamB);
			
			Member member1 = new Member("member1", 10, teamA);
			Member member2 = new Member("member2", 20, teamA);
			Member member3 = new Member("member3", 30, teamB);
			Member member4 = new Member("member4", 40, teamB);
			em.persist(member1);
			em.persist(member2);
			em.persist(member3);
			em.persist(member4);
			
			// 영속성 컨텍스트 초기화 
			em.flush();
			em.clear();
			
			// JPQL 방식
			// member 1 찾기
			String qString = "select m from Member m where m.username = : username";
			Member findByJpql = em.createQuery(qString, Member.class)
					.setParameter("username","member1")
					.getSingleResult()
					;
			
			System.out.println("findByJqpl = " + findByJpql.getUsername().equals("member1"));
			
			//QueryDSL 방식 
			//QMember m = new QMember("m"); // Qmember 의 이름부여, 별칭 부여
			QMember m = QMember.member;
			
			// static import 를 통해 QMemeer 클래스 
			Member findByQueryDSL = queryFactory.select(m)
					.from(m)
					.where(m.username.eq("member1")
							.and(m.age.eq(10)))
					.fetchOne()
					;
			
//			System.out.println("findByQueryDSL = " + findByQueryDSL.getUsername().equals("member1"));
			System.out.println(findByQueryDSL.toString());
			
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
		} finally {
			em.close();
			emf.close();
		}
	}
}
