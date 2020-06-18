package com.project.controllers;

import java.util.List;
import javax.servlet.http.HttpSession;
import com.project.exceptions.NotLoggedInException;
import com.project.models.Account;
import com.project.services.AccountService;
public class AccountController {
	private final AccountService AccountService = new AccountService();
	
	public List<Account> findAll(){
		return AccountService.findAll();
	}
	
	public List<Account> findByStatus(int statusId){
		return AccountService.findByStatus(statusId);
	}
	
	public List<Account> findByType(int typeId){
		return AccountService.findByType(typeId);
	}
	
}
