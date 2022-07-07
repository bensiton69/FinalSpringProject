package restapi.webapp.Mappers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import restapi.webapp.Dtos.Get.CostumerUserGetDto;
import restapi.webapp.Dtos.Get.ReservationGetDto;
import restapi.webapp.Dtos.KeyValuePair;
import restapi.webapp.Dtos.Get.MovieGetDto;
import restapi.webapp.Dtos.Get.SeatPackageGetDto;
import restapi.webapp.Dtos.Get.ShowTimeDto;
import restapi.webapp.Dtos.Set.CostumerUserSetDto;
import restapi.webapp.Models.*;

import java.util.stream.Collectors;

//TODO: implements IMapperCinema
@Component
public class MapperCinema implements IMapperCinema {
    private static final Logger logger = LoggerFactory.getLogger(MapperCinema.class);


    // from DAO to DTO
    public MovieGetDto MapFromMovieToMovieGetDto(Movie movie) {
        MovieGetDto movieGetDto = new MovieGetDto();
        movieGetDto.setId(movie.getId());
        movieGetDto.setName(movie.getName());
        movieGetDto.setLink(movie.getLink());
        movieGetDto.setDuration(movie.getDuration());
        movieGetDto.setYearOfPublish(movie.getYearOfPublish());
        movieGetDto.setGenres(movie.getGenres());

        //TODO: work with map and stream instead
        for (ShowTime showTimeLeg : movie.getShowTimes()) {
            movieGetDto.AddShowTimesAsKVP(MapFromShowTimeToKVP(showTimeLeg));
        }

        return movieGetDto;
    }
    public KeyValuePair MapFromShowTimeToKVP(ShowTime showTime) {
        return new KeyValuePair(showTime.getId(), showTime.getStartTime().toString());
    }

    public ShowTimeDto MapFromShowTimeToShowTimeDto(ShowTime showTime){
        ShowTimeDto showTimeDto = new ShowTimeDto();
        showTimeDto.setStartTime(showTime.getStartTime());
        showTimeDto.setId(showTime.getId());
        showTimeDto.setMovie(MapFromMovieToKVP(showTime.getMovie()));
        showTimeDto.setSeatPackage(
                showTime.getSeatPackages()
                        .stream()
                        .map(this::MapFromSeatPackageToSeatPackageGetDto)
                        .collect(Collectors.toList()));

        return showTimeDto;
    }

    public KeyValuePair MapFromMovieToKVP(Movie movie) {
        return new KeyValuePair(movie.getId(), movie.getName());
    }

    public SeatPackageGetDto MapFromSeatPackageToSeatPackageGetDto(SeatPackage seatPackage)
    {
        SeatPackageGetDto seatPackageGetDto = new SeatPackageGetDto();
        KeyValuePair showTimeAsKVP = new KeyValuePair(seatPackage.getShowTime().getId(), seatPackage.getShowTime().getMovie().getName());
        seatPackageGetDto.setShowTime(showTimeAsKVP);
        seatPackageGetDto.setAvailable(seatPackage.isAvailable());
        seatPackageGetDto.setId(seatPackage.getId());
        seatPackageGetDto.setStatus(seatPackage.getStatus());
        seatPackageGetDto.setCol(seatPackage.getColNumber());
        seatPackageGetDto.setRow(seatPackage.getRowNUmber());

        return seatPackageGetDto;
    }


    public ReservationGetDto MapFromReservationToReservationGetDto(Reservation reservation){
        ReservationGetDto reservationGetDto = new ReservationGetDto();
        reservationGetDto.setId(reservation.getId());
        reservationGetDto.setStatus(reservation.getStatus());
        reservationGetDto.setPrice(reservation.getPrice());
        reservationGetDto.setStartTime(reservation.getStartTime());
        reservationGetDto.setOrderTime(reservation.getOrderTime());
        reservationGetDto.setCostumerUser(MapFromUserToKVP(reservation.getCostumerUser()));
        reservationGetDto.setSeatPackages(reservation
                .getSeatPackages()
                .stream()
                .map(this::MapFromSeatPackageToSeatPackageGetDto)
                .collect(Collectors.toList()));

        return reservationGetDto;
    }

    public KeyValuePair MapFromUserToKVP(CostumerUser costumerUser){
        return new KeyValuePair(costumerUser.getId(), costumerUser.getUsername());
    }

    public CostumerUserGetDto MapFromCostumerUserToCostumerUserGetDto(CostumerUser costumerUser) {
        CostumerUserGetDto costumerUserGetDto = new CostumerUserGetDto();
        costumerUserGetDto.setStatus(costumerUser.getStatus());
        costumerUserGetDto.setUsername(costumerUser.getUsername());
        costumerUserGetDto.setId(costumerUser.getId());
        costumerUserGetDto.setReservations(costumerUser.getReservations()
                .stream()
                .map(this::MapFromReservationToReservationGetDto)
                .collect(Collectors.toList()));

        return costumerUserGetDto;
    }

    //from DTO to DAO

    public CostumerUser MapFromCostumerUserSetDtoToCostumerUser(CostumerUserSetDto costumerUserSetDto)
    {
        return new CostumerUser(costumerUserSetDto.getUsername());
    }

    public CostumerUser MapFromCostumerUserGetDtoToCostumerUser(CostumerUserGetDto costumerUserGetDto) {
        CostumerUser costumerUser = new CostumerUser(costumerUserGetDto.getUsername());
        costumerUser.setId(costumerUser.getId());
        return costumerUser;
    }
}
