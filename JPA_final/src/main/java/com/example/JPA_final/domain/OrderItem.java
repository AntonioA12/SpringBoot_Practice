package com.example.JPA_final.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class OrderItem {

	@Id @GeneratedValue
	@Column(name = "order_item_id")
	private Long id;
	
	
	/*
	 * 연관관계 매핑
	 * Order 테이블 <- OrderStatus 테이블
	 * Item 테이블 <- OrderStatus 테이블
	 */

	@ManyToOne
	@JoinColumn(name = "order_id")
	private Order order;
	
	@ManyToOne
	@JoinColumn(name = "item_id")
	private Item item;
	
	private int orderPrice;
	private int count;
}
