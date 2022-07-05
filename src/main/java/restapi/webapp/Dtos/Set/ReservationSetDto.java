package restapi.webapp.Dtos.Set;

import lombok.Data;

import java.util.List;

@Data
public class ReservationSetDto {
    private List<Long> seatPackagesId;
    private Long costumerUserId;
}
