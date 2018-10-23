package instagram.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import instagram.exception.BusinessException;
import instagram.exception.ErrorCodes;
import instagram.model.Follower;
import instagram.repository.FollowerRepository;
import instagram.service.FollowerService;

@Service
public class FollowerServiceImpl implements FollowerService {

	@Autowired
	private FollowerRepository followerRepository;

	@Override
	public List<Follower> getAllFollowersFromUser(int userid) {
		return this.followerRepository.FindAllByFollowed(userid);
	}

	@Override
	public List<Follower> getAllFollowedsFromUser(int userid) {
		return this.followerRepository.findAllByFollow(userid);
	}

	@Override
	public Follower requestNewFollower(int follower, int followed) throws BusinessException {
		if(FollowExist(follower, followed)) {
			throw new BusinessException(ErrorCodes.FOLLOW_EXISTS);
		}
		Follower follow = new Follower();
		follow.setFollowed(followed);
		follow.setFollow(follower);
		follow.setAccepted(false);
		
		this.followerRepository.save(follow);
		return follow;
	}
	
	@Override
	public Follower acceptFolllower(int follower, int followed, boolean acepted) throws BusinessException {
		if(!FollowExist(follower, followed)) {
			throw new BusinessException(ErrorCodes.FOLLOW_NOT_FOUND);
		}
		Follower follow = this.followerRepository.FindOneByfollowAndFollowed(follower, followed);
		follow.setAccepted(acepted);
		this.followerRepository.save(follow);
		return follow;
	}
	
	private boolean FollowExist(int follower,int followed) {
		Follower follow  = this.followerRepository.FindOneByfollowAndFollowed(follower, followed);
		return follow != null;
	}
	

}
