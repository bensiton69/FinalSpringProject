package restapi.webapp.Dtos.Get;

import lombok.Data;
import lombok.NoArgsConstructor;
import restapi.webapp.Builders.IHasId;
import restapi.webapp.Dtos.KeyValuePair;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class ShowTimeGetDto implements IHasId {
    private Long id;
    private LocalDateTime startTime;

    private KeyValuePair movie;
    private List<SeatPackageGetDto> seatPackage;
}
