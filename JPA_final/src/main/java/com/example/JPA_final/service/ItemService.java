package com.example.JPA_final.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.JPA_final.domain.Item;
import com.example.JPA_final.repository.ItemRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemService {
	
	private final ItemRepository itemRepository;

	/*
	 *  ItemController 요청에서 
	 *  ItemForm 수정사항을 저장하는 서비스 영역 
	 */
	@Transactional
	public void saveItem(Item item) {
		itemRepository.save(item);
	}
}
