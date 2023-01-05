package com.koreait.item_final.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.koreait.item_final.domain.DeliveryCode;
import com.koreait.item_final.domain.Item;
import com.koreait.item_final.domain.ItemType;
import com.koreait.item_final.repository.ItemRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class ItemController {

	private final ItemRepository itemRepository;
	
	/*
	 * 1. 상품 조회
	 */
	
	@GetMapping
	public String items(Model model) {
		List<Item> items = itemRepository.findAll();
		model.addAttribute("items", items);
		
		return "basic/items";
	}
	
	/*
	 * 1-1 상품 아이템id로 조회
	 */
	@GetMapping("/{itemId}")
	public String item(@PathVariable Long itemId, Model model ) {
		Item item = itemRepository.findById(itemId);
		model.addAttribute("item", item);
		
		return "basic/item";
	}
	
	/*
	 *  2. 상품 등록 
	 * 1번의 상품 조회 페이지에서 
	 * 상품 등록 버튼 html 태그에 
	 * th:onclick="|location.href='@{/basic/items/add}'|" 
	 * 경로를 지정하여 버튼 클릭시 상품 등록 페이지로 이동하는 컨트롤러 
	 * 
	 * "/add" 경로로 이동시 
	 * item 객체에 Item() 생성자를 할당하여
	 * "basic/addForm" 페이지의 
	 * th:field 명의 데이터가 Item() 생성자 에 담아
	 * 상품 등록 데이터를 담는다 ( 동시에 조회페이지에 바로 이동 ) 
	 */
	
	@GetMapping("/add")
	public String addForm(Model model) {
		model.addAttribute("item", new Item());
		return "basic/addForm"; 
	}
	
	/* 
	 * 3. 상품 등록 에 필요한 
	 *  배송 지역 과 배송코드 를 컨트롤러 영역에서 선언하고
	 *  데이터 할당후 바로 반환하여 
	 *  모든 Mapping 주소로 사용가능하게끔 지정 
	 */
	
	@ModelAttribute("regions")
	public Map<String, String> regions(){
		Map<String, String> regions = new LinkedHashMap<String, String>();
		
		regions.put("SEOUL","서울");
		regions.put("BUSAN", "부산");
		regions.put("JEJU", "제주");
		
		return regions;
	}
	
	@ModelAttribute("itemType")
	public ItemType[] itemTypes() {
		// enum에 있는 값을 배열로 변환하고 
		// 호출시 enum 값을 전체리턴 시켜는 것으로 조회 
		return ItemType.values();
	}
	
	@ModelAttribute("delieveryCodes")
	public List<DeliveryCode> deliveryCodes(){
		List<DeliveryCode> deliveryCodes = new ArrayList<DeliveryCode>();
		
		deliveryCodes.add(new DeliveryCode("FAST", "빠른배송"));
		deliveryCodes.add(new DeliveryCode("NORMAL", "일반배송"));
		deliveryCodes.add(new DeliveryCode("SLOW", "안전배송"));
		return deliveryCodes;
	}
	
	/*
	 * 3. 상품 목록 저장 
	 * 
	 */
}
