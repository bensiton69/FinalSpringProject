package restapi.webapp.Models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data // For Lombok auto-generated methods
@Entity // JPA annotation to make the object storable SQL-based DB
@NoArgsConstructor
public class ShowTime {

    @Id
    @GeneratedValue
    private Long id;
    private LocalDateTime startTime;

    @ManyToOne
    private Movie movie;

    @OneToMany
    private List<SeatPackage> seatPackage = new ArrayList<SeatPackage>();

    public ShowTime(LocalDateTime startTime, Movie movie) {
        this.startTime = startTime;
        this.movie = movie;
    }
}
