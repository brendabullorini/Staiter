package view;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Size;

import model.Post;
import controller.PostController;

@Named
public class PostMb{

	@Inject
	PostController postController;
	
	@Inject
	LoginMb loginMb;
	
	@Size(min=1,max=140, message = "El post debe tener entre 1 y 140 caracteres.")
	private String content;
	
	public void add(){
		System.out.println("/*/*/*/*/* LLEGUE ACA O NO HOLA: " + content);
		if(content != null && content.length() > 0){
			System.out.println("/*/*/*/*/* ESTOY EN CREATE POST");
			Post post = new Post(loginMb.getCurrentUser(), content);
			postController.createPost(post);
		}else{
			System.out.println("/*/*/*/*/* ESTOY EN MANDAR MENSAJE NO VACIO");
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "El contenido del post no puede quedar vacio.", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		System.out.println("/*/**//*/* TERMINE EL ADD()");

	}
	
	public List<Post> getPosts(){
		return postController.getAll();		
	}
	
	public List<Post> getPostsByUser(){
		return postController.getByUser(loginMb.getCurrentUser().getID());
	}

	public String getContent() {
		System.out.println("/*/*/*/* GET CONTENT: " + content);
		return content;
	}
	
	public void setContent(String content) {
		System.out.println("/*/*/*/*/* SET CONTENT: " + content);
		this.content = content;
	}
	
}
