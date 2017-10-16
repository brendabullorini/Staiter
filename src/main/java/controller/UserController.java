package controller;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import model.DB;
import model.User;

@Stateless
public class UserController {
	
	@Inject
	DB db;
	
	public List<User> getAll(){
		return db.getUsers();
	}
	
	public boolean createUser(User u){
		boolean created = false;
		if(!userExists(u.getEmail())){
			u.setID(getNewID());
			db.getUsers().add(u);
			created = true;
		}else{
			created = false;
		}
		
		return created;
	}
	
	public User verify(String email, String password){
		
		User user = null;
		
		for (User u : db.getUsers()) {
			if(u.getEmail().equals(email) && u.getPassword().equals(password)){
				user = u;
				break;
			}
		}
		
		return user;
	}
	
	public boolean userExists(String email){
		
		boolean exists = false;
		
		for (User u : db.getUsers()) {
			if(u.getEmail().equals(email)){
				exists = true;
				break;
			}
		}
		
		return exists;
	}
	
	private int getNewID(){
		
		int lastID = 0;
		if(!db.getUsers().isEmpty()){
			User lastUser = db.getUsers().get(db.getUsers().size()-1);
			lastID = lastUser.getID() + 1;	
		}	
		return lastID;
	}	

}
