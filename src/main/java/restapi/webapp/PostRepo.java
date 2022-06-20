package restapi.webapp;

import org.apache.catalina.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

interface PostRepo extends CrudRepository<Post, Long> {
    List<Post> findByProfileId(Long id);
}