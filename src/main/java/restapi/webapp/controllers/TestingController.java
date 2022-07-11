package restapi.webapp.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restapi.webapp.Builders.BuilderEntityFactory;
import restapi.webapp.Builders.IControllerInterface;
import restapi.webapp.Dtos.Get.ReservationGetDto;
import restapi.webapp.Dtos.Get.ShowTimeGetDto;
import restapi.webapp.Dtos.KeyValuePair;
import restapi.webapp.Dtos.Set.ReservationSetDto;
import restapi.webapp.Mappers.IMapperCinema;
import restapi.webapp.Mappers.MapperCinema;
import restapi.webapp.Models.CostumerUser;
import restapi.webapp.Models.Reservation;
import restapi.webapp.Models.SeatPackage;
import restapi.webapp.Models.ShowTime;
import restapi.webapp.Repositories.*;
import restapi.webapp.Services.ReservationService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static restapi.webapp.controllers.ReservationController.getResponseEntity;

@RestController
public class TestingController implements IControllerInterface<ReservationGetDto> {

    private static final Logger logger = LoggerFactory.getLogger(TestingController.class);

    private final MovieRepos movieRepos;
    private final ShowTimeRepos showTimeRepos;
    private final ReservationRepos reservationRepos;
    private final UserRepos userRepos;
    private final SeatPackageRepos seatPackageRepos;
    private final ReservationService reservationService;
    private final IMapperCinema mapperCinema;
    private final BuilderEntityFactory<ReservationGetDto> reservationEntityFactory;

    public TestingController(MovieRepos movieRepos, ShowTimeRepos showTimeRepos, ReservationRepos reservationRepos, UserRepos userRepos, SeatPackageRepos seatPackageRepos, ReservationService reservationService, IMapperCinema mapperCinema) {
        this.movieRepos = movieRepos;
        this.showTimeRepos = showTimeRepos;
        this.reservationRepos = reservationRepos;
        this.userRepos = userRepos;
        this.seatPackageRepos = seatPackageRepos;
        this.reservationService = reservationService;
        this.mapperCinema = mapperCinema;
        this.reservationEntityFactory = new BuilderEntityFactory<>(this);
    }

    @PostMapping("/TestReservations")
    public ResponseEntity<?> newProduct(@RequestBody ReservationSetDto reservationSetDto){
        CostumerUser costumerUser = (userRepos.findById(reservationSetDto.getCostumerUserId()).get());
        List<SeatPackage> seatPackageList = new ArrayList<>();
        for (Long id : reservationSetDto.getSeatPackagesId())
        {
            seatPackageList.add(seatPackageRepos.findById(id).get());
        }
        Optional<Reservation> optionalReservation = reservationService.SafeReservation(seatPackageList,costumerUser);
        return getResponseEntity(reservationSetDto, optionalReservation, mapperCinema, reservationEntityFactory);
    }

    @Override
    public ResponseEntity<EntityModel<ReservationGetDto>> getById(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<CollectionModel<EntityModel<ReservationGetDto>>> getAsResponseEntity() {
        return null;
    }

    @GetMapping("/products/multipleparams")
    @ResponseBody
    String getError(@RequestParam (required = true) String param1,
                    @RequestParam (required = false) String param2,
                    @RequestParam (required = false) String param3){
        return param1 + param2 + param3;
    }


    @GetMapping("/products/optionals")
    // required = true
    @ResponseBody
    String getOptionalMessage(@RequestParam Optional<Date> someParameter){
        return "someParameter value = " + someParameter.orElseThrow();
    }


    @GetMapping("/products/getOptionalMessageWithLocalDate")
    // required = true
    @ResponseBody
    public List<?> getOptionalMessageWithLocalDate(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            Optional<LocalDate> someParameter){
        return
                ((List<ShowTime>)showTimeRepos.findAll())
                        .stream()
                        .filter(st -> st.getStartTime().toLocalDate().isAfter(someParameter.get()) ||
                                st.getStartTime().toLocalDate().isEqual(someParameter.get()))
                        .map(showTime -> {
                            return new KeyValuePair(showTime.getId(), showTime.getMovie().getName() + showTime.getStartTime());
                        })
                        .collect(Collectors.toList());
    }
}
