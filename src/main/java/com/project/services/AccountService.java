package com.project.services;

//SERVICE LAYER PROVIDES THE LOGIC FOR THE ACTUAL APPLICATION
	//BANKING 
		//ALL SPECIFIC BANKING INFO GOES IN THIS LAYER
			//LOGIN, APPLY FOR ACCOUNT, REGISTER, OIPEN ACCOUNT, WITHDRAW,. DEPOSIT, TRANSFER, ACRUE INTEREST, UPGRADE ACCOUNT, JOINT USERS
			//NEED EMPLOYEES TO BE ABLE TO LOOK AT OTHER EMPLOYEES INFORMATION
import java.util.List;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;
import com.project.dao.AccountDAO;
import com.project.models.Account;
import com.project.models.User;
//service layer is a layer that is designed to enforce your business logic
//there are miscellaneous rules that define how your application will function
//			Ex: May not withdraw money over the current balance
//this design is simply furthering same design structure that we have used up to now
//how you go about designing the details of this layer is up to you
//due to the nature of the "business logic" being rather arbitrary
//this layer has the MOST creativity involved....most other layers are pretty boiler plate
//where you copy and paste most methods
public class AccountService {
		private AccountDAO adao = new AccountDAO();
	//A starting place that i like to use, is to also create CRUD methods in the service layer
		//that will be used to interact with the DAO
		// Then additionally, you can have extra methods to enforce whaterver features/rules that you want
		//for example, we might also have a login/logout method
		public int insert(Account a) {	
			return adao.insert(a);
		}
		public List<Account> findAll() {
			return adao.findAll();
		}
		public List<Account> findByStatus(int statusId){ //MAKES STATUS ID INT 
			return adao.findByStatus(statusId);
		}
		public List<Account> findByType(int typeId) { // Find by account type (1 checking, 2 savings)
			return adao.findByType(typeId);
		}
}
		
