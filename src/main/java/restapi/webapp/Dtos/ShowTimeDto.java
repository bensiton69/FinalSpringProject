package restapi.webapp.Dtos;

import restapi.webapp.Models.Movie;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

public class ShowTimeDto {
    private Long id;
    private LocalDateTime startTime;

    private KeyValuePair movie;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public KeyValuePair getMovie() {
        return movie;
    }

    public void setMovie(KeyValuePair movie) {
        this.movie = movie;
    }

    public ShowTimeDto() {
    }
}
