package restapi.webapp.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import restapi.webapp.Enums.Status;
import restapi.webapp.Models.Movie;
import restapi.webapp.Models.SeatPackage;
import restapi.webapp.Models.ShowTime;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Repository
public interface ShowTimeRepos extends CrudRepository<ShowTime, Long> {
    List<ShowTime> findSeatPackageByStatus(Status status);
    List<ShowTime> findSeatPackageByMovie(Movie movie);
    List<ShowTime> findSeatPackageByStartTime(LocalDateTime dateTime);

}
