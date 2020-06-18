package com.project.dao;

import java.util.List;
import com.project.models.Account;
public interface IAccountDAO {
 //crud operations that interfere with user in database
//purpose of an interface\
		//  allows you to specify that multiple classes implement some common functionality.
	public int insert(Account a); //Create Operation
	public List<Account> findAll(); //Read Method
	public List<Account> findByStatus(int statusId);
	public Account findById(int accountId);
}
