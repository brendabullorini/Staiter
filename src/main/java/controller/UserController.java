package controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import model.Image;
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
	
	public User getById(int id){
		try{
	    	String hql = "Select u from User u where u.ID = :id";
			TypedQuery<User> q = entityManager.createQuery(hql,User.class);
			q.setParameter("id", id);
			return q.getSingleResult();
		}catch (PersistenceException e){
			return null;
		}
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
		
		try{
			String jpql = "Select u from User u where u.email = :email and u.password = :password";
			TypedQuery<User> q = entityManager.createQuery(jpql, User.class);
			q.setParameter("email", email);
			q.setParameter("password", password);
			return q.getSingleResult();
		} catch (PersistenceException e){
			return null;
		}				
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
    	if(count > 0)
    		exists = true;    	
		
		return exists;
	}
	
	public boolean alreadyFollowed(User followed, User user){
		boolean existe = false;
		User userDB = entityManager.find(User.class, user.getID());
		Set<User> following = userDB.getFollowing();
		if(following!=null){
			for(User u : following){
				if(u.getID() == followed.getID()){
					existe = true;
					break;
				}
			}
		}
		return existe;
	}
	
	public void addFollow(User followed, User user){
		Set<User> following = getFollowing(user);
		if(following==null)
			following = new HashSet<User>();
		following.add(followed);
		user.setFollowing(following);
		entityManager.merge(user);				
	}
	
	public void removeFollow(User followed, User user){
		Set<User> following = getFollowing(user);
		if(following==null){
			following = new HashSet<User>();
		}			
		following.remove(followed);
		user.setFollowing(following);
		entityManager.merge(user);			
	}
	
	public Set<User> getFollowing(User u){
		User userDB = entityManager.find(User.class, u.getID());
		if(userDB.getFollowing()!=null){
			return userDB.getFollowing();
		}else{
			return null;
		}
	}
	
	public int countFollowing(int id){
		User userDB = entityManager.find(User.class, id);
		if(userDB.getFollowing()!=null){
			return userDB.getFollowing().size();
		}else{
			return 0;
		}
	}
	
	public Set<User> getFollowers(User u){
		User userDB = entityManager.find(User.class, u.getID());
		if(userDB.getFollowers()!=null){
			return userDB.getFollowers();
		}else{
			return null;
		}
	}
	
	public int countFollowers(int id){
		User userDB = entityManager.find(User.class, id);
		if(userDB.getFollowers()!=null){
			return userDB.getFollowers().size();
		}else{
			return 0;
		}
	}
	
	public boolean isFollowingMe(User f, User u){
		boolean following = false;
		User userDB = entityManager.find(User.class, f.getID());
		if(userDB!=null){
			if(userDB.getFollowing()!=null){
				for(User uf : userDB.getFollowing()){
					if(uf.getID() == u.getID()){
						following = true;
						break;
					}
				}
			}	
		}
		return following;
	}
	
	public String getUsernameById(int id){
		User userDB = entityManager.find(User.class, id);
		if(userDB!=null){
			return userDB.getUserName();			
		}else{
			return null;
		}
	}
	
	public Image getImageById(int id){
		User userDB = entityManager.find(User.class, id);
		if(userDB!=null){
			return userDB.getImage();			
		}else{
			return null;
		}
	}
	
	public String getEmailById(int id){
		User userDB = entityManager.find(User.class, id);
		if(userDB!=null){
			return userDB.getEmail();			
		}else{
			return null;
		}
	}
	
	public boolean existsById(int id){
		User userDB = entityManager.find(User.class, id);
		if(userDB!=null){
			return true;
		}else{
			return false;
		}
	}
	
	public List<User> findUsers(String search){
    	String hql = "Select u from User u where u.email LIKE '%" + search + "%' or u.userName LIKE '%" + search + "%'";
		TypedQuery<User> q = entityManager.createQuery(hql,User.class);		
        return q.getResultList();
	}

}
