package view;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import auth.AuthMb;
import model.Comment;
import model.Post;
import model.User;
import controller.CommentController;

@Named
public class CommentMb {
	
	@Inject
	private CommentController commentCntrl;
	
	@Inject
	private AuthMb authMb;
	
	@NotNull(message="El comentario no puede estar vacio!")
	@Size(min=2,max=255)
	private String comment;
	
	public void create(Post post){
		System.out.println("******************** LLEGUE AL CREATE");
		if(comment != null && comment.length() > 0){
			User user = authMb.getCurrentUser();
			System.out.println("******************** COMENTARIO: " + comment);
			commentCntrl.create(user, post, comment);
		}else{
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "El comentario no puede quedar vacio.", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}

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
