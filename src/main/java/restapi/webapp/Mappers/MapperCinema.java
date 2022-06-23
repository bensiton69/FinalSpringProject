package restapi.webapp.Mappers;

import org.springframework.stereotype.Component;
import restapi.webapp.Dtos.KeyValuePair;
import restapi.webapp.Dtos.MovieDto;
import restapi.webapp.Dtos.SeatPackageDto;
import restapi.webapp.Dtos.ShowTimeDto;
import restapi.webapp.Models.Movie;
import restapi.webapp.Models.SeatPackage;
import restapi.webapp.Models.ShowTime;

import java.util.stream.Collectors;

//TODO: implements IMapperCinema
public class MapperCinema {


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
        for (ShowTime showTime : movie.getShowTimes()) {
            movieDto.AddShowTimesAsKVP(MapFromShowTimeToKVP(showTime));
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
        seatPackageDto.setCol(seatPackage.getCol());
        seatPackageDto.setRow(seatPackage.getRow());

        return seatPackageDto;
    }

    //from DTO to DAO

}
