package station.poc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import station.poc.data.models.MetricModel;
import station.poc.data.type.MetricType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class MetricService
{

    public List<MetricModel> getMetricModels(String entityMetrics, List<String> inputMetrics) {
        log.info("Creating metric model list and appending to sensor model list. userMetrics[{}]", inputMetrics);
        List<MetricModel> metricModelList = new ArrayList<>();
        List<String> metricNames =  new ArrayList<>();

        if(inputMetrics != null){
            metricNames = inputMetrics.stream().map(String::toUpperCase).toList();
        } else{
            metricNames = Arrays.stream(MetricType.values()).map(Enum::name).toList();
        }

        if (entityMetrics != null) {
            List<String> splitEntityMetrics = List.of(entityMetrics.split(","));
            metricNames.stream().filter(splitEntityMetrics::contains)
                    .forEach(metric -> metricModelList.add(new MetricModel(metric, MetricType.valueOf(metric).getUnit(), null)));
        }
        return metricModelList;
    }

}