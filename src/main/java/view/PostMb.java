package view;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import model.Post;
import model.User;
import controller.PostController;

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
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
}
