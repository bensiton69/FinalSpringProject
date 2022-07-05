package restapi.webapp.Services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import restapi.webapp.Models.CostumerUser;
import restapi.webapp.Models.Reservation;
import restapi.webapp.Models.SeatPackage;
import restapi.webapp.Models.ShowTime;
import restapi.webapp.Repositories.ReservationRepos;
import restapi.webapp.Repositories.SeatPackageRepos;
import restapi.webapp.Repositories.UserRepos;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class ReservationService {
    private Double pricePerSeat = 2.6;
    ReentrantLock reentrantLock = new ReentrantLock();
    private static final Logger logger = LoggerFactory.getLogger(ReservationService.class);


    public Optional<Reservation> SafeReservation(List<SeatPackage> seatPackages,
                                                 CostumerUser costumerUser,
                                                 UserRepos userRepos,
                                                 ReservationRepos reservationRepos)
    {
        reentrantLock.lock();
        Optional<Reservation> optionalReservation = Optional.empty();
        Reservation reservation = new Reservation(costumerUser);

        try {
            for(SeatPackage seatPackage : seatPackages)
                {
                    if(seatPackage.isAvailable() == false)
                    {
                        throw new Exception("Seat "+ seatPackage+ "are taken!");
                    }
                    else {
                        seatPackage.setAvailable(false);
                        seatPackage.setReservation(reservation);
                        reservation.addSeatPackage(seatPackage);
                    }
                }
            reservation.setPrice(pricePerSeat * seatPackages.size());
            reservationRepos.save(reservation);

            optionalReservation = Optional.of(reservation);
        }
        catch (Exception e ) {
            logger.info("Cannot reservation, exception: " + e.getMessage());
        }
        finally {
            reentrantLock.unlock();
            return optionalReservation;
        }
    }

    public void RemoveReservation(Long id, ReservationRepos reservationRepos, SeatPackageRepos seatPackageRepos) {
        Reservation reservation = reservationRepos.findById(id).get();
        List<SeatPackage> seatPackages = reservation.getSeatPackages();
        //reservation.setSeatPackages(null);

        for (SeatPackage seatPackage : seatPackages)
        {
            seatPackage.setReservation(null);
            seatPackage.setAvailable(true);
        }
        reservationRepos.deleteById(id);
        seatPackageRepos.saveAll(seatPackages);
    }
}
