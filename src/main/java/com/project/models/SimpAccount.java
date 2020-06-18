package com.project.models;

public class SimpAccount extends Account {
	public SimpAccount(int id, double balance, AccountStatus as, AccountType at) {
		super( id,  balance,  as,  at);
	}
	public SimpAccount() {
		super();
	}
}
