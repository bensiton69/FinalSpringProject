package restapi.webapp.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import restapi.webapp.Enums.Status;
import restapi.webapp.Models.CostumerUser;
import restapi.webapp.Models.Reservation;

import java.util.Collection;
import java.util.List;

@Repository
public interface UserRepos extends CrudRepository<CostumerUser, Long> {
    CostumerUser findCostumerUserByUsername(String username);
    List<CostumerUser> findCostumerUserByStatus(Status status);
}

