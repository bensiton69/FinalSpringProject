package restapi.webapp.Models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

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
}
