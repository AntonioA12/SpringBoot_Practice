package com.example.JPA_final.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Member {

	@Id @GeneratedValue
	@Column(name = "member_id")
	private Long id;
	private String name;
	
	@Embedded
	private Address address;
	
	/*
	 * 연관관계 매핑 
	 * 
	 * Member 테이블 -> Order 테이블 1방향 지정 
	 */
	
	// OneToMany - PK -> FK 
	@OneToMany(mappedBy = "member")
	private List<Order> orders
		= new ArrayList<Order>();
	
	
	
	
}
