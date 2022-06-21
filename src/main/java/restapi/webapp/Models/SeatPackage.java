package restapi.webapp.Models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
public class SeatPackage {
    @Id
    @GeneratedValue
    private Long id;
    private boolean isAvailable;

    @OneToOne
    private Seat seat;
}
