package restapi.webapp.Dtos.Get;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import restapi.webapp.Builders.IHasId;
import restapi.webapp.Dtos.KeyValuePair;
import restapi.webapp.Models.ShowTime;

import java.util.ArrayList;
import java.util.List;

@JsonPropertyOrder({"id","name", "link","duration","yearOfPublish","genres","showTimes"})
@Data
public class MovieGetDto implements IHasId {
    private Long id;
    private String name;
    private String link;
    private int duration;
    private int yearOfPublish;

    private String[] genres;
    private List<KeyValuePair> showTimes = new ArrayList<KeyValuePair>();

    public void AddShowTimesAsKVP(KeyValuePair keyValuePair) {
        showTimes.add(keyValuePair);
    }
}
