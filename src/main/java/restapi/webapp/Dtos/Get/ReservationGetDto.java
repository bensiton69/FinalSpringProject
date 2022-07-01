package restapi.webapp.Dtos.Get;

import lombok.Data;
import restapi.webapp.Dtos.KeyValuePair;
import restapi.webapp.Dtos.SeatPackageDto;
import restapi.webapp.Models.ShowTime;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class ReservationGetDto {
    private Long id;
    private Double price;
    private LocalDateTime orderTime;
    private LocalDateTime startTime;
    private List<SeatPackageDto> seatPackages = new ArrayList<>();
    private KeyValuePair costumerUser;
}
