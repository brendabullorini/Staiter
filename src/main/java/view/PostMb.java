package view;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Size;

import model.Post;
import model.User;
import controller.PostController;

@Named
public class PostMb{

	@Inject
	PostController postController;
	
	@Inject
	LoginMb loginMb;
	
	@Size(min=1,max=140, message = "El post debe tener entre 1 y 140 caracteres.")
	private String content;
	
	public String add(){
		if(content.length() > 0){
			Post post = new Post(loginMb.getCurrentUser(), content);
			postController.createPost(post);		
			return "index";
		}else{
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "El contenido del post no puede quedar vacio.", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return null;
		}

	}
	
	public List<Post> getPosts(){
		return postController.getAll();		
	}
	
	public List<Post> getPostsByUser(){
		return postController.getByUser(loginMb.getCurrentUser().getID());
	}

	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
}
