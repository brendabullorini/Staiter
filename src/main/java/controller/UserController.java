package controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import model.Post;
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
	
	public void update(User user){
		entityManager.merge(user);
	}
	
	public boolean usernameExists(String username){

		boolean exists = false;		
    	String hql = "Select count(u) from User u where u.userName = :user";       	
    	Long count = (Long) entityManager.createQuery(hql)
    		       .setParameter("user", username).getSingleResult();    	 	
    	if(count > 0){
    		exists = true;
    	}      		
		return exists;
	}
		
	public User verify(String email, String password){
		
		User user = null;
		User userDB = null;
		
		//TODO checkear por usuario y password, no solo usuario y despues password.
		
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
	
	public boolean changePassword(String email, String newPassword){
		
		boolean changed = false;		
		entityManager.getTransaction().begin();
		String hql = "UPDATE User SET password=?1 WHERE email=?2";
		int executeUpdate = entityManager.createQuery(hql).setParameter(1, newPassword).setParameter(2, email).executeUpdate();
		entityManager.getTransaction().commit();
	    entityManager.close();
	    if(executeUpdate > 1)
	    	changed = true;
		
		return changed;
	}
	
	public boolean changeUsername(String email, String newUsername){
		
		boolean changed = false;				
		entityManager.getTransaction().begin();
		String hql = "UPDATE User SET userName=?1 WHERE email=?2";
		int executeUpdate = entityManager.createQuery(hql).setParameter(1, newUsername).setParameter(2, email).executeUpdate();
		entityManager.getTransaction().commit();
	    entityManager.close();
	    if(executeUpdate > 1)
	    	changed = true;
		
		return changed;
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
	
	public boolean alreadyFollowed(User followed, User user){
		System.out.println("********** USER CONTROLLER ***********");
		System.out.println("********** USER FOLLOWED: " + followed.getEmail());
		System.out.println("********** USER LOGUEADO: " + user.getEmail());
		boolean existe = false;
		User userDB = entityManager.find(User.class, user.getID());
		Set<User> follows = userDB.getFollows();
		if(follows!=null){
			for(User u : follows){
				System.out.println("********** VERIFICIANDO FOLLOWER: " + u.getEmail());
				if(u.getID() == followed.getID()){
					System.out.println("********* FOLLOW ENCONTRADO");
					existe = true;
					break;
				}
			}
		}
		return existe;
	}
	
	public void addFollow(User followed, User user){
		Set<User> follows = getFollows(user);
		if(follows==null)
			follows = new HashSet<User>();
		follows.add(followed);
		user.setFollows(follows);
		entityManager.merge(user);				
	}
	
	public void removeFollow(User followed, User user){
		Set<User> follows = getFollows(user);
		if(follows==null)
			follows = new HashSet<User>();
		follows.remove(followed);
		user.setFollows(follows);
		entityManager.merge(user);				
	}
	
	public Set<User> getFollows(User u){
		User userDB = entityManager.find(User.class, u.getID());
		if(userDB.getFollows()!=null){
			return userDB.getFollows();
		}else{
			return null;
		}
	}

}
