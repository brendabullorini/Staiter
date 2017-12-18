package controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import model.Post;
import model.User;

@Stateless
public class PostController {
	
    @PersistenceContext
    private EntityManager entityManager;
    
    @Inject
    UserController userController;
	
	public List<Post> getAll(){		
    	String hql = "Select p from Post p order by p.ID desc";
		TypedQuery<Post> q = entityManager.createQuery(hql,Post.class);
        return q.getResultList();
	}
	
	public List<Post> getByUser(int ID){		
    	String hql = "Select p from Post p where p.user.ID = :user order by p.ID desc";
		TypedQuery<Post> q = entityManager.createQuery(hql,Post.class);
		q.setParameter("user", ID);
		try{
			List<Post> posts = q.getResultList();
			return posts;
		}catch(Exception e){
			System.out.println("**********EXCEPTION EN GETBYUSER " + e.getMessage());
			return null;
		}
		
        
	}	
	
	public void createPost(Post p){
		p.setDate(new Date());
		entityManager.persist(p);
	}
	
	public List<Post> getByFollowing(User u){
		List<Post> posts = new ArrayList<Post>();
		Set<User> following = userController.getFollowing(u);
		for(User f : following){
			List<Post> temp = getByUser(f.getID());
			if(temp!=null){
				for(Post p : temp){
					posts.add(p);
				}	
			}
		}
		return posts;
	}
	
	public void addLike(Post p, User u){
		Set<User> likes = getLikes(p);
		if(likes==null)
			likes = new HashSet<User>();
		likes.add(u);
		p.setUsersLikes(likes);
		entityManager.merge(p);				
	}
	
	public void removeLike(Post p, User u){
		Set<User> likes = getLikes(p);
		if(likes==null){
			likes = new HashSet<User>();
		}
		likes.remove(u);
		p.setUsersLikes(likes);
		entityManager.merge(p);	
	}
	
	public int countLikes(Post p){
		int cantidad=0;
		Post postDB = entityManager.find(Post.class, p.getID());
		if(postDB.getUsersLikes()!=null)
			cantidad=postDB.getUsersLikes().size();
		return cantidad;
	}
	
	public int countPosts(int id){
		String hql = "Select count(p) from Post p where p.user.ID = :user";
    	Long count = (Long) entityManager.createQuery(hql)
 		       .setParameter("user", id).getSingleResult();
    	return count.intValue();
	}
	
	private Set<User> getLikes(Post p){
		Post postDB = entityManager.find(Post.class, p.getID());
		if(postDB.getUsersLikes()!=null){
			return postDB.getUsersLikes();
		}else{
			return null;
		}
			
	}
	
	public boolean existsLike(Post post, User user){
		boolean existe = false;
		Post postDB = entityManager.find(Post.class, post.getID());
		Set<User> likes = postDB.getUsersLikes();
		if(likes!=null){
			for(User u : likes){
				if(u.getID() == user.getID()){
					existe = true;
					break;
				}
			}
		}
		return existe;
	}
}
