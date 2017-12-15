package view;

import java.io.Serializable;

import javax.inject.Inject;
import javax.inject.Named;

import model.User;
import controller.UserController;

@Named
public class ProfileMb implements Serializable {

	private static final long serialVersionUID = 2914514327462218871L;

	@Inject
	UserController userCtrl;
	
	private int id;
	
	private User user;
	
	public void loadData(){
		
		System.out.println("***************** LOAD DATA: " + id);			
		user = userCtrl.getById(id);
		if(user==null)
			System.out.println("************ USUARIO OBTENIDO NULL");
	}

	public int getId() {
		System.out.println("****************** GETER DE PROFILEMB: " + id);
		return id;
	}

	public void setId(int id) {
		System.out.println("*******************SETER DE PROFILEMB: " + id);
		this.id = id;
	}	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
