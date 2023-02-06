package com.example.JPA_final.repository;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.example.JPA_final.domain.Item;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

	private final EntityManager em;
	
	public void save(Item item) {
		em.persist(item);
	}

}
