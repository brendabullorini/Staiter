package view;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import controller.UserController;
import model.User;

@Named
public class UserMb implements Serializable{
	
	private static final long serialVersionUID = -3411857293915149484L;

	@Inject
	UserController userController;
	
	private int ID;
	private String email;
	private String userName;
	private String password;
	
	private User currentUser;
	
	public boolean isLogged(){
		return currentUser != null;
	}
	
	public String login(){
		currentUser = userController.verify(userName, password);
		userName = null;
		password = null;
		if(isLogged()){
			return "index?faces-redirect=true";
		}else {			
			return null;
		}
			
	}
	
	public String logout(){
		currentUser = null;
		return "index";
	}
	
	public String add(){
		User user = new User(email, userName, password);
		boolean created = false;
		created = userController.createUser(user);
		if(created){
			return "login";	
		}else{
			return null;
		}		
	}
	
	public List<User> getUsers(){
		return userController.getAll();
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}	

}
