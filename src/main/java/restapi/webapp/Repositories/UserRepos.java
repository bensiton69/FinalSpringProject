package restapi.webapp.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import restapi.webapp.Models.CostumerUser;
import restapi.webapp.Models.Reservation;

@Repository
public interface UserRepos extends CrudRepository<CostumerUser, Long> {
}

