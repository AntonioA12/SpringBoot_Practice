package com.koreait.item_final.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.koreait.item_final.domain.Item;

@Repository
public class ItemRepository {

	/*
	 *  아이템 정보를 HashMap 타입으로 담아줄 변수와
	 *  아이템 id 를 생성할 때마다 순차적으로 증가시켜줄 시퀀스 생성 
	 * 
	 *  static 변수로 생성 하여 Repository 클래스 외부에서도 
	 *  사용 가능케 한다 
	 */
	private static final Map<Long, Item> store = new HashMap<Long, Item>();
	private static long sequence = 0L;
	
	
	/*
	 *  1. 상품조회 
	 *  아이템 정보를 담은 store의 모든 값을 리턴하는 것으로 조회 
	 */
	
	public List<Item> findAll() {
		return new ArrayList<Item>(store.values());
	}


	/*
	 * 1-1 아아디로 상품조회
	 * URI 에 GET 방식으로 명시된 id 값으로 
	 * item 정보가 담긴 HashMap 변수 하나를 전부 호출한다 ( 해당 아이디 만 ) 
	 */
	public Item findById(Long itemId) {
		return store.get(itemId);
	}


	/*
	 * 3. 상품 저장 
	 * Controller 영역에 선언된 item 객체 데이터를 가져와 
	 * 테이블의 sequence 를 ++ 추가하고 
	 * itemId 값에 맞춰 item 값을 저장하는 구조 
	 * 
	 * ! - 외부 DB 영역은 선언하지 않고 Spring 내부 DB 사용중 
	 * 
	 */
	public Item save(Item item) {
		item.setId(++sequence);
		store.put(item.getId(), item);
		return item;
		
	}

	/*
	 *  4. 상품 수정 
	 */
	
	public void update(Long itemId , Item updateParam) {
		
		// 수정할 아이템 id 를 조회 
		Item findItem = findById(itemId);
		
		// 조회된 id 의 값의 모든 정보를 사용자가 입력한 정보로 수정(=update) 
		findItem.setItemName(updateParam.getItemName());
		findItem.setPrice(updateParam.getPrice());
		findItem.setQuantity(updateParam.getQuantity());
		
		findItem.setOpen(updateParam.getOpen());
		findItem.setRegions(updateParam.getRegions());
		findItem.setItemType(updateParam.getItemType());
		findItem.setDeliveryCode(updateParam.getDeliveryCode());
	}
}
