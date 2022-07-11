package restapi.webapp.Mappers;

import org.springframework.stereotype.Service;
import restapi.webapp.Dtos.Get.CostumerUserGetDto;
import restapi.webapp.Dtos.Get.ReservationGetDto;
import restapi.webapp.Dtos.KeyValuePair;
import restapi.webapp.Dtos.Get.MovieGetDto;
import restapi.webapp.Dtos.Get.SeatPackageGetDto;
import restapi.webapp.Dtos.Get.ShowTimeGetDto;
import restapi.webapp.Dtos.Set.CostumerUserSetDto;
import restapi.webapp.Dtos.Set.ShowTimeSetDto;
import restapi.webapp.Models.*;

@Service
public interface IMapperCinema {
    // from DAO to DTO
    MovieGetDto MapFromMovieToMovieGetDto(Movie movie);
    KeyValuePair MapFromShowTimeToKVP(ShowTime showTime);
    ShowTimeGetDto MapFromShowTimeToShowTimeDto(ShowTime showTime);
    KeyValuePair MapFromMovieToKVP(Movie movie);
    SeatPackageGetDto MapFromSeatPackageToSeatPackageGetDto(SeatPackage seatPackage);
    ReservationGetDto MapFromReservationToReservationGetDto(Reservation reservation);
    KeyValuePair MapFromUserToKVP(CostumerUser costumerUser);
    CostumerUserGetDto MapFromCostumerUserToCostumerUserGetDto(CostumerUser costumerUser);

    // from DTO to DAO
    CostumerUser MapFromCostumerUserSetDtoToCostumerUser(CostumerUserSetDto costumerUserSetDto);
    CostumerUser MapFromCostumerUserGetDtoToCostumerUser(CostumerUserGetDto costumerUserGetDto);

}
