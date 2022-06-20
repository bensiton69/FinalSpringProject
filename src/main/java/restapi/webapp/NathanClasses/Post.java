package restapi.webapp.NathanClasses;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data // For Lombok auto-generated methods
@Entity // JPA annotation to make the object storable SQL-based DB
@NoArgsConstructor
public class Post{
    // JPA Provider will populate the id
    @Id @GeneratedValue
    private Long id;
    private String title;
    private LocalDate creationDate;
    private String content;

    @JsonIgnore @OneToOne private Profile profile;

    public Post(String title, String content, Profile profile) {
        this.creationDate = LocalDate.now();
        this.title = title;
        this.content = content;
        this.profile = profile;
    }

}
