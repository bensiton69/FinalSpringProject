package restapi.webapp.Dtos.Get;

import lombok.Data;
import restapi.webapp.Dtos.KeyValuePair;
import restapi.webapp.Models.IActivable;
import restapi.webapp.Enums.Status;

@Data
public class SeatPackageGetDto implements IActivable {
    private Long id;
    private boolean isAvailable;
    private int row;
    private int col;
    private Status status ;
    private KeyValuePair showTime;
}
