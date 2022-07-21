package restapi.webapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import restapi.webapp.Models.CostumerUser;
//import restapi.webapp.Models.Movie;
//import restapi.webapp.Models.SeatPackage;
import restapi.webapp.Dtos.Set.ReservationSetDto;
import restapi.webapp.Models.*;
import restapi.webapp.Repositories.*;
import restapi.webapp.Services.ShowTimeService;
import restapi.webapp.Services.ReservationService;
//import restapi.webapp.Repositories.SeatPackageRepos;
//import restapi.webapp.Repositories.UserRepos;
//import restapi.webapp.Services.InitShowTimeService;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
            ShowTimeService showTimeService) {
        // runner gets a copy of the new DB and creates the following
        // products and saves them
        return args -> {
            seedUsers(userRepos);
            seedShowTimes(movieRepos, showTimeService);
            seedReservations(showTimeRepos, userRepos,reservationRepos);
            SeedReservationsViaService(reservationService, userRepos, seatPackageRepos,showTimeRepos,reservationRepos);
        };
    }

    private void seedUsers(UserRepos userRepos) {
        CostumerUser user1 = new CostumerUser("bensiton69");
        CostumerUser user2 = new CostumerUser("ShacharGlik94");
        CostumerUser user3 = new CostumerUser("DummyUser1");

        logger.info("logging " + userRepos.save(user1));
        logger.info("logging " + userRepos.save(user2));
        logger.info("logging " + userRepos.save(user3));


    }

    private void seedShowTimes(MovieRepos movieRepos, ShowTimeService showTimeService) {

        Movie hp1 = movieRepos.findById(1L).get();

        ShowTime showTime =
                showTimeService.InitShowTime(hp1, LocalDateTime.now().plusHours(2));
        ShowTime showTime2 =
                showTimeService.InitShowTime(hp1, LocalDateTime.now().plusHours(4));

        movieRepos.save(hp1);
    }

    private void seedReservations(
            ShowTimeRepos showTimeRepos,
            UserRepos userRepos,
            ReservationRepos reservationRepos) {

        ShowTime showTime = ((List<ShowTime>) showTimeRepos.findAll()).get(0);
        CostumerUser costumerUser = ((List<CostumerUser>) userRepos.findAll()).get(0);

        List<SeatPackage> seatPackages = new ArrayList<>();



        SeatPackage seatPackage1 = showTime.getSeatPackages().get(6);
        SeatPackage seatPackage2 = showTime.getSeatPackages().get(7);

        logger.info("sp1: " + seatPackage1);
        logger.info("sp2: " + seatPackage2);

        seatPackage1.setAvailable(false);
        seatPackage2.setAvailable(false);
        seatPackages.add(seatPackage1);
        seatPackages.add(seatPackage2);



        Reservation reservation =  new Reservation(seatPackages);
        reservation.setCostumerUser(costumerUser);

        seatPackage1.setReservation(reservation);
        seatPackage2.setReservation(reservation);

        reservation.setStartTime(seatPackage1.getReservation().getStartTime());
        reservationRepos.save(reservation);

        costumerUser.addReservation(reservation);

        userRepos.save(costumerUser);
    }


    private void SeedReservationsViaService(ReservationService reservationService,
                                            UserRepos userRepos,
                                            SeatPackageRepos seatPackageRepos,
                                            ShowTimeRepos showTimeRepos,
                                            ReservationRepos reservationRepos) {

        ReservationSetDto reservationSetDto = new ReservationSetDto();
        reservationSetDto.setCostumerUserId(2L);
        List<Long> idsList = new ArrayList<>();
        idsList.add(30L);
        idsList.add(31L);

        reservationSetDto.setSeatPackagesId(idsList);

        List<SeatPackage> seatPackageList = new ArrayList<>();
        for (Long id : reservationSetDto.getSeatPackagesId())
        {
            seatPackageList.add(seatPackageRepos.findById(id).get());
        }

        CostumerUser costumerUser = ((List<CostumerUser>) userRepos.findAll()).get(1);

        reservationService.SafeReservation(seatPackageList ,costumerUser);

    }
}