package view;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import auth.AuthMb;
import controller.UserController;

@Named
public class LoginMb implements Serializable{
	
	private static final long serialVersionUID = -3411857293915149484L;

	@Inject
	UserController userController;
	
	@Inject
	private AuthMb authMb;
	
	@NotNull
	private String username;
	
	@NotNull
	private String password;
	
	public String login(){
		if(username != null && password != null){
			authMb.setCurrentUser(userController.verify(username, password));
			if(authMb.isLogged()){
				System.out.println("********** LOGUEADO ");
				System.out.println(authMb.getCurrentUser().toString());
				return "newindex?faces-redirect=true";
			}else {				
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "El usuario no existe", null);
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return null;
			}
		}else{	
			return null;
		}
			
	}
	
	public String logout(){
		authMb.setCurrentUser(null);
		return "newindex?faces-redirect=true";
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
		
	

}
