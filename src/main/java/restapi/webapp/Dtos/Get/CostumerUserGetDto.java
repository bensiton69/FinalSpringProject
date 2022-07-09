package restapi.webapp.Dtos.Get;

import lombok.Data;
import restapi.webapp.Builders.IHasId;
import restapi.webapp.Enums.Status;

import java.util.ArrayList;
import java.util.List;

@Data
public class CostumerUserGetDto implements IHasId {
    private Long id;
    private String username;
    private Status status;
    private List<ReservationGetDto> reservations = new ArrayList<>();
}
