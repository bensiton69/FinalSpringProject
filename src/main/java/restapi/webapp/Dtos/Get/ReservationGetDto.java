package restapi.webapp.Dtos.Get;

import lombok.Data;
import restapi.webapp.Builders.IHasId;
import restapi.webapp.Dtos.KeyValuePair;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class ReservationGetDto implements IHasId {
    private Long id;
    private Double price;
    private LocalDateTime orderTime;
    private LocalDateTime startTime;
    private List<SeatPackageDto> seatPackages = new ArrayList<>();
    private KeyValuePair costumerUser;
}
