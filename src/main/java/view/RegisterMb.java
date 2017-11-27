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
public class RegisterMb{
	
	@Inject
	UserController userController;
	
	@Inject
	ImageController imageController;

	private User user = new User();
	
	private Part file;
	
	public String add(){
								
		if(user.getEmail().length()>0 && user.getUserName().length()>0 && user.getPassword().length()>0){
			
			if(userController.userExists(user.getEmail())){
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"El e-mal ingresado ya existe.",null);
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return null;				
			}else{
				boolean created = false;
				if(file != null && file.getSize() > 0){
					try{
						Image img = null;
						if(file.getContentType().startsWith("image/")){
							img = imageController.upload(file);
							user.setImage(img);
						}
					} catch (Exception e){
						e.printStackTrace();
						FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"No se pudo cargar la foto, reintente.", null);
						FacesContext.getCurrentInstance().addMessage(null, msg);
					}	
				}
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

	public Part getFile() {
		return file;
	}

	public void setFile(Part file) {
		this.file = file;
	}



}
