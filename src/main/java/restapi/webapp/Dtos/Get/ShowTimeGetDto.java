package restapi.webapp.Dtos.Get;

import restapi.webapp.Builders.IHasId;
import restapi.webapp.Dtos.KeyValuePair;

import java.time.LocalDateTime;
import java.util.List;

public class ShowTimeGetDto implements IHasId {
    private Long id;
    private LocalDateTime startTime;

    private KeyValuePair movie;
    private List<SeatPackageGetDto> seatPackage;

    public List<SeatPackageGetDto> getSeatPackage() {
        return seatPackage;
    }

    public void setSeatPackage(List<SeatPackageGetDto> seatPackage) {
        this.seatPackage = seatPackage;
    }

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

    public ShowTimeGetDto() {
    }
}
