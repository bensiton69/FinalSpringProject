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
        openingTime = LocalTime.of(11,00);
        closingTime = LocalTime.of(00,30);
        numOfRows = 10;
        numOfCols = 10;
    }

    public ShowTime InitShowTime(Movie movie, LocalDateTime startTime){
        ShowTime showTime = new ShowTime(startTime, movie);
        initSeatPackageForShowTime(showTime);
        movie.addShowTime(showTime);
        return showTime;
    }

    public List<ShowTime> createNewShowTimes(List<Movie> movies){
        LocalDateTime dateTime = null;
        List<ShowTime> showTimes = new ArrayList<>();

        for (Movie m: movies){
//            dateTime = nextAvailableDateTime();
            dateTime = nextAvailableDateTimeByST(showTimes);
            ShowTime showTime = new ShowTime(dateTime, m);
            initSeatPackageForShowTime(showTime);
            m.addShowTime(showTime);
            showTimes.add(showTime);
        }

        return showTimes;
    }

    private LocalDateTime nextAvailableDateTime() {
        ShowTime last = ((List<ShowTime>)showTimeRepos.findAll())
                .get(((List<ShowTime>) showTimeRepos.findAll()).size() -1);

        LocalDateTime roundUpLastShowTimeFinishingTime =
                roundUpMinutes(last.getStartTime().plusMinutes(last.getMovie().getDuration()));

        return roundUpLastShowTimeFinishingTime.isBefore(LocalDateTime.of(LocalDate.now(), closingTime)) ?
                roundUpLastShowTimeFinishingTime :
                LocalDateTime.of(roundUpLastShowTimeFinishingTime.toLocalDate(), openingTime);
    }

    private LocalDateTime nextAvailableDateTimeByST(List<ShowTime> showTimes) {

        ShowTime last = showTimes.size() == 0 ?
                ((List<ShowTime>)showTimeRepos.findAll())
                        .get(((List<ShowTime>) showTimeRepos.findAll()).size() -1) :
                showTimes.get(showTimes.size() - 1);

        LocalDateTime roundUpLastShowTimeFinishingTime =
                roundUpMinutes(last.getStartTime().plusMinutes(last.getMovie().getDuration()));
        LocalDateTime toCheck = LocalDateTime.of(last.getStartTime().toLocalDate().plusDays(1), closingTime);
        return roundUpLastShowTimeFinishingTime.isBefore(toCheck) ?
                roundUpLastShowTimeFinishingTime :
                LocalDateTime.of(roundUpLastShowTimeFinishingTime.toLocalDate(), openingTime);
    }

    private void initSeatPackageForShowTime(ShowTime showTime){
        List<SeatPackage> seatPackages = new ArrayList<>();

        for (int i=0;i<numOfRows;i++)
            for (int j=0;j<numOfCols;j++)
                seatPackages.add(new SeatPackage(i,j, true, showTime));
        showTime.setSeatPackages(seatPackages);
    }

    private LocalDateTime roundUpMinutes(LocalDateTime dateTime){
        int numOfMinutesToRoundUpTo = 30;

//        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
//        df.format(dateTime);
        int currentMinutes = dateTime.getMinute();

        if (currentMinutes < numOfMinutesToRoundUpTo)
            return dateTime.plusMinutes(numOfMinutesToRoundUpTo - currentMinutes);
        else
            return dateTime.plusMinutes(60 - currentMinutes);

    }

}
