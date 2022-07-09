package restapi.webapp.Models;

import lombok.Data;
import lombok.NoArgsConstructor;
import restapi.webapp.Enums.Status;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@Table(name = "SeatPackage")
public class SeatPackage implements IActivable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int rowNUmber;
    private int colNumber;
    private boolean isAvailable;
    private Status status = Status.Active;

    @ManyToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @ManyToOne
    @JoinColumn(name = "showTime_id")
    private ShowTime showTime;

    public SeatPackage( int row, int col, boolean isAvailable) {
        this.rowNUmber = row;
        this.colNumber = col;
        this.isAvailable = isAvailable;
    }

    public SeatPackage(int rowNUmber, int colNumber, boolean isAvailable, ShowTime showTime) {
        this.rowNUmber = rowNUmber;
        this.colNumber = colNumber;
        this.isAvailable = isAvailable;
        this.showTime = showTime;
    }

    @Override
    public String toString() {
        return "SeatPackage{" +
                "id=" + id +
                '}';
    }
}
