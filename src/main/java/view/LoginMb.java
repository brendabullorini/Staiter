package view;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import model.User;
import controller.UserController;

@Named
@SessionScoped
public class LoginMb implements Serializable{
	
	private static final long serialVersionUID = -3411857293915149484L;

	@Inject
	UserController userController;
	
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
	
	public User getCurrentUser(){
		return currentUser;
	}
	
	

}
