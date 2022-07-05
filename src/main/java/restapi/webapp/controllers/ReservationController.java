package restapi.webapp.controllers;

import org.hibernate.annotations.NotFound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restapi.webapp.Builders.BuilderEntityFactory;
import restapi.webapp.Builders.IControllerInterface;
import restapi.webapp.Dtos.Get.ReservationGetDto;
import restapi.webapp.Dtos.Set.ReservationSetDto;
import restapi.webapp.Mappers.IMapperCinema;
import restapi.webapp.Models.CostumerUser;
import restapi.webapp.Models.Reservation;
import restapi.webapp.Models.SeatPackage;
import restapi.webapp.Repositories.ReservationRepos;
import restapi.webapp.Repositories.SeatPackageRepos;
import restapi.webapp.Repositories.UserRepos;
import restapi.webapp.Services.ReservationService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

@RestController
public class ReservationController implements IControllerInterface<ReservationGetDto> {
    private final ReservationRepos reservationRepos;
    private final IMapperCinema mapperCinema;
    private final UserRepos userRepos;
    private final SeatPackageRepos seatPackageRepos;
    private final ReservationService reservationService;
    private final BuilderEntityFactory<ReservationGetDto> reservationEntityFactory;
    private static final Logger logger = LoggerFactory.getLogger(ReservationController.class);

    public ReservationController(ReservationRepos reservationRepos,
                                 IMapperCinema mapperCinema,
                                 UserRepos userRepos,
                                 SeatPackageRepos seatPackageRepos,
                                 ReservationService reservationService) {
        this.reservationRepos = reservationRepos;
        this.mapperCinema = mapperCinema;
        this.userRepos = userRepos;
        this.seatPackageRepos = seatPackageRepos;
        this.reservationService = reservationService;
        this.reservationEntityFactory = new BuilderEntityFactory<>(this);
    }

    @GetMapping("/Reservations/{id}")
    @Override
    public ResponseEntity<EntityModel<ReservationGetDto>> getById(@PathVariable Long id) {
        return reservationRepos.findById(id)
                .map(mapperCinema::MapFromReservationToReservationGetDto)
                .map(reservationEntityFactory::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/Reservations")
    @Override
    public ResponseEntity<CollectionModel<EntityModel<ReservationGetDto>>> getAsResponseEntity() {
        return ok(
                reservationEntityFactory.toCollectionModel(
                        StreamSupport.stream(reservationRepos.findAll().spliterator(),
                                        false)
                                .map(mapperCinema::MapFromReservationToReservationGetDto) //
                                .collect(Collectors.toList())));
    }

//    @PostMapping("/Reservations")
//    public ResponseEntity<?> postReservation(@RequestBody ReservationSetDto reservationSetDto) {
//        CostumerUser costumerUser = (userRepos.findById(reservationSetDto.getCostumerUserId()).get());
//
//        List<SeatPackage> seatPackageList = new ArrayList<>();
//        for (Long id : reservationSetDto.getSeatPackagesId())
//        {
//            seatPackageList.add(seatPackageRepos.findById(id).get());
//        }
//
//        Optional<Reservation> optionalReservation = reservationService.SafeReservation(seatPackageList,costumerUser, userRepos, reservationRepos);
//
//        String logInfo = optionalReservation.isPresent() ? "success" : "error";
//        logger.info("safe service: " + logInfo);
//
//        if (optionalReservation.isPresent())
//        {
//            return
//                    ResponseEntity.ok(
//                mapperCinema.MapFromReservationToReservationGetDto(optionalReservation.get())
//        );
//        }
//        else
//            return ResponseEntity.badRequest().body("Cannot create the product corresponding to " + reservationSetDto);
//
//    }

    @PostMapping("/Reservations/new")
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
            EntityModel<ReservationGetDto> entityProduct = reservationEntityFactory.toModel(reservationGetDto);
            return
                    // status code 201
                    ResponseEntity.created(new URI(entityProduct.getRequiredLink(IanaLinkRelations.SELF)
                            .getHref())).body(entityProduct);
        } catch (Exception e) {
            return
                    // status code: 400
                    ResponseEntity.badRequest().body("Cannot create the product corresponding to " + reservationSetDto);
        }
    }


//    @PutMapping("/Reservations/{id}")
//    public ResponseEntity<?> putReservation(@RequestBody ReservationSetDto reservationSetDto){
//
//    }

    @DeleteMapping("/Reservations/{id}")
    public ResponseEntity deleteReservation(@PathVariable Long id){
        if(reservationRepos.findById(id).isPresent() == false)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
        {
            reservationService.RemoveReservation(id, reservationRepos, seatPackageRepos); // TODO: to service
            logger.info("Reservation" + id + "deleted");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

    }


}
