package com.example.JPA_final.dto;


import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ItemForm {

	private Long id;
	private String name;
	private int price;
	private int stockQuantity;
}
