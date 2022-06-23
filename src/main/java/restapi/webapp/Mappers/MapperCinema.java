//package restapi.webapp.Mappers;
//
//import restapi.webapp.Dtos.KeyValuePair;
//import restapi.webapp.Dtos.MovieDto;
//import restapi.webapp.Dtos.SeatPackageDto;
//import restapi.webapp.Dtos.ShowTimeDto;
//import restapi.webapp.Models.MovieLeg;
//import restapi.webapp.Models.SeatPackage;
//import restapi.webapp.Models.ShowTimeLeg;
//
//import java.util.stream.Collectors;
//
////TODO: implements IMapperCinema
//public class MapperCinema {
//
//
//    // from DAO to DTO
//    public static MovieDto MapFromMovieToMovieGetDto(MovieLeg movieLeg) {
//        MovieDto movieDto = new MovieDto();
//        movieDto.setId(movieLeg.getId());
//        movieDto.setName(movieLeg.getName());
//        movieDto.setLink(movieLeg.getLink());
//        movieDto.setDuration(movieLeg.getDuration());
//        movieDto.setYearOfPublish(movieLeg.getYearOfPublish());
//        movieDto.setGenres(movieLeg.getGenres());
//
//        //TODO: work with map and stream instead
//        for (ShowTimeLeg showTimeLeg : movieLeg.getShowTimeLegs()) {
//            movieDto.AddShowTimesAsKVP(MapFromShowTimeToKVP(showTimeLeg));
//        }
//
//        return movieDto;
//    }
//    public static KeyValuePair MapFromShowTimeToKVP(ShowTimeLeg showTimeLeg) {
//        return new KeyValuePair(showTimeLeg.getId(), showTimeLeg.getStartTime().toString());
//    }
//
//    public static ShowTimeDto MapFromShowTimeToShowTimeDto(ShowTimeLeg showTimeLeg){
//        ShowTimeDto showTimeDto = new ShowTimeDto();
//        showTimeDto.setStartTime(showTimeLeg.getStartTime());
//        showTimeDto.setId(showTimeLeg.getId());
//        showTimeDto.setMovie(MapFromMovieToKVP(showTimeLeg.getMovieLeg()));
//        showTimeDto.setSeatPackage(
//                showTimeLeg.getSeatPackages()
//                        .stream()
//                        .map(MapperCinema::MapFromSeatPackageToSeatPackageDto)
//                        .collect(Collectors.toList()));
//
//        return showTimeDto;
//    }
//
//    private static KeyValuePair MapFromMovieToKVP(MovieLeg movieLeg) {
//        return new KeyValuePair(movieLeg.getId(), movieLeg.getName());
//    }
//
//    public static SeatPackageDto MapFromSeatPackageToSeatPackageDto(SeatPackage seatPackage)
//    {
//        SeatPackageDto seatPackageDto = new SeatPackageDto();
//        seatPackageDto.setAvailable(seatPackage.isAvailable());
//        seatPackageDto.setId(seatPackage.getId());
//        seatPackageDto.setCol(seatPackage.getCol());
//        seatPackageDto.setRow(seatPackage.getRow());
//
//        return seatPackageDto;
//    }
//
//    //from DTO to DAO
//
//}
