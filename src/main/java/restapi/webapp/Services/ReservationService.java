package restapi.webapp.Services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import restapi.webapp.Dtos.Set.ReservationSetDto;
import restapi.webapp.Models.CostumerUser;
import restapi.webapp.Models.Reservation;
import restapi.webapp.Models.SeatPackage;
import restapi.webapp.Enums.Status;
import restapi.webapp.Repositories.ReservationRepos;
import restapi.webapp.Repositories.SeatPackageRepos;
import restapi.webapp.Repositories.ShowTimeRepos;
import restapi.webapp.Repositories.UserRepos;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class ReservationService {
    private Double pricePerSeat = 2.6;
    ReentrantLock reentrantLock = new ReentrantLock();
    private static final Logger logger = LoggerFactory.getLogger(ReservationService.class);
    private final ActivationService activationService;
    private final ShowTimeRepos showTimeRepos;
    private final ReservationRepos reservationRepos;
    private final SeatPackageRepos seatPackageRepos;
    private final UserRepos userRepos;

    public ReservationService(ActivationService activationService, ShowTimeRepos showTimeRepos, ReservationRepos reservationRepos, SeatPackageRepos seatPackageRepos, UserRepos userRepos) {
        this.activationService = activationService;
        this.showTimeRepos = showTimeRepos;
        this.reservationRepos = reservationRepos;
        this.seatPackageRepos = seatPackageRepos;
        this.userRepos = userRepos;
    }


    public Optional<Reservation> SafeReservation(List<SeatPackage> seatPackages,
                                                 CostumerUser costumerUser)
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
            reservation.setStartTime(seatPackages.get(0).getShowTime().getStartTime());
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

    /***
     * Makes some reservation and all its seat packages inactive,
     * Also creates new SeatPackages with same props into the DB
     * @param id - Reservation ID
     */
    public void RemoveReservation(Long id) {
        Reservation reservation = reservationRepos.findById(id).get();
        List<SeatPackage> seatPackages = reservation.getSeatPackages();
        activationService.Deactivate(reservation, reservationRepos);
        // TODO: add showtime details
        deactiveSeatPackageAndInsertIntoArchive(seatPackages);
        reservationRepos.save(reservation);
    }

    /**
     * Deactive List of SeatPackage, and creates new one instead
     * @param seatPackages to deactive
     */
    private void deactiveSeatPackageAndInsertIntoArchive(List<SeatPackage> seatPackages) {
        for (SeatPackage seatPackage : seatPackages)
        {
            logger.info("saving: " + seatPackageRepos.save(new SeatPackage(
                    seatPackage.getRowNUmber(),
                    seatPackage.getColNumber(),
                    true,
                    seatPackage.getShowTime()
            )));
            seatPackage.setStatus(Status.Inactive);
        }
    }

    public Optional<Reservation> SafePutReservation(Reservation oldReservation, ReservationSetDto newReservation) {
        CostumerUser costumerUser = (userRepos.findById(newReservation.getCostumerUserId()).get());

        // archive old reservation
        List<SeatPackage> seatPackageListOld = oldReservation.getSeatPackages();


        List<SeatPackage> seatPackageListNew = new ArrayList<>();
        for (Long seatPackageId : newReservation.getSeatPackagesId())
        {
            SeatPackage toAdd = seatPackageRepos.findById(seatPackageId).get();
            seatPackageListNew.add(toAdd);
            if (seatPackageListOld.contains(toAdd))
                seatPackageListOld.remove(toAdd);
        }

        deactiveSeatPackageAndInsertIntoArchive(seatPackageListOld);
        oldReservation.setStatus(Status.Inactive);
        reservationRepos.save(oldReservation);

        return SafeReservation(seatPackageListNew,costumerUser);

    }
}
