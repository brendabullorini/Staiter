package model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    
    public User(){
    	
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

}
