package restapi.webapp.Repositories;

import org.springframework.data.repository.CrudRepository;
import restapi.webapp.Models.Movie;

public interface MovieRepos extends CrudRepository<Movie, Long> {
}


