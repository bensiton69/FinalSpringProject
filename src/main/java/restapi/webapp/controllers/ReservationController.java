package restapi.webapp.controllers;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import restapi.webapp.Builders.BuilderEntityFactory;
import restapi.webapp.Builders.IControllerInterface;
import restapi.webapp.Dtos.Get.ReservationGetDto;
import restapi.webapp.Mappers.IMapperCinema;
import restapi.webapp.Repositories.ReservationRepos;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class ReservationController implements IControllerInterface<ReservationGetDto> {
    private final ReservationRepos reservationRepos;
    private final IMapperCinema mapperCinema;
    private final BuilderEntityFactory<ReservationGetDto> reservationEntityFactory;


    public ReservationController(ReservationRepos reservationRepos, IMapperCinema mapperCinema) {
        this.reservationRepos = reservationRepos;
        this.mapperCinema = mapperCinema;
        this.reservationEntityFactory = new BuilderEntityFactory<>(this);
    }

    @GetMapping("/Reservations/getById/{id}")
    @Override
    public ResponseEntity<EntityModel<ReservationGetDto>> getById(@PathVariable Long id) {
        return reservationRepos.findById(id)
                .map(mapperCinema::MapFromReservationToReservationGetDto)
                .map(reservationEntityFactory::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/Reservations/getAll")
    @Override
    public ResponseEntity<CollectionModel<EntityModel<ReservationGetDto>>> getAsResponseEntity() {
        return ResponseEntity.ok(
                reservationEntityFactory.toCollectionModel(
                        StreamSupport.stream(reservationRepos.findAll().spliterator(),
                                        false)
                                .map(mapperCinema::MapFromReservationToReservationGetDto) //
                                .collect(Collectors.toList())));
    }
}
