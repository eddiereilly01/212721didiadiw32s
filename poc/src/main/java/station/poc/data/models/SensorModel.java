package station.poc.data.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class SensorModel {
    private Integer id;
    private String name;
    private String description;
    private List<MetricModel> metricModelList;
}
