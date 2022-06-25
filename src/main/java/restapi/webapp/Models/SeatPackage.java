package restapi.webapp.Models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@Table(name = "SeatPackage")
public class SeatPackage{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int rowNUmber;
    private int colNumber;
    private boolean isAvailable;

    @ManyToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    public SeatPackage( int row, int col, boolean isAvailable) {
        this.rowNUmber = row;
        this.colNumber = col;
        this.isAvailable = isAvailable;
    }
}
