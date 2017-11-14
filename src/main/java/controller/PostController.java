package controller;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import model.Post;

@Stateless
public class PostController {
	
    @PersistenceContext
    private EntityManager entityManager;	
	
	public List<Post> getAll(){
		
    	String hql = "Select p from Post p order by p.ID desc";
		TypedQuery<Post> q = entityManager.createQuery(hql,Post.class);
        return q.getResultList();
	}
	
	public List<Post> getByUser(int ID){
		
		System.out.println("ID en controller: " + String.valueOf(ID));
    	String hql = "Select p from Post p where p.user.ID = :user order by p.ID desc";
		TypedQuery<Post> q = entityManager.createQuery(hql,Post.class);
		q.setParameter("user", ID);
        return q.getResultList();
	}	
	
	public void createPost(Post p){
		p.setDate(new Date());
		entityManager.persist(p);
	}


}
