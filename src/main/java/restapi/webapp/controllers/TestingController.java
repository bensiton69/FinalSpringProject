package restapi.webapp.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import restapi.webapp.Builders.BuilderEntityFactory;
import restapi.webapp.Builders.IControllerInterface;
import restapi.webapp.Dtos.Get.ReservationGetDto;
import restapi.webapp.Dtos.Set.ReservationSetDto;
import restapi.webapp.Mappers.IMapperCinema;
import restapi.webapp.Models.CostumerUser;
import restapi.webapp.Models.Reservation;
import restapi.webapp.Models.SeatPackage;
import restapi.webapp.Repositories.*;
import restapi.webapp.Services.ReservationService;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        Optional<Reservation> optionalReservation = reservationService.SafeReservation(seatPackageList,costumerUser, userRepos, reservationRepos);
        try{
            Reservation reservation = optionalReservation.get();
            ReservationGetDto reservationGetDto = mapperCinema.MapFromReservationToReservationGetDto(reservation);
            EntityModel<ReservationGetDto> entityReservation = reservationEntityFactory.toModel(reservationGetDto);
            return
                    // status code 201
                    ResponseEntity.created(new URI(entityReservation.getRequiredLink(IanaLinkRelations.SELF)
                            .getHref())).body(entityReservation);
        } catch (Exception e) {
            return
                    // status code: 400
                    ResponseEntity.badRequest().body("Cannot create the product corresponding to " + reservationSetDto);
        }
    }

    @Override
    public ResponseEntity<EntityModel<ReservationGetDto>> getById(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<CollectionModel<EntityModel<ReservationGetDto>>> getAsResponseEntity() {
        return null;
    }
}
