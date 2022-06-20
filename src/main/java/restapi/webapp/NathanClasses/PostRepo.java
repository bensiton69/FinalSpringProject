package restapi.webapp.NathanClasses;

import org.springframework.data.repository.CrudRepository;
import restapi.webapp.NathanClasses.Post;

import java.util.List;

public interface PostRepo extends CrudRepository<Post, Long> {
    List<Post> findByProfileId(Long id);
}