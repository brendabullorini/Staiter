package view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import model.User;
import controller.UserController;

@Named
public class SearchMb implements Serializable{
	
	private static final long serialVersionUID = 7032291430363137834L;
	
	private String user;
	
	@Inject
	UserController userCtrl;

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
		
	public List<User> getUsers() {
		List<User> users = new ArrayList<>();
		users = userCtrl.findUsers(user);		
		return users;
	}
}
