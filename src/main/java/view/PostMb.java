package view;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import controller.PostController;
import model.Post;
import model.User;

@Named
public class PostMb {
	
	@Inject
	PostController postController;

	private String userName;
	private User user;
	private String date;
	private String content;
	
	public String add(){
		Post post = new Post(user, content);
		postController.createPost(post);		
		return "index";
	}
	
	public List<Post> getPosts(){
		return postController.getAll();
	}

	public List<Post> getUserPosts(){

		//user = new User(1, "brendabullorini@gmail.com", "brendabullorini", "brenda");
		return postController.getByUser(userName);
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
	
	public void setUserName(String userName){
		this.userName = userName;
	}
	
	

}
