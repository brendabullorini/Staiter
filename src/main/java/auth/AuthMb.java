package auth;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import model.User;

@Named
@SessionScoped
public class AuthMb implements Serializable{

	private static final long serialVersionUID = 1307370432440670440L;
	
	private User currentUser;

	public boolean isLogged() {
		return currentUser != null;
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

}
