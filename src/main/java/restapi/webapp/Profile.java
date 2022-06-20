package restapi.webapp;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Data // For Lombok auto-generated methods
@Entity // JPA annotation to make the object storable SQL-based DB
@NoArgsConstructor //for Jackson ObjectMapper class
public class Profile {
    @Id @GeneratedValue
    private Long id;
    private LocalDate creationDate;
    private String fullName;

    // JPA @OneToMany annotation indicating the relationship between Profile & Employee
    // is stored in the database tables in Post "profile" property,
    // i.e. profile’s primary key will be stored as a foreign key in the POSTS table.
    @JsonIgnore
    @OneToMany(mappedBy = "profile")
    // Eager Loading to avoid NullPointerException
    private List<Post> posts = new ArrayList<>();

    public Profile(String fullName){
        this.fullName = fullName;
    }
}
