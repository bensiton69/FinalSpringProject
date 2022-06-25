package restapi.webapp.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import restapi.webapp.Models.ShowTime;

@Repository
public interface ShowTimeRepos extends CrudRepository<ShowTime, Long> {
}