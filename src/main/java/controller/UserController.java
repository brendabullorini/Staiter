package controller;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import model.User;

@Stateless
public class UserController {
	
    @PersistenceContext
    private EntityManager entityManager;
	
	public boolean createUser(User u){
		boolean created = false;
		if(!userExists(u.getEmail())){			
			entityManager.persist(u);
			created = true;
		}else{
			created = false;
		}		
		
		return created;
	}
	
	public User verify(String email, String password){
		
		User user = null;
		User userDB = null;
		
    	String hql = "Select u from User u where u.email = :user";
		TypedQuery<User> q = entityManager.createQuery(hql,User.class);
		q.setParameter("user", email);
		
		List<User> resultList = (List<User>) q.getResultList(); 
		if (!(resultList.isEmpty())) {
			userDB = resultList.get(0);
		}
		
        if(userDB != null){
        	if(userDB.getPassword().equals(password)){
        		user = userDB;        	
        	}
        }
		
		return user;
	}
	
	public boolean userExists(String email){
		
		boolean exists = false;
		
    	String hql = "Select count(u) from User u where u.email = :user";
       	
    	Long count = (Long) entityManager.createQuery(hql)
    		       .setParameter("user", email).getSingleResult();
    	 	
    	if(count > 0){
    		exists = true;
    	}        
		
		return exists;
	}

}
