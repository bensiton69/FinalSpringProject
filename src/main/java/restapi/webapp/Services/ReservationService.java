package restapi.webapp.Services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import restapi.webapp.Models.CostumerUser;
import restapi.webapp.Models.Reservation;
import restapi.webapp.Models.SeatPackage;
import restapi.webapp.Models.ShowTime;
import restapi.webapp.Repositories.UserRepos;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class ReservationService {
    private Double pricePerSeat = 2.6;
    ReentrantLock reentrantLock = new ReentrantLock();
    private static final Logger logger = LoggerFactory.getLogger(ReservationService.class);


    public boolean SafeReservation(List<SeatPackage> seatPackages,
                                   ShowTime showTime,
                                   CostumerUser costumerUser,
                                   UserRepos userRepos)
    {
        reentrantLock.lock();
        Reservation reservation = new Reservation(costumerUser, showTime);

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
            costumerUser.addReservation(reservation);
            userRepos.save(costumerUser);
        }
        catch (Exception e ) {
            logger.info("Exception: " + e.getMessage());
        }
        finally {
            reentrantLock.unlock();
        }
//        return costumerUser.getReservations().get(0);
        return true;
    }
}
