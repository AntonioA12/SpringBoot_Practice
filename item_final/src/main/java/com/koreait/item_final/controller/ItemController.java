package com.koreait.item_final.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	
	// 3-1 @RequestParam 사용 
	
//	@PostMapping("/add")
	public String saveV1( @RequestParam String itemName,
						@RequestParam int price,
						@RequestParam Integer quantity,
						Model model) {
		/*
		 * @RequestParam 을 통해 itemName, Price, quantity 값을
		 * 사용자의 URI 리소스 에 전달 받고 item 객체에 할당하여 
		 * Repository 영역에 저장하여 view 단에 호출 및 사용자에게 요청처리 하는 방식 
		 */
		
		Item item = new Item();
		item.setItemName(itemName);
		item.setPrice(price);
		item.setQuantity(quantity);
		
		itemRepository.save(item);
		
		// 저장이 완료되면 "basic/item" 위치로 리턴 
		return "basic/item";
	}
	
	// 3-2 @ModelAttribute 사용 
	
	//@PostMapping("/add")
	public String saveV2 (@ModelAttribute("item") Item item ) {
		// @ModelAttribute 가 해주는 역할
//		Item item = new Item();
//		item.setItemName(itemName);
//		item.setPrice(price);
//		item.setQuantity(quantity);
		
		// 파라미터에 @ModelAttribute 어노테이션 생략 가능하나
		// 가독성을 위해 선언해주자. 
		
		itemRepository.save(item);
		return "basic/item";
	}
	
	// 3-3 RedirectAttribute 사용
	
//	@PostMapping("/add")
	public String saveV3 (Item item, RedirectAttributes rttr) {
		
		Item saveItem = itemRepository.save(item);
		rttr.addAttribute("itemId", saveItem.getId());
		rttr.addAttribute("status", true);
		
		// 리턴방식은 redirect 로 리턴 
		return "redirect:/basic/items/{itemId}";
		
	}
	
	// 3-4 BindingResult 사용 
	
//	@PostMapping("/add")
	public String saveV4( Item item, BindingResult bindingResult , RedirectAttributes redirectAttributes) {
		
		// 1. ItemName 조건 
		
		// BindingResult 메소드 - StringUtils.hasText 
		// 값이 있을 경우 true, 공백이나 null 값일 경우 false 를 반환한다 
		// 해당 메소드를 통해 itemName 값의 Validation Check 를 실행 할 수 있다. 
		if( ! StringUtils.hasText(item.getItemName())) {
			bindingResult.addError(new FieldError("item","itemName","상품 이름은 필수 입니다."));
		}
		
		// 사용자의 요청 과정에 error 가 발생하게되면 
		// addForm 화면으로 돌아가게 설정 
		if ( bindingResult.hasErrors()) {
			System.out.println("errors= " + bindingResult);
			return "basic/addForm";
		}
		
		// 2. Price 조건 
		
		if ( item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 100000000) {
			bindingResult.addError( new FieldError("item", "price", "가격을 입력하셔야 합니다 [ 가격은 1000~ 100000000 사이 여야합니다.]"));
		}
		
		// 3. quantity 조건 
		
		if ( item.getQuantity() == null || item.getQuantity() > 100000) {
			bindingResult.addError( new FieldError("item", "quantity", "수량을 입력하셔야 합니다 [ 수량은 100000 미만이여야 합니다."));
		}
		
		// 값이 잘 입력되었는지 콘솔에 출력해보기 
		System.out.println("item.open" + item.getOpen());
		System.out.println("item.regions" + item.getRegions());
		System.out.println("item.itemType" + item.getItemType());
		
		// bindingResult 검증 오류가 없다면 ? 
		// 정상적으로 saveItem 실행 
		Item saveItem = itemRepository.save(item);
		redirectAttributes.addAttribute("itemId", saveItem.getId());
		redirectAttributes.addAttribute("status", true);
		
		
		return "redirect:/basic/items/{itemId}"; 
	}
	
	@PostMapping("/add")
	public String saveV5( Item item , BindingResult bindingResult , RedirectAttributes redirectAttributes ) {
		

		String defaultmessage = "지역변수로 오류 설정해보기";
		
		if ( ! StringUtils.hasText(item.getItemName()) ) {
			bindingResult.addError( new FieldError("item", "itemName", item.getItemName(), false ,
					new String[] {"required.item.itemName","required.default"}, null, defaultmessage));
		}
		
		
		if ( bindingResult.hasErrors()) {
			System.out.println("errors= " + bindingResult);
			return "basic/addForm";
		}
		
		
		if (item.getPrice() == null || item.getPrice() < 1000 
				|| item.getPrice() > 10000000) {
			bindingResult.addError( new FieldError("item", "price", item.getPrice(), false,
					new String[] {"range.item.price"}, new Object[] {1000,1000000}, defaultmessage));
		}
		
		
		if ( item.getQuantity() == null || item.getQuantity() > 100000) {
			bindingResult.addError( new FieldError("item", "quantity", item.getQuantity(), false,
					new String[] {"max.item.quantity"}, new Object[] {9999}, defaultmessage));
		}
		
		System.out.println("item.open = " + item.getOpen());
		System.out.println("item.regions = " + item.getRegions());
		System.out.println("item.itemType = " + item.getItemType());

		Item saveItem =  itemRepository.save(item);
		redirectAttributes.addAttribute("itemId", saveItem.getId());
		redirectAttributes.addAttribute("status",true); // 저장이 완료되면 true 값 
		
		return "redirect:/basic/items/{itemId}";
	}
	
	/*
	 * 4. 상품 수정 
	 * 
	 * 상품 수정은 메소드가 2개 필요하다 
	 * 1 - 수정 할 상품을 조회할 메소드 ( GET 방식 ) 
	 * 2 - 수정 된 상품을 저장하고 redirect 로 즉시 호출해줄 메소드 ( POST 방식 )
	 */
	
	@GetMapping("/{itemId}/edit")
	public String editForm( @PathVariable Long itemId, Model model) {
		
		Item item = itemRepository.findById(itemId);
		model.addAttribute("item", item);
		return "basic/editForm";
	}
	
	@PostMapping("/{itemId}/edit")
	public String edit( @PathVariable Long id , @ModelAttribute Item item) {
		
		itemRepository.update(id, item);
		
		return "redirect:/basic/items/{itemId}";
	}
	
	// 테스트용 데이터 추가
	@PostConstruct
	public void init() {
//		System.out.println("초기화 메서드");
		itemRepository.save( new Item( "testA", 10000, 10));
		itemRepository.save( new Item( "testB" , 2000, 20));
	}
	
	// 종료 메서드
	@PreDestroy
	public void destroy() {
		System.out.println("종료 메서드");
	}
}
