package model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.*;

@Entity
public class User {
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)	
	private int ID;
    
    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "El formato de e-mail es invalido.")
	private String email;
    
    @NotNull
    @Size(min=4,max=15, message = "El username debe tener entre 4 y 15 caracteres.")
	private String userName;
    
    @NotNull
    @Size(min=4,max=15, message = "La password debe tener entre 4 y 15 caracteres.")
	private String password;
    
    @ManyToOne(fetch=FetchType.EAGER)
    private Image image;
    
    @ManyToMany(mappedBy = "usersLikes")
    private Set<Post> postsLikes = new HashSet<>();
    
    @ManyToMany
    @JoinTable(name = "social_follows",
    joinColumns = @JoinColumn(name = "follow_id"),
    inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> followers = new HashSet<>();

    @ManyToMany
    @JoinTable(name="social_follows",
     joinColumns=@JoinColumn(name="user_id"),
     inverseJoinColumns=@JoinColumn(name="follow_id")
    )
    private Set<User> following = new HashSet<>();
    
    public User(){
    	
    }
    
	public Set<User> getFollowers() {
		return followers;
	}
	
	public void setFollowers(Set<User> followers) {
		this.followers = followers;
	}

	public Set<User> getFollowing() {
		return following;
	}

	public void setFollowing(Set<User> following) {
		this.following = following;
	}

	public Set<Post> getPostsLikes() {
		return postsLikes;
	}

	public void setPostsLikes(Set<Post> postsLikes) {
		this.postsLikes = postsLikes;
	}

	public User(String email, String userName, String password) {
		super();
		this.email = email;
		this.userName = userName;
		this.password = password;
	}
	
	public User(int ID, String email, String userName, String password) {
		super();
		this.ID = ID;
		this.email = email;
		this.userName = userName;
		this.password = password;
	}
	
	public int getID() {
		return ID;
	}
	
	public void setID(int iD) {
		ID = iD;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
	
	public boolean hasProfileImage(){
		if(this.image != null){
			return true;
		}else{
			return false;
		}			
	}
	
	public void addFollow(User user){
		this.following.add(user);
		user.getFollowing().add(this);
	}
	
	public void removeFollow(User user){
		this.following.remove(user);
		user.getFollowing().remove(this);
	}
	
   @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(ID, user.ID);
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }

	@Override
	public String toString() {
		return "User [ID=" + ID + ", email=" + email + ", userName=" + userName
				+ ", password=" + password
				+ ", followers=" + followers
				+ ", follows=" + following + "]";
	}
    
    
}