package com.yujeans.justdo.user.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yujeans.justdo.user.Account;
import com.yujeans.justdo.user.repository.EditProfileRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EditProfileService {

	@Autowired
	private final EditProfileRepository editProfileRepository;
	
	public void save(Account account) {
		editProfileRepository.save(account);
	}
	
	// 수정
	
	public Account AccountView(Long id) {
		return editProfileRepository.findById(id).get();
	}
	
	// 전체 리턴 
	public List<Account> AccountList(){
		return editProfileRepository.findAll();
	}
	

		
	
}
