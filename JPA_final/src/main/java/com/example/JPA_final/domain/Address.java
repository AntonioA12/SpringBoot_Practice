package com.example.JPA_final.domain;

import javax.persistence.Embeddable;

import lombok.Getter;

@Embeddable
@Getter
public class Address {

	/*
	 * Setter 가 없는 이유 ? 
	 * 연관관계 매핑시 주소(Address) 정보를 
	 * 중복 생성 및 무한 생성을 방지하기 위함  
	 */
	
	private String city;
	private String street;
	private String zipcode;
	
	/*
	 *  Setter 가 없는 이유와 마찬가지로 
	 *  기본 생성자를 생성하고 1회 이상 생성자를 
	 *  생성하지 못하게 protected 로 막아놓는다. 
	 */
	public Address(String city, String street, String zipcode) {
		super();
		this.city = city;
		this.street = street;
		this.zipcode = zipcode;
	}
	
	
	protected Address() {}
	
}
