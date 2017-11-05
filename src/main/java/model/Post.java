package model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Post {
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)	
	private int ID;   
    
    @ManyToOne
	private User user;
	private Date date;
	private String content;
		
	public Post(User user, String content) {
		super();
		this.user = user;
		this.content = content;
	}
	
	public Post(int ID, User user, Date date, String content) {
		super();
		this.ID = ID;
		this.date = date;
		this.user = user;
		this.content = content;
	}
	
	public int getID() {
		return ID;
	}
	
	public void setID(int iD) {
		ID = iD;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}

	
	
}
