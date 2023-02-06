package com.example.JPA_final.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.JPA_final.domain.Item;
import com.example.JPA_final.dto.ItemForm;
import com.example.JPA_final.service.ItemService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ItemController {

	private final ItemService itemService;
	
	/*
	 *  사용자가 "/item/new" 영역 요청시 
	 *  Item 정보를 응답과 동시에 
	 *  수정요청 및 응답을 처리하는 컨트롤러 
	 */
	
	@GetMapping( value = "/items/new")
	public String createForm(Model model) {
		model.addAttribute("form", new ItemForm());
		return "item/createItemForm";
	}
	
	@PostMapping("/items/new")
	public String create(ItemForm itemForm) {
		Item item = new Item();
		item.setName(itemForm.getName());
		item.setPrice(itemForm.getPrice());
		item.setStockQuantity(itemForm.getStockQuantity());
		
		itemService.saveItem(item);
		return "redirect:/";
	}
}
