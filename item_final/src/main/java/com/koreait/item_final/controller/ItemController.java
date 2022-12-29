package com.koreait.item_final.controller;

import org.springframework.stereotype.Controller;

import com.koreait.item_final.repository.ItemRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ItemController {

	private final ItemRepository itemRepository;
	
}
