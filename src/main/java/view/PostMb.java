package view;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Part;
import javax.validation.constraints.Size;

import auth.AuthMb;
import model.Image;
import model.Post;
import model.User;
import controller.ImageController;
import controller.PostController;
import controller.UserController;

@Named
@MultipartConfig(location="/tmp",
fileSizeThreshold=1024*1024, 
maxFileSize=1024*1024*5,
maxRequestSize=1024*1024*5*5)
public class PostMb{

	@Inject
	PostController postController;
	
	@Inject
	UserController userController;
	
	@Inject 
	private ImageController imgCntl;
	
	private Part file;
	
	@Inject
	AuthMb authMb;
	
	@Size(min=1,max=140, message = "El post debe tener entre 1 y 140 caracteres.")
	private String content;
	
	public void add(){		
		if(content != null && content.length() > 0){
			try{
				Image img = null;
				if(file != null && file.getSize() > 0 && file.getContentType().startsWith("image/")){
					img = imgCntl.upload(file);
				}
				Post post = new Post(authMb.getCurrentUser(), content, img);
				postController.createPost(post);
				content = null;
			} catch (Exception e){
				e.printStackTrace();
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error interno", null);
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
		}else{
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "El contenido del post no puede quedar vacio.", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	public List<Post> getPostsByFollowing(){
		return postController.getByFollowing(authMb.getCurrentUser());
	}
	
	public List<Post> getPosts(){
		return postController.getAll();		
	}
	
	public List<Post> getPostsByUser(){
		return postController.getByUser(authMb.getCurrentUser().getID());
	}
	
	public List<Post> getPostsByUser(User user){
		return postController.getByUser(user.getID());
	}

	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public Part getFile(){
		return file;
	}
	
	public void setFile(Part file){
		this.file = file;
	}
	
	public int countFollowers(){
		return userController.countFollowers(authMb.getCurrentUser().getID());
	}
	
	public int countFollowing(){
		return userController.countFollowing(authMb.getCurrentUser().getID());
	}
	
	public int countPosts(){
		return postController.countPosts(authMb.getCurrentUser().getID());
	}
	
}
