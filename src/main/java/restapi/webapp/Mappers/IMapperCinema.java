package restapi.webapp.Mappers;

import org.springframework.stereotype.Service;
import restapi.webapp.Dtos.Get.CostumerUserGetDto;
import restapi.webapp.Dtos.Get.ReservationGetDto;
import restapi.webapp.Dtos.KeyValuePair;
import restapi.webapp.Dtos.Get.MovieGetDto;
import restapi.webapp.Dtos.Get.SeatPackageDto;
import restapi.webapp.Dtos.Get.ShowTimeDto;
import restapi.webapp.Models.*;

@Service
public interface IMapperCinema {
    // from DAO to DTO
    MovieGetDto MapFromMovieToMovieGetDto(Movie movie);
    KeyValuePair MapFromShowTimeToKVP(ShowTime showTime);
    ShowTimeDto MapFromShowTimeToShowTimeDto(ShowTime showTime);
    KeyValuePair MapFromMovieToKVP(Movie movie);
    SeatPackageDto MapFromSeatPackageToSeatPackageDto(SeatPackage seatPackage);
    ReservationGetDto MapFromReservationToReservationGetDto(Reservation reservation);
    KeyValuePair MapFromUserToKVP(CostumerUser costumerUser);
    CostumerUserGetDto MapFromCostumerUserToCostumerUserGetDto(CostumerUser costumerUser);
    }
