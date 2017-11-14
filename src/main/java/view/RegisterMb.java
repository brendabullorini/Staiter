package view;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import model.User;
import controller.UserController;

@Named
public class RegisterMb{
	
	@Inject
	UserController userController;

	private User user = new User();
	
	public String add(){
		
		if(user.getEmail().length()>0 && user.getUserName().length()>0 && user.getPassword().length()>0){
			
			if(userController.userExists(user.getEmail())){
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"El e-mal ingresado ya existe.",null);
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return null;				
			}else{
				boolean created = false;
				created = userController.createUser(user);
				if(created){
					return "login";	
				}else{
					return null;
				}
			}			
	
		}else{
			return null;
		}
	
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	


}
