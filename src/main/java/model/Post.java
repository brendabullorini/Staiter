package model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.*;

@Entity
public class Post {
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)	
	private int ID;   
    
    @ManyToOne
	private User user;
    
    @ManyToMany(cascade = { 
    	    CascadeType.PERSIST, 
    	    CascadeType.MERGE
    	})
    	@JoinTable(name = "post_likes",
    	    joinColumns = @JoinColumn(name = "post_id"),
    	    inverseJoinColumns = @JoinColumn(name = "user_id")
    	)
    	private Set<User> usersLikes = new HashSet<>();
    
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
	private Date date;
    
    @ManyToOne(fetch=FetchType.EAGER)
    private Image image;
    
    @NotNull
    @Size(min=1,max=140, message = "El post debe tener entre 1 y 140 caracteres.")
	private String content;
		
	public Post(User user, String content, Image image) {
		super();
		this.user = user;
		this.content = content;
		this.image = image;
	}
	
	public Post(int ID, User user, Date date, String content, Image image) {
		super();
		this.ID = ID;
		this.date = date;
		this.user = user;
		this.content = content;
		this.image = image;
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
	
	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
	
	public boolean hasImage(){
		if(this.image != null){
			return true;
		}else{
			return false;
		}
	}
	
	public void addLike(User user){
		this.usersLikes.add(user);
		user.getPostsLikes().add(this);
	}
	
	public void removeLike(User user){
		this.usersLikes.remove(user);
		user.getPostsLikes().remove(this);
	}

	public Set<User> getUsersLikes() {
		return usersLikes;
	}

	public void setUsersLikes(Set<User> usersLikes) {
		this.usersLikes = usersLikes;
	}
	
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Post)) return false;
        return ID > 0 && ID == (((Post) o).ID);
    }
 
    @Override
    public int hashCode() {
        return 31;
    }
}
