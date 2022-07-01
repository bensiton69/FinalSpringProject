package restapi.webapp.Mappers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restapi.webapp.Dtos.Get.ReservationGetDto;
import restapi.webapp.Dtos.KeyValuePair;
import restapi.webapp.Dtos.MovieDto;
import restapi.webapp.Dtos.SeatPackageDto;
import restapi.webapp.Dtos.ShowTimeDto;
import restapi.webapp.Models.*;
import restapi.webapp.controllers.TestingController;

import java.util.stream.Collectors;

//TODO: implements IMapperCinema
public class MapperCinema {
    private static final Logger logger = LoggerFactory.getLogger(MapperCinema.class);


    // from DAO to DTO
    public static MovieDto MapFromMovieToMovieGetDto(Movie movie) {
        MovieDto movieDto = new MovieDto();
        movieDto.setId(movie.getId());
        movieDto.setName(movie.getName());
        movieDto.setLink(movie.getLink());
        movieDto.setDuration(movie.getDuration());
        movieDto.setYearOfPublish(movie.getYearOfPublish());
        movieDto.setGenres(movie.getGenres());

        //TODO: work with map and stream instead
        for (ShowTime showTimeLeg : movie.getShowTimes()) {
            movieDto.AddShowTimesAsKVP(MapFromShowTimeToKVP(showTimeLeg));
        }

        return movieDto;
    }
    public static KeyValuePair MapFromShowTimeToKVP(ShowTime showTime) {
        return new KeyValuePair(showTime.getId(), showTime.getStartTime().toString());
    }

    public static ShowTimeDto MapFromShowTimeToShowTimeDto(ShowTime showTime){
        ShowTimeDto showTimeDto = new ShowTimeDto();
        showTimeDto.setStartTime(showTime.getStartTime());
        showTimeDto.setId(showTime.getId());
        showTimeDto.setMovie(MapFromMovieToKVP(showTime.getMovie()));
        showTimeDto.setSeatPackage(
                showTime.getSeatPackages()
                        .stream()
                        .map(MapperCinema::MapFromSeatPackageToSeatPackageDto)
                        .collect(Collectors.toList()));

        return showTimeDto;
    }

    private static KeyValuePair MapFromMovieToKVP(Movie movie) {
        return new KeyValuePair(movie.getId(), movie.getName());
    }

    public static SeatPackageDto MapFromSeatPackageToSeatPackageDto(SeatPackage seatPackage)
    {
        SeatPackageDto seatPackageDto = new SeatPackageDto();
        seatPackageDto.setAvailable(seatPackage.isAvailable());
        seatPackageDto.setId(seatPackage.getId());
        seatPackageDto.setCol(seatPackage.getColNumber());
        seatPackageDto.setRow(seatPackage.getRowNUmber());

        return seatPackageDto;
    }


    public static ReservationGetDto MapFromReservationToReservationGetDto(Reservation reservation){
        ReservationGetDto reservationGetDto = new ReservationGetDto();
        reservationGetDto.setId(reservation.getId());
        reservationGetDto.setPrice(reservation.getPrice());
        reservationGetDto.setStartTime(reservationGetDto.getStartTime());
        reservationGetDto.setOrderTime(reservation.getOrderTime());
        reservationGetDto.setCostumerUser(MapFromUserToKVP(reservation.getCostumerUser()));
        reservationGetDto.setSeatPackages(reservation
                .getSeatPackages()
                .stream()
                .map(MapperCinema::MapFromSeatPackageToSeatPackageDto)
                .collect(Collectors.toList()));

        return reservationGetDto;
    }

    public static KeyValuePair MapFromUserToKVP(CostumerUser costumerUser){
        return new KeyValuePair(costumerUser.getId(), costumerUser.getUsername());
    }

    //from DTO to DAO

}
