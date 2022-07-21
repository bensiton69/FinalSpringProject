package restapi.webapp.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import restapi.webapp.Enums.Status;
import restapi.webapp.Models.SeatPackage;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface SeatPackageRepos extends CrudRepository<SeatPackage, Long> {
    List<SeatPackage> findSeatPackageByStatus(Status status);
    List<SeatPackage> findSeatPackagesByRowNumber(int r);
    List<SeatPackage> findSeatPackageByRowNumberAndColNumber(int r, int c);

}
