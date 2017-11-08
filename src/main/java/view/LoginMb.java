package view;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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
	
	private User user = new User();

	private User currentUser;
	
	public boolean isLogged(){
		return currentUser != null;
	}
	
	public String login(){
		currentUser = userController.verify(user.getEmail(), user.getPassword());
		if(isLogged()){
			return "index?faces-redirect=true";
		}else {	
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "El usuario no existe", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return null;
		}
			
	}
	
	public String logout(){
		currentUser = null;
		return "index";
	}
		
	
	public User getCurrentUser(){
		return currentUser;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	

}
