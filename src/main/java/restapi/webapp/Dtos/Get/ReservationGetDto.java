package restapi.webapp.Dtos.Get;

import lombok.Data;
import restapi.webapp.Builders.IHasId;
import restapi.webapp.Dtos.KeyValuePair;
import restapi.webapp.Models.IActivable;
import restapi.webapp.Enums.Status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class ReservationGetDto implements IHasId, IActivable {
    private Status status;
    private Long id;
    private Double price;
    private LocalDateTime orderTime;
    private LocalDateTime startTime;
    private List<SeatPackageGetDto> seatPackages = new ArrayList<>();
    private KeyValuePair costumerUser;
}
