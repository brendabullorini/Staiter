package view;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Part;

import model.Image;
import model.User;
import controller.ImageController;
import controller.UserController;

@Named
@MultipartConfig(location="/tmp",
fileSizeThreshold=1024*1024, 
maxFileSize=1024*1024*5,
maxRequestSize=1024*1024*5*5)
public class ProfileMb {
	
	@Inject
	UserController userController;
	
	@Inject
	ImageController imgController;
	
	@Inject
	LoginMb loginMb;
	
	private String newUsername;
	private String oldPassword;
	private String newPassword;
	private String confirmPassword;
	private Part file;

	private User user = new User();
	
	public String saveChanges(){
		
		boolean errorCarga = false;
						
		user.setID(loginMb.getCurrentUser().getID());
		user.setEmail(loginMb.getCurrentUser().getEmail());
		
		if(oldPassword.length() > 0 && newPassword.length() > 0){
			if(userController.verify(loginMb.getCurrentUser().getEmail(), oldPassword) != null){
				if(newPassword.equals(confirmPassword)){
					user.setPassword(newPassword);
				}else{					
					FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"La nueva clave y su confirmacion no coinciden.", null);
					FacesContext.getCurrentInstance().addMessage(null, msg);
					errorCarga = true;
				}
			}else{		
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"La clave anterior es incorrecta.", null);
				FacesContext.getCurrentInstance().addMessage(null, msg);
				errorCarga = true;
			}
		}else{			
			user.setUserName(loginMb.getCurrentUser().getPassword());
		}
		
		if(newUsername.length() > 0){
			if(!userController.usernameExists(newUsername)){				
				user.setUserName(newUsername);
			}else{				
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"El nombre de usuario ya existe.", null);
				FacesContext.getCurrentInstance().addMessage(null, msg);
				errorCarga = true;
			}
		}else{
			user.setUserName(loginMb.getCurrentUser().getUserName());
		}		
		
		if(file != null && file.getSize() > 0){
			try{
				Image img = null;
				if(file.getContentType().startsWith("image/")){
					img = imgController.upload(file);
					user.setImage(img);
				}
			} catch (Exception e){
				e.printStackTrace();
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"No se pudo cargar la foto, reintente.", null);
				FacesContext.getCurrentInstance().addMessage(null, msg);
				errorCarga = true;
			}	
		}else{
			user.setImage(loginMb.getCurrentUser().getImage());
		}
								
		if(!errorCarga){
			userController.update(user);
			loginMb.setCurrentUser(user);
			return "index";
		}else{
			return null;
		}
		
	}
	
	public String cancelChanges(){
		return "index";
	}	
	
	public String getNewUsername() {
		return newUsername;
	}

	public void setNewUsername(String newUsername) {
		this.newUsername = newUsername;
	}

	public String getOldPassword() {
		return oldPassword;
	}
	
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public Part getFile(){
		return file;
	}
	
	public void setFile(Part file){
		this.file = file;
	}
	

}
