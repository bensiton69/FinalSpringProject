package restapi.webapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import restapi.webapp.Models.CostumerUser;
//import restapi.webapp.Models.Movie;
//import restapi.webapp.Models.SeatPackage;
import restapi.webapp.Models.*;
import restapi.webapp.Repositories.*;
import restapi.webapp.Services.InitShowTimeService;
import restapi.webapp.Services.ReservationService;
//import restapi.webapp.Repositories.SeatPackageRepos;
//import restapi.webapp.Repositories.UserRepos;
//import restapi.webapp.Services.InitShowTimeService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
// class declares one or one @Bean method
// Spring container to generates bean definitions and handles requests beans (at runtime)
class SeedDB {
    private static final Logger logger = LoggerFactory.getLogger(SeedDB.class);

    @Bean
    // CommandLineRunner beans once the application context is loaded.
    CommandLineRunner initDatabase(
            AsyncRunner asyncRunner ,
            UserRepos userRepos,
            MovieRepos movieRepos,
            ReservationRepos reservationRepos,
            ShowTimeRepos showTimeRepos,
            SeatPackageRepos seatPackageRepos,
            ReservationService reservationService,
            InitShowTimeService initShowTimeService) {
        // runner gets a copy of the new DB and creates the following
        // products and saves them
        return args -> {
            seedUsers(userRepos);
            seedShowTimes(movieRepos, initShowTimeService);
            seedReservations(showTimeRepos, userRepos,reservationRepos);
            SeedReservationsViaService(reservationService, userRepos, seatPackageRepos,showTimeRepos,reservationRepos);
            //seedRemoveReservation(reservationRepos, seatPackageRepos, userRepos);
        };
    }

    private void seedRemoveReservation(ReservationRepos reservationRepos, SeatPackageRepos seatPackageRepos, UserRepos userRepos) {
        Reservation reservation = reservationRepos.findById(206L).get();
        List<SeatPackage> seatPackages = reservation.getSeatPackages();

        for (SeatPackage seatPackage : seatPackages){
            seatPackage.setReservation(null);
        }

        seatPackageRepos.saveAll(seatPackages);
        CostumerUser costumerUser = userRepos.findById(reservation.getCostumerUser().getId()).get();
        costumerUser.setReservations(null);

        userRepos.save(costumerUser);
        reservationRepos.deleteById(reservation.getId());
    }

    private void seedUsers(UserRepos userRepos) {
        CostumerUser user1 = new CostumerUser("bensiton69");
        CostumerUser user2 = new CostumerUser("ShacharGlik94");
        CostumerUser user3 = new CostumerUser("DummyUser1");

        logger.info("logging " + userRepos.save(user1));
        logger.info("logging " + userRepos.save(user2));
        logger.info("logging " + userRepos.save(user3));


    }

    private void seedShowTimes(MovieRepos movieRepos, InitShowTimeService initShowTimeService) {

        Movie hp1 = movieRepos.findById(1L).get();

        ShowTime showTime =
                initShowTimeService.InitShowTime(hp1, 10, 10, LocalDateTime.now().plusHours(2));
        ShowTime showTime2 =
                initShowTimeService.InitShowTime(hp1, 10, 10, LocalDateTime.now().plusHours(4));

        movieRepos.save(hp1);

        // TODO: move to a service that deals with initialize show time / ctor
    }

    private void seedReservations(
            ShowTimeRepos showTimeRepos,
            UserRepos userRepos,
            ReservationRepos reservationRepos) {

        ShowTime showTime = ((List<ShowTime>) showTimeRepos.findAll()).get(0);
        CostumerUser costumerUser = ((List<CostumerUser>) userRepos.findAll()).get(0);

        // TODO: change start time to showtime.startTime

        List<SeatPackage> seatPackages = new ArrayList<>();



        SeatPackage seatPackage1 = showTime.getSeatPackages().get(6);
        SeatPackage seatPackage2 = showTime.getSeatPackages().get(7);

        logger.info("sp1: " + seatPackage1);
        logger.info("sp2: " + seatPackage2);

        seatPackage1.setAvailable(false);
        seatPackage2.setAvailable(false);
        seatPackages.add(seatPackage1);
        seatPackages.add(seatPackage2);



        Reservation reservation =  new Reservation(seatPackages, LocalDateTime.now().plusMinutes(300));
        reservation.setCostumerUser(costumerUser);

        seatPackage1.setReservation(reservation);
        seatPackage2.setReservation(reservation);


        reservationRepos.save(reservation);

        costumerUser.addReservation(reservation);

        userRepos.save(costumerUser);
    }


    private void SeedReservationsViaService(ReservationService reservationService,
                                            UserRepos userRepos,
                                            SeatPackageRepos seatPackageRepos,
                                            ShowTimeRepos showTimeRepos,
                                            ReservationRepos reservationRepos) {
        List<SeatPackage> seatPackages = new ArrayList<>();
        CostumerUser costumerUser = ((List<CostumerUser>) userRepos.findAll()).get(1);

        ShowTime showTime = ((List<ShowTime>)showTimeRepos.findAll()).get(0);

        seatPackages.add ((showTime.getSeatPackages().get(0)));
        seatPackages.add ((showTime.getSeatPackages().get(1)));

        reservationService.SafeReservation(seatPackages ,costumerUser, userRepos, reservationRepos);

    }
}