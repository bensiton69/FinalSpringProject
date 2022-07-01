package restapi.webapp.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import restapi.webapp.Dtos.Get.ReservationGetDto;
import restapi.webapp.Mappers.MapperCinema;
import restapi.webapp.Models.Reservation;
import restapi.webapp.Repositories.MovieRepos;
import restapi.webapp.Repositories.ReservationRepos;
import restapi.webapp.Repositories.ShowTimeRepos;

import java.util.List;

@RestController
public class TestingController {

    private static final Logger logger = LoggerFactory.getLogger(TestingController.class);

    private final MovieRepos movieRepos;
    private final ShowTimeRepos showTimeRepos;
    private final ReservationRepos reservationRepos;

    public TestingController(MovieRepos movieRepos, ShowTimeRepos showTimeRepos, ReservationRepos reservationRepos) {
        this.movieRepos = movieRepos;
        this.showTimeRepos = showTimeRepos;
        this.reservationRepos = reservationRepos;
    }

    @GetMapping("Test")
    public ReservationGetDto getTest()
    {
        return MapperCinema.MapFromReservationToReservationGetDto(
                ((List<Reservation>) reservationRepos.findAll()).get(0)
        );
    }
}
