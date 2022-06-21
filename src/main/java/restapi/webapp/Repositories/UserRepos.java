package restapi.webapp.Repositories;

import org.springframework.data.repository.CrudRepository;
import restapi.webapp.Models.CostumerUser;

public interface UserRepos extends CrudRepository<CostumerUser, Long> {
}
