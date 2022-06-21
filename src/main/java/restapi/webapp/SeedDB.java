package restapi.webapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import restapi.webapp.Models.CostumerUser;
import restapi.webapp.Models.Movie;
import restapi.webapp.Models.ShowTime;
import restapi.webapp.Repositories.MovieRepos;
import restapi.webapp.Repositories.UserRepos;
import restapi.webapp.Services.InitShowTimeService;
import restapi.webapp.controllers.ShowTimeRepos;

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
            InitShowTimeService initShowTimeService,
            ShowTimeRepos showTimeRepos) {
        // runner gets a copy of the new DB and creates the following
        // products and saves them
        return args -> {
            CostumerUser user1 = new CostumerUser("bensiton69");
            CostumerUser user2 = new CostumerUser("AmitRotem11");

            logger.info("logging " + userRepos.save(user1));
            logger.info("logging " + userRepos.save(user2));


            Movie hp1 = movieRepos.findById(new Long(1)).get();
            ShowTime showTime1 = new ShowTime(LocalDateTime.now().plusHours(2),hp1);
            showTimeRepos.save(showTime1);
            List<ShowTime> showTimes = new ArrayList<>();
            showTimes.add(showTime1);

            hp1.setShowTimes(showTimes);
            movieRepos.save(hp1);
        };
    }
}