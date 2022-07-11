package restapi.webapp.Dtos.Set;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ShowTimeSetDto {
    private Long movieId;
    private LocalDateTime dateTime;
}
