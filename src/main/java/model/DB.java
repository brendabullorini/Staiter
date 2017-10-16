package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Singleton;

import model.User;
import model.Post;

@Singleton
public class DB {
	
	private List<User> users = new ArrayList<User>();
	private List<Post> posts = new ArrayList<Post>();	

	public DB(){
		User userTest = new User(1, "brendabullorini@gmail.com", "brendabullorini", "brenda");
		User userTestTwo = new User(1, "nicolasbullorini@gmail.com", "nicolasbullorini", "niclas");
		users.add(userTest);
		users.add(userTestTwo);						
		Post postTest = new Post(1, userTest, new Date(), "Probando post usuario brenda");
		Post postTestTwo = new Post(2, userTest, new Date(), "Probando post 2 usuario brenda");
		Post postTestThree = new Post(3, userTestTwo, new Date(), "Probando post usuario nicolas");
		Post postTestFour = new Post(4, userTestTwo, new Date(), "Probando post 2 usuario nicolas");
		Post postTestFive = new Post(5, userTestTwo, new Date(), "Probando post 3 usuario nicolas");
		posts.add(postTest);
		posts.add(postTestTwo);
		posts.add(postTestThree);
		posts.add(postTestFour);
		posts.add(postTestFive);
	}
	
	public List<User> getUsers() {
		return users;
	}
	public List<Post> getPosts() {
		return posts;
	}
				

}

