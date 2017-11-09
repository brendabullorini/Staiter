package view;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import model.Comment;
import model.Post;
import model.User;
import controller.CommentController;

@Named
public class CommentMb {
	
	@Inject
	private CommentController commentCntrl;
	
	@Inject
	private LoginMb loginMb;
	
	@NotNull
	@Size(min=2,max=255)
	private String comment;
	
	public void create(Post post){
		User user = loginMb.getCurrentUser();
		System.out.println("/*/*/*/*/*/*/*/* CREATE POST " + user.getUserName());
		System.out.println("/*/*/*/*/*/*/*/* CREATE POST " + post.getContent());
		System.out.println("/*/*/*/*/*/*/*/* CREATE POST " + comment);
		commentCntrl.create(user, post, comment);
	}

	public List<Comment> listByPost(Post post){
		return commentCntrl.byPost(post);
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
