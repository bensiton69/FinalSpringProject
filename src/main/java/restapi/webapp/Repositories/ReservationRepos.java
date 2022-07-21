package restapi.webapp.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import restapi.webapp.Enums.Status;
import restapi.webapp.Models.Reservation;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Repository
public interface ReservationRepos extends CrudRepository<Reservation, Long> {
    List<Reservation> findReservationByCostumerUser(String username);
    List<Reservation> findReservationsByOrderTime(LocalDateTime dateTime);
    List<Reservation> findReservationsByStatus(Status status);


}
