package restapi.webapp.Services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import restapi.webapp.Models.Movie;
import restapi.webapp.Models.SeatPackage;
import restapi.webapp.Models.ShowTime;
import restapi.webapp.Repositories.ShowTimeRepos;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShowTimeService {
    private static final Logger logger = LoggerFactory.getLogger(ShowTimeService.class);
    private final LocalTime openingTime;
    private final LocalTime closingTime;
    private final int numOfRows;
    private final int numOfCols;
    private final ShowTimeRepos showTimeRepos;

    public ShowTimeService(ShowTimeRepos showTimeRepos) {
        this.showTimeRepos = showTimeRepos;
        openingTime = LocalTime.of(9,00);
        closingTime = LocalTime.of(21,00);
        numOfRows = 10;
        numOfCols = 10;
    }

    /**
     * receive a movie and datetime and will initialize it to showtime
     * @param movie to create show time
     * @param startTime at the requested time
     * @return ShowTime
     */
    public ShowTime InitShowTime(Movie movie, LocalDateTime startTime){
        ShowTime showTime = new ShowTime(startTime, movie);
        initSeatPackageForShowTime(showTime);
        movie.addShowTime(showTime);
        return showTime;
    }

    /**
     * receive a movies and create a show times
     * @return a list of showtimes that corresponding to the movie list
     */
    public List<ShowTime> createNewShowTimes(List<Movie> movies){
        LocalDateTime dateTime = null;
        List<ShowTime> showTimes = new ArrayList<>();

        for (Movie m: movies){
            dateTime = nextAvailableDateTimeByST(showTimes);
            ShowTime showTime = new ShowTime(dateTime, m);
            initSeatPackageForShowTime(showTime);
            m.addShowTime(showTime);
            showTimes.add(showTime);
        }

        return showTimes;
    }

    /**
     * will provide the next Available DateTime by opening/ closing time,
     * considering the last showtime.
     * @param showTimes the showtimes in the system
     * @return
     */
    private LocalDateTime nextAvailableDateTimeByST(List<ShowTime> showTimes) {

        ShowTime last = showTimes.size() == 0 ?
                ((List<ShowTime>)showTimeRepos.findAll())
                        .get(((List<ShowTime>) showTimeRepos.findAll()).size() -1) :
                showTimes.get(showTimes.size() - 1);

        LocalDateTime roundUpLastShowTimeFinishingTime =
                roundUpMinutes(last.getStartTime().plusMinutes(last.getMovie().getDuration()));
        LocalDateTime toCheck = LocalDateTime.of(roundUpLastShowTimeFinishingTime.toLocalDate(), closingTime);


        return roundUpLastShowTimeFinishingTime.isBefore(toCheck) ?
                roundUpLastShowTimeFinishingTime :
                LocalDateTime.of(roundUpLastShowTimeFinishingTime.toLocalDate().plusDays(1), openingTime);
    }

    /**
     * Will receive a Showtime and initialize the seatPackages
     * @param showTime
     */
    private void initSeatPackageForShowTime(ShowTime showTime){
        List<SeatPackage> seatPackages = new ArrayList<>();

        for (int i=0;i<numOfRows;i++)
            for (int j=0;j<numOfCols;j++)
                seatPackages.add(new SeatPackage(i,j, true, showTime));
        showTime.setSeatPackages(seatPackages);
    }

    /**
     * will round up to the closest half an hour
     * @param dateTime
     * @return
     */
    private LocalDateTime roundUpMinutes(LocalDateTime dateTime){
        int numOfMinutesToRoundUpTo = 30;

        dateTime = dateTime.truncatedTo(ChronoUnit.MINUTES);

        int currentMinutes = dateTime.getMinute();

        if (currentMinutes < numOfMinutesToRoundUpTo)
            return dateTime.plusMinutes(numOfMinutesToRoundUpTo - currentMinutes);
        else
            return dateTime.plusMinutes(60 - currentMinutes);

    }

}
