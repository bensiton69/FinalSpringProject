package restapi.webapp.Models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

//@Data
//@Entity
//@NoArgsConstructor
public abstract class Seat {
//    @Id @GeneratedValue
    private UUID id;

    private int row;
    private int col;

    public Seat(int row, int col) {
        this.row = row;
        this.col = col;
//        id = UUID.randomUUID();
    }

    @Override
    public String toString() {
        return "Seat{" +
                "id=" + id +
                ", row=" + row +
                ", col=" + col +
                '}';
    }
}

