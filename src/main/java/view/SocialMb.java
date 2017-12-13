package view;

import javax.inject.Inject;
import javax.inject.Named;

import model.Post;
import auth.AuthMb;
import controller.PostController;

@Named
public class SocialMb {
	
	@Inject
	private PostController postCtrl;
	
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
}
