package view;

import java.util.List;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import auth.AuthMb;
import model.Image;
import model.Post;
import model.User;
import controller.PostController;
import controller.UserController;

@Named
public class ProfileMb{
		
	HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
	private String id = origRequest.getParameter("id");

	@Inject
	PostController postCtrl;
	
	@Inject
	UserController userCtrl;
	
	@Inject
	private AuthMb authMb;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public List<Post> getPosts(){		
		if(id==null){
			return null;
		}else{
			return postCtrl.getByUser(Integer.valueOf(id));
		}			
	}
	
	public String getUsername(){
		return userCtrl.getUsernameById(Integer.valueOf(id));
	}
	
	public String getEmail(){
		return userCtrl.getEmailById(Integer.valueOf(id));
	}
	
	public int countFollowers(){
		return userCtrl.countFollowers(Integer.valueOf(id));
	}
	
	public int countFollowing(){
		return userCtrl.countFollowing(Integer.valueOf(id));
	}
	
	public int countPosts(){
		return postCtrl.countPosts(Integer.valueOf(id));
	}
	
	public Image getImage(){
		return userCtrl.getImageById(Integer.valueOf(id));
	}
	
	public boolean validParameter(){
		if(id==null){
			return false;
		}else{
			try{
				Integer.valueOf(id);
				if(userCtrl.existsById(Integer.valueOf(id))){
					return true;
				}else{
					return false;
				}
			}catch (Exception e){
				return false;
			}
		}
	}
	
	public void follow(){
		User user = userCtrl.getById(Integer.valueOf(id));
		if(!userCtrl.alreadyFollowed(user, authMb.getCurrentUser())){
			userCtrl.addFollow(user, authMb.getCurrentUser());
		}else{
			userCtrl.removeFollow(user, authMb.getCurrentUser());
		}
	}

}
