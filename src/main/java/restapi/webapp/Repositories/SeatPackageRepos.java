package restapi.webapp.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import restapi.webapp.Models.SeatPackage;

import java.util.UUID;

@Repository
public interface SeatPackageRepos extends CrudRepository<SeatPackage, Long> {
}
