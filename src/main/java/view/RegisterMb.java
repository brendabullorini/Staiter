package view;

import javax.inject.Inject;
import javax.inject.Named;

import model.User;
import controller.UserController;

@Named
public class RegisterMb{
	
	@Inject
	UserController userController;
	
	private String email;
	private String userName;
	private String password;
	
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
