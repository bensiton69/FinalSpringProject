package restapi.webapp.Models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
public class Seat {
    @Id @GeneratedValue
    private UUID id;

    private int row;
    private int col;


}

