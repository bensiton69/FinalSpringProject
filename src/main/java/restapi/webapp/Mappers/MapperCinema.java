package restapi.webapp.Mappers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import restapi.webapp.Dtos.Get.CostumerUserGetDto;
import restapi.webapp.Dtos.Get.ReservationGetDto;
import restapi.webapp.Dtos.KeyValuePair;
import restapi.webapp.Dtos.MovieGetDto;
import restapi.webapp.Dtos.SeatPackageDto;
import restapi.webapp.Dtos.ShowTimeDto;
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
                        .map(this::MapFromSeatPackageToSeatPackageDto)
                        .collect(Collectors.toList()));

        return showTimeDto;
    }

    public KeyValuePair MapFromMovieToKVP(Movie movie) {
        return new KeyValuePair(movie.getId(), movie.getName());
    }

    public SeatPackageDto MapFromSeatPackageToSeatPackageDto(SeatPackage seatPackage)
    {
        SeatPackageDto seatPackageDto = new SeatPackageDto();
        seatPackageDto.setAvailable(seatPackage.isAvailable());
        seatPackageDto.setId(seatPackage.getId());
        seatPackageDto.setCol(seatPackage.getColNumber());
        seatPackageDto.setRow(seatPackage.getRowNUmber());

        return seatPackageDto;
    }


    public ReservationGetDto MapFromReservationToReservationGetDto(Reservation reservation){
        ReservationGetDto reservationGetDto = new ReservationGetDto();
        reservationGetDto.setId(reservation.getId());
        reservationGetDto.setPrice(reservation.getPrice());
        reservationGetDto.setStartTime(reservationGetDto.getStartTime());
        reservationGetDto.setOrderTime(reservation.getOrderTime());
        reservationGetDto.setCostumerUser(MapFromUserToKVP(reservation.getCostumerUser()));
        reservationGetDto.setSeatPackages(reservation
                .getSeatPackages()
                .stream()
                .map(this::MapFromSeatPackageToSeatPackageDto)
                .collect(Collectors.toList()));

        return reservationGetDto;
    }

    public KeyValuePair MapFromUserToKVP(CostumerUser costumerUser){
        return new KeyValuePair(costumerUser.getId(), costumerUser.getUsername());
    }

    public CostumerUserGetDto MapFromCostumerUserToCostumerUserGetDto(CostumerUser costumerUser) {
        CostumerUserGetDto costumerUserGetDto = new CostumerUserGetDto();
        costumerUserGetDto.setUsername(costumerUser.getUsername());
        costumerUserGetDto.setId(costumerUser.getId());
        costumerUserGetDto.setReservations(costumerUser.getReservations()
                .stream()
                .map(this::MapFromReservationToReservationGetDto)
                .collect(Collectors.toList()));

        return costumerUserGetDto;
    }

    //from DTO to DAO

}
