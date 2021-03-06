package instagram.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import instagram.model.Post;

public interface PostRepository extends CrudRepository<Post, Integer> {
	
	@Query("SELECT p FROM Post p WHERE p.id = ?1")
	Post findOneById(int id);
	
	@Query("SELECT p FROM Post p WHERE p.id_user = ?1")
	List<Post> findAllByUser(int id_user);
	
	@Query("SELECT p FROM Post p WHERE p.id_user IN ?1")
	List<Post> findAllByFollowers(List<Integer> usersID);
	
}