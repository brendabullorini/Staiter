package controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import model.DB;
import model.Post;
import model.User;

@Stateless
public class PostController {
	
	@Inject
	DB db;
	
	public List<Post> getAll(){
		return db.getPosts();
	}
	
	public List<Post> getByUser(String username){
		
		List<Post> posts = new ArrayList<>();
		
		for (Post p : db.getPosts()) {
			if(p.getUser().getUserName().equals(username))				
				posts.add(p);
		}
		
		return posts;
	}	
	
	public void createPost(Post p){
		p.setID(getNewID());
		p.setDate(new Date());
		db.getPosts().add(p);
	}
	
	private int getNewID(){
		
		int lastID = 1;
		if(!db.getPosts().isEmpty()){
			Post lastPost = db.getPosts().get(db.getPosts().size()-1);
			lastID = lastPost.getID() + 1;	
		}	
		return lastID;
	}


}
