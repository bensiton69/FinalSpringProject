package restapi.webapp.Repositories;

import org.springframework.data.repository.CrudRepository;
import restapi.webapp.Models.SeatPackage;

import java.util.UUID;

public interface SeatPackageRepos extends CrudRepository<SeatPackage, Long> {
}
