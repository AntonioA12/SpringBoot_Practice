package com.example.JPA_final.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "orders")
public class Order {

	@Id @GeneratedValue
	@Column(name = "order_id")
	private Long id;
	
	/*
	 * 연관관계 매핑 
	 * Member 테이블 <- Order 테이블 
	 */

	@ManyToOne
	@JoinColumn( name = "member_id")
	private Member member;
	private LocalDateTime orderDate;
	
	/* 
	 * 연관관계 매핑 
	 * Order 테이블 -> OrderStatus 테이블 
	 */
	
	@OneToMany(mappedBy = "order")
	private List<OrderItem> orderItems
		= new ArrayList<OrderItem>();
	
	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;
	
	/*
	 * 연관관계 메소드 
	 * 
	 * 양방향 매핑된 Order 테이블 안에서 
	 * member 테이블의 Order 와 
	 * OrderItem 테이블의 OrderItem 데이터가 
	 * 
	 * 생성되면 Member 테이블과 OrderItem 테이블에서도 
	 * 동시에 데이터가 적용되게끔 설정 
	 */
	
	public void setMember(Member member){
		this.member = member;
		member.getOrders().add(this);
	}
	
	public void addOrderItem( OrderItem orderItem) {
		orderItems.add(orderItem);
		orderItem.setOrder(this);
	}
}
