package view;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import controller.PostController;
import model.Post;
import model.User;

@Named
public class PostMb{

	@Inject
	PostController postController;
	
	@Inject
	LoginMb loginMb;

	private User user;
	private String date;
	private String content;
		
	public String add(){
		System.out.println("/*/*/*/*/**/ CONTENIDO (aca llega null: " + content);
		Post post = new Post(loginMb.getCurrentUser(), content);
		postController.createPost(post);		
		return "index";
	}
	
	public List<Post> getPosts(){
		return postController.getAll();		
	}
	
	public List<Post> getPostsByUser(){
		return postController.getByUser(loginMb.getCurrentUser().getID());
	}

	public User getUser() {
		return user;
	}

	public String getDate() {
		return date;
	}

	public String getContent() {
		System.out.println("Soy el content desde el getter (llega bien) " + content);
		return content;
	}
	
	public void setContent(String content) {
		System.out.println("Hola entre al setter (llega bien): " + content);
		this.content = content;
		System.out.println("Content seteado: " + this.content);
	}
	
}
