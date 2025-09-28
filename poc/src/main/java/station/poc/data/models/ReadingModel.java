package station.poc.data.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReadingModel {
    private String beginning;
    private String ending;
    private Float average;
    private Float min;
    private Float max;
    private Float sum;
}

