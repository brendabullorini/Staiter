package view;

import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import model.Post;
import model.User;
import auth.AuthMb;
import controller.PostController;
import controller.UserController;

@Named
public class SocialMb {
	
	@Inject
	private PostController postCtrl;
	
	@Inject
	private UserController userCtrl;
	
	@Inject
	private AuthMb authMb;
	
	public void like(Post post){				
		if(!existsLike(post)){			
			postCtrl.addLike(post, authMb.getCurrentUser());
		}else{			
			postCtrl.removeLike(post, authMb.getCurrentUser());
		}
	}
	
	public boolean existsLike(Post post){
		return postCtrl.existsLike(post, authMb.getCurrentUser());
	}
	
	public int countLikes(Post post){				
		return postCtrl.countLikes(post);
	}	
	
	public void follow(User user){
		if(!alreadyFollowed(user)){
			userCtrl.addFollow(user, authMb.getCurrentUser());
		}else{
			userCtrl.removeFollow(user, authMb.getCurrentUser());
		}
	}
	
	public void removeFollow(User user){
		userCtrl.removeFollow(user, authMb.getCurrentUser());
	}
	
	public void addFollow(User user){
		userCtrl.addFollow(user, authMb.getCurrentUser());
	}
	
	public boolean alreadyFollowed(User followed){
		return userCtrl.alreadyFollowed(followed, authMb.getCurrentUser());		
	}
	
	public Set<User> getFollowing(){
		return userCtrl.getFollowing(authMb.getCurrentUser());
	}
	
	public Set<User> getFollowers(){
		return userCtrl.getFollowers(authMb.getCurrentUser());
	}
	
	public boolean isFollowingMe(User following){
		return userCtrl.isFollowingMe(following, authMb.getCurrentUser());
	}
		
}
