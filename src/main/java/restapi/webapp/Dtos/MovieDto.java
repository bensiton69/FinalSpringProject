package restapi.webapp.Dtos;

import restapi.webapp.Models.Movie;
import restapi.webapp.Models.ShowTime;

import java.util.ArrayList;
import java.util.List;

public class MovieDto {

    private Long id;
    private String name;
    private String link;
    private int duration;
    private int yearOfPublish;

    //TODO: Transfer to enum
    private String[] genres;
    private List<KeyValuePair> showTimes = new ArrayList<KeyValuePair>();

    public MovieDto(Movie m) {
        id = m.getId();
        name = m.getName();
        link = m.getLink();
        duration = m.getDuration();
        yearOfPublish = m.getYearOfPublish();
        genres = m.getGenres();
        setShowTimesAsSTlist(m.getShowTimes());

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getYearOfPublish() {
        return yearOfPublish;
    }

    public void setYearOfPublish(int yearOfPublish) {
        this.yearOfPublish = yearOfPublish;
    }

    public String[] getGenres() {
        return genres;
    }

    public void setGenres(String[] genres) {
        this.genres = genres;
    }

    public List<KeyValuePair> getShowTimes() {
        return showTimes;
    }

    public void setShowTimes(List<KeyValuePair> showTimes) {
        this.showTimes = showTimes;
    }

    public void setShowTimesAsSTlist(List<ShowTime> showTimes) {
        for (ShowTime showTime : showTimes)
        {
            this.showTimes.add(new KeyValuePair(showTime.getId(), showTime.getStartTime().toString()));
        }
    }
}
