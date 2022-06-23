package restapi.webapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import restapi.webapp.Models.CostumerUser;
//import restapi.webapp.Models.Movie;
//import restapi.webapp.Models.SeatPackage;
import restapi.webapp.Models.Movie;
import restapi.webapp.Models.SeatPackage;
import restapi.webapp.Models.ShowTime;
import restapi.webapp.Repositories.MovieRepos;
//import restapi.webapp.Repositories.SeatPackageRepos;
//import restapi.webapp.Repositories.UserRepos;
//import restapi.webapp.Services.InitShowTimeService;
import restapi.webapp.Repositories.ShowTimeRepos;

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
            /*UserRepos userRepos,*/
            MovieRepos movieRepos,
            /*InitShowTimeService initShowTimeService*/
            ShowTimeRepos showTimeRepos
            /*SeatPackageRepos seatPackageRepos*/) {
        // runner gets a copy of the new DB and creates the following
        // products and saves them
        return args -> {
//            seedUsers(userRepos);
            seedShowTimes(movieRepos, showTimeRepos/*, seatPackageRepos*/);
        };
    }

/*    private void seedUsers(UserRepos userRepos) {
        CostumerUser user1 = new CostumerUser("bensiton69");
        CostumerUser user2 = new CostumerUser("ShacharGlik94");

        logger.info("logging " + userRepos.save(user1));
        logger.info("logging " + userRepos.save(user2));

    }*/

    private void seedShowTimes(MovieRepos movieRepos, ShowTimeRepos showTimeRepos/*, SeatPackageRepos seatPackageRepos*/) {

        Movie hp1 = movieRepos.findById(new Long(1)).get();

        ShowTime showTime = new ShowTime(LocalDateTime.now().plusHours(2),hp1);

        List<SeatPackage> seatPackages = new ArrayList<>();

        for (int i=0;i<10;i++)
            for (int j=0;j<10;j++)
                seatPackages.add(new SeatPackage(i,j, true));

        showTime.setSeatPackages(seatPackages);
        hp1.setShowTimes(Arrays.asList(showTime));
        movieRepos.save(hp1);


//
//        // TODO: move to a service that deals with initialize show time / ctor
//        List<SeatPackage> seatPackages = new ArrayList<>();
//        showTime.setSeatPackages(seatPackages);
//
//        for (int i=0;i<2;i++)
//            for (int j=0;j<2;j++)
//            {
////                seatPackageRepos.save(new SeatPackage(i,j,true, showTime1));
//              showTime.AddSeatPackage(new SeatPackage(i,j,true, showTime));
//            }
//
////        logger.info("seats: " + showTime1.getSeatPackages());
////
//        showTimeRepos.save(showTime);
////        logger.info("showtime1: " + showTime1);
    }
}