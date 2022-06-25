package restapi.webapp.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import restapi.webapp.Models.Movie;

@Repository
public interface MovieRepos extends CrudRepository<Movie, Long> {
}


