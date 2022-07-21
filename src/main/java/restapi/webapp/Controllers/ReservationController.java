package restapi.webapp.Controllers;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.http.ResponseEntity.ok;

/**
 * Reservation REST controller
 * Implements IControllerInterface of ReservationGetDto
 */
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

    /**
     * takes id as param and return single ReservationGetDto
     * @param id Reservation id
     * @return ResponseEntity of EntityModel of ReservationGetDto
     */
    @GetMapping("/Reservations/{id}")
    @Override
    public ResponseEntity<EntityModel<ReservationGetDto>> getById(@PathVariable Long id) {
        return reservationRepos.findById(id)
                .map(mapperCinema::MapFromReservationToReservationGetDto)
                .map(reservationEntityFactory::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * @return ResponseEntity of collection model of EntityModel of MovieGetDto
     */
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

    //TODO: move logic from here
    /**
     * POST request to post new Reservation
     * Using ReservationService
     * @param reservationSetDto ReservationSetDto as body param
     * @return ResponseEntity of unknown type (?)
     */
    @PostMapping("/Reservations")
    public ResponseEntity<?> newReservation(@RequestBody ReservationSetDto reservationSetDto){
        CostumerUser costumerUser = (userRepos.findById(reservationSetDto.getCostumerUserId()).get());
        List<SeatPackage> seatPackageList = new ArrayList<>();
        for (Long id : reservationSetDto.getSeatPackagesId())
        {
            seatPackageList.add(seatPackageRepos.findById(id).get());
        }
        Optional<Reservation> optionalReservation = reservationService.SafeReservation(seatPackageList,costumerUser);
        return getResponseEntity(reservationSetDto, optionalReservation);
    }

    /**
     * PUT request to update some Reservation entity
     * @param id reservation id to update
     * @param reservationSetDto reservation as setDto as body param
     * @return ResponseEntity of unknown type (?)
     */
    @PutMapping("/Reservations/{id}")
    public ResponseEntity<?> putReservation(@PathVariable Long id,@RequestBody ReservationSetDto reservationSetDto){

        Reservation oldReservation = reservationRepos.findById(id).orElseThrow();
        Optional<Reservation> optionalReservation = reservationService.SafePutReservation(oldReservation, reservationSetDto);

        return getResponseEntity(reservationSetDto, optionalReservation);

    }

    /**
     * will try to get from optional and then map it and add links
      * @param reservationSetDto
     * @param optionalReservation
     * @return
     */
    ResponseEntity<?> getResponseEntity(@RequestBody ReservationSetDto reservationSetDto, Optional<Reservation> optionalReservation) {
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

    /**
     * DELETE request to delete reservation entity
     * @param id reservation id to delete
     * @return ResponseEntity
     */
    @DeleteMapping("/Reservations/{id}")
    public ResponseEntity deleteReservation(@PathVariable Long id){
        if(reservationRepos.existsById(id) == false)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
        {
            reservationService.RemoveReservation(id);
            logger.info("Reservation" + id + "is no longer active");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

    }


}
