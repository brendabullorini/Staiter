package model;

import java.util.Date;

public class Post {
	
	private int ID;
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
