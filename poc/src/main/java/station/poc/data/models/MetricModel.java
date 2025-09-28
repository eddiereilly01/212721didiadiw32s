package station.poc.data.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MetricModel{
    private String name;
    private String unit;
    private ReadingModel reading;
}