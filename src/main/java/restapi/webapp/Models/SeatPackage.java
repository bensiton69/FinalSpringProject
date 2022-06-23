package restapi.webapp.Models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
public class SeatPackage{
    @Id @GeneratedValue
    private Long id;
    private boolean isAvailable;
    private int row;
    private int col;

    @ManyToOne
    ShowTime showTime;

    public SeatPackage(int row, int col,boolean isAvailable, ShowTime showTime) {
        this.isAvailable = isAvailable;
        this.row = row;
        this.col = col;
        this.showTime = showTime;
    }
    @Override
    public String toString() {
        return super.toString() +
                "SeatPackage{" +
                "isAvailable=" + isAvailable +
                '}';
    }
}
