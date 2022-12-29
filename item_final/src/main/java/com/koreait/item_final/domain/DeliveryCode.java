package com.koreait.item_final.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter 
@Data
@AllArgsConstructor
public class DeliveryCode {

	private String code;
	private String displayName;
}
