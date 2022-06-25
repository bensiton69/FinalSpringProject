package restapi.webapp.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import restapi.webapp.Models.Reservation;

@Repository
public interface ReservationRepos extends CrudRepository<Reservation, Long> {
}
