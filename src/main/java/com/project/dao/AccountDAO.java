package com.project.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.project.models.SimpAccount;
import com.project.models.Account;
import com.project.models.AccountStatus;
//DATA ACCES OBJECT IN REGARDS TO DATA ACCES WANTED TO LEVERAGE ABSTRACTION
	//IN GENERAL IS A CLASS
		//CREATES INTERFACE TO USE ABSTRACTION
			//INTERFACE USED TO HIDE ALL LOGIC FROM THE CLASSES WHICH CONTAIN THEM
import com.project.models.AccountType;
import com.project.util.ConnectionUtil;
public class AccountDAO implements IAccountDAO{
	@Override
	public int insert(Account a) { //re
		try (Connection conn = ConnectionUtil.getConnection()) { //obtains conneciton from utility class
			String sql = "INSERT INTO ACCOUNTS (balance, status_id, type_id) VALUES (?,?,?)";
			// the ? are placeholders for input values, they work for PreparedStatements, and are designed to protect from SQL Injection
			PreparedStatement stmt = conn.prepareStatement(sql); //using prepared statement to prevent sql injection
			//inserting below values into the ?'s above
			double balance = a.getBalance(); //initial object creation of balance
			AccountType accounttype = a.getType(); //initial account type 
			AccountStatus accountstatus = a.getStatus();
		//	stmt.setDouble(1, double balance = a.getBalance(); //initial object creation of balance);
			//			stmt.setInt(6, u.getRole().getId());
			stmt.setDouble(1, balance);
			stmt.setInt(2, accountstatus.getStatusId());
			stmt.setInt(3, accounttype.getTypeId());
			return stmt.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
	@Override
	public List<Account> findAll() { // Return all users
		List<Account> everyAccount = new ArrayList<>();
		try (Connection conn = ConnectionUtil.getConnection()) {// This is a 'try with resources' block. 
			//Allows us to instantiate some variable, and at the end of try it will auto-close 
			//to prevent memory leaks, even if exception is thrown.
			String sql = "SELECT *" + "FROM ACCOUNTS " + "INNER JOIN ACCOUNT_STATUS ON ACCOUNTS.status_id = ACCOUNT_STATUS.id " + "INNER JOIN ACCOUNT_TYPE ON ACCOUNTS.type_id = ACCOUNT_TYPE.id"; 
			Statement stmnt = conn.createStatement();
			ResultSet resultSet = stmnt.executeQuery(sql); // Right as this is executed, the query runs to the database and grabs the info
			/*while(rs.next()) {
				int id = rs.getInt("id"); //grabs first column from the sql statement above
				String username = rs.getString("username");
				String password = rs.getString("password");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				String email = rs.getString("email");
				int roleId = rs.getInt("role_id");
				String rName = rs.getString("role");
				Role role = new Role(roleId, rName);
				User u = new User(id, username, password, firstName, lastName, email, role); //user object
			 * 
			 */
			while(resultSet.next()) { // For each entry in the result set
				int id = resultSet.getInt("ID"); // Grab the account id
				double balance = resultSet.getDouble("BALANCE");
				int asID = resultSet.getInt("STATUS_ID");
				String asStatus = resultSet.getString("status");
				int atID = resultSet.getInt("type_id");
				String atType = resultSet.getString("type");
				AccountStatus accountStatus = new AccountStatus(asID,asStatus);
				AccountType accountType = new AccountType(atID, atType);
				Account a = new SimpAccount(id,balance,accountStatus,accountType);
				everyAccount.add(a); // add User object to the list
			}
		} catch(SQLException e) {
			e.printStackTrace();
			return new ArrayList<Account>(); // If something goes wrong, return an empty list.
		}
		return everyAccount;
	}
	@Override
	public Account findById(int accountId) { 
		Account result = null;
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM ACCOUNTS " + "INNER JOIN ACCOUNT_STATUS ON ACCOUNTS.status_id = ACCOUNT_STATUS.id " + "INNER JOIN ACCOUNT_TYPE ON ACCOUNTS.type_id = ACCOUNT_TYPE.id " + "WHERE ACCOUNTS.ID = ?";
			PreparedStatement stmnt = conn.prepareStatement(sql);
			stmnt.setInt(1, accountId); // Defines the WHERE ID = ?
			ResultSet rs = stmnt.executeQuery(); // grabs result set of the query
			while(rs.next()) { // While there are results:
				int accountID = rs.getInt("id");
				double balance = rs.getDouble("balance");
				int statusId = rs.getInt("status_id");
				String statusName = rs.getString("status");
				int typeId = rs.getInt("type_id");
				String typeName = rs.getString("type");
				AccountStatus accountStatus = new AccountStatus(statusId,statusName);
				AccountType accountType = new AccountType(typeId,typeName);
				result = new SimpAccount(accountID,balance,accountStatus,accountType);
			}
		} catch(SQLException e) {
			e.printStackTrace();
			return result; // If something goes wrong, return 0 for '0 changed rows'.
		}
		return null;
	}
	@Override
	public List<Account> findByStatus(int statusId) { // Locate files of an appropriate status (pending, open, closed, denied)
		//CONFIRMED WORKS
		List<Account> everyAccount = new ArrayList<>();
		try (Connection conn = ConnectionUtil.getConnection()) {// This is a 'try with resources' block. 
			//Allows us to instantiate some variable, and at the end of try it will auto-close 
			//to prevent memory leaks, even if exception is thrown.
			String sql = "SELECT *" + "FROM ACCOUNTS " + "INNER JOIN ACCOUNT_STATUS ON ACCOUNTS.status_id = ACCOUNT_STATUS.id " + "INNER JOIN ACCOUNT_TYPE ON ACCOUNTS.type_id = ACCOUNT_TYPE.id " + "WHERE ACCOUNT_STATUS.id = ?"; // gets all accounts that match the specific account status ID
			PreparedStatement stmnt = conn.prepareStatement(sql);
			stmnt.setInt(1, statusId);
			ResultSet resultSet = stmnt.executeQuery(); // Right as this is executed, the query runs to the database and grabs the info
			while(resultSet.next()) { // For each entry in the result set
				int id = resultSet.getInt("ID"); // Grab the account id
				double balance = resultSet.getDouble("BALANCE");
				int asID = resultSet.getInt("status_id");
				String asStatus = resultSet.getString("status");
				int atID = resultSet.getInt("type_id");
				String atType = resultSet.getString("type");
				AccountStatus as = new AccountStatus(asID,asStatus);
				AccountType at = new AccountType(atID, atType);
				Account a = new SimpAccount(id,balance,as,at);
				everyAccount.add(a); // add User object to the list
			}
		} catch(SQLException e) {
			e.printStackTrace();
			return new ArrayList<Account>(); // If something goes wrong, return an empty list.
		}
		return everyAccount;
	}
	public List<Account> findByType(int typeId){ // Find by type (1 checking, 2 savings)
		//CONFIRMED WORKS
		List<Account> everyAccount = new ArrayList<>();
		try (Connection conn = ConnectionUtil.getConnection()) {// This is a 'try with resources' block. 
			//Allows us to instantiate some variable, and at the end of try it will auto-close 
			//to prevent memory leaks, even if exception is thrown.
			String sql = "SELECT * " + "FROM ACCOUNTS " + "INNER JOIN ACCOUNT_STATUS ON ACCOUNTS.status_id = ACCOUNT_STATUS.id " + "INNER JOIN ACCOUNT_TYPE ON ACCOUNTS.type_id = ACCOUNT_TYPE.id " + "WHERE ACCOUNT_TYPE.id = ?"; // gets all accounts that match the specific account type ID
			PreparedStatement stmnt = conn.prepareStatement(sql);
			stmnt.setInt(1, typeId);
			ResultSet rs = stmnt.executeQuery(); // Right as this is executed, the query runs to the database and grabs the info
			while(rs.next()) { // For each entry in the result set
				int id = rs.getInt("id"); // Grab the account id
				double balance = rs.getDouble("balance");
				int asID = rs.getInt("status_id");
				String asStatus = rs.getString("status");
				int atID = rs.getInt("type_id");
				String atType = rs.getString("type");
				AccountStatus as = new AccountStatus(asID,asStatus);
				AccountType at = new AccountType(atID, atType);
				Account a = new SimpAccount(id,balance,as,at);
				everyAccount.add(a); // add User object to the list
			}
		} catch(SQLException e) {
			e.printStackTrace();
			return new ArrayList<Account>(); // If something goes wrong, return an empty list.
		}
		return everyAccount;
	}
}
