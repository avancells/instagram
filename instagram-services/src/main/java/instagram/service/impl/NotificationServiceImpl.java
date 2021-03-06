package instagram.service.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import instagram.model.Follower;
import instagram.model.Notification;
import instagram.repository.NotificationRepository;
import instagram.service.CommentService;
import instagram.service.FollowerService;
import instagram.service.NotificationService;
import instagram.service.PostService;

@Service
public class NotificationServiceImpl implements NotificationService {

	@Autowired
	private NotificationRepository notificationRepository;
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private FollowerService followerService;

	@Override
	public List<Notification> getAllForIdOwnerUser(int idOwnerUser) {
		return notificationRepository.findAllByIdOwnerUserAndRequestFalse(idOwnerUser);
	}
	
	@Override
	public List<Notification> getFollowRequestForIdOwnerUser(int idOwnerUser) {
		return notificationRepository.findAllByIdOwnerUserAndRequestTrue(idOwnerUser);
	}
	
	@Override
	public void addedLikeToPost(int idPost, int idActionUser) {
		
		Notification event = new Notification();
		
		event.setIdActionUser(idActionUser);
		event.setIdOwnerUser(postService.getUserIdByPost(idPost));
		event.setIdPost(idPost);
		event.setRequest(false);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		event.setCreatedAt(timestamp.getTime());
		
		event.setMessage("le gusta post");
		if(event.getIdActionUser() != event.getIdOwnerUser()) {
			notificationRepository.save(event);

		}
				
	}

	@Override
	public void addedCommentToPost(int idPost, int idComment, int idActionUser) {
		
		Notification event = new Notification();
		
		event.setIdActionUser(idActionUser);
		event.setIdOwnerUser(postService.getUserIdByPost(idPost));
		event.setIdPost(idPost);
		event.setIdComent(idComment);
		event.setRequest(false);
		
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		event.setCreatedAt(timestamp.getTime());
		
		event.setMessage("Ha comentado en tu post");
		
		if(event.getIdActionUser() != event.getIdOwnerUser()) {
			notificationRepository.save(event);
		}
		
	}

	@Override
	public void addedFollowRequest(int idActionUser, int idOwnerUser, int idFollow) {
		
		Notification event = new Notification();
		
		Follower follow = this.followerService.getFollowerById(idFollow);
		
		event.setIdActionUser(idActionUser);
		event.setIdOwnerUser(idOwnerUser);
		event.setIdFollow(idFollow);
		
		if(follow.isAccepted()) {
			event.setRequest(false);
			event.setMessage("Te ha empezado a seguir");
		}else {
			event.setRequest(true);
			event.setMessage("Te quiere seguir");
		}
		
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		event.setCreatedAt(timestamp.getTime());
		
		if(event.getIdActionUser() != event.getIdOwnerUser()) {
			notificationRepository.save(event);

		}
		
	}

	@Override
	public void addedLikeToComment(int idComment, int idActionUser) {
		
		Notification event = new Notification();
		
		event.setIdActionUser(idActionUser);
		event.setIdOwnerUser(commentService.getCommentById(idComment).getIdUser());
		
		event.setIdComent(idComment);
		event.setIdPost(commentService.getCommentById(idComment).getIdPost());
		
		event.setRequest(false);
		
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		event.setCreatedAt(timestamp.getTime());
		
		event.setMessage("Le gusta tu comentario");
		
		if(event.getIdActionUser() != event.getIdOwnerUser()) {
			notificationRepository.save(event);

		}
		
	}

	@Override
	public Notification getByidNotification(int idNotification) {
		return this.notificationRepository.findById(idNotification).get();
	}

	@Override
	public void actionOverNotification(int idNotification) {
		Notification notification = this.notificationRepository.findById(idNotification).get();
		this.notificationRepository.delete(notification);
	}

}