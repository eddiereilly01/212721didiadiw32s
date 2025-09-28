package station.poc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import station.poc.data.entity.SensorEntity;
import station.poc.data.models.SensorModel;
import station.poc.data.repository.SensorRepository;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class SensorService {

    private final SensorRepository sensorRepository;
    private final MapperService mapperService;
    private final MetricService metricService;
    private final ReadingService readingService;


    public SensorService(SensorRepository sensorRepository, MapperService mapperService, MetricService metricService, ReadingService readingService) {
        this.sensorRepository = sensorRepository;
        this.mapperService = mapperService;
        this.metricService = metricService;
        this.readingService = readingService;
    }

    public List<SensorModel> getSensorDataForIds(List<Integer> ids) {
        List<SensorEntity> sensorEntityList = getSensorEntitiesForIds(ids);
        if (sensorEntityList.isEmpty()) {
            return List.of();
        }
        return mapperService.mapSensorEntityToModel(sensorEntityList);
    }

    public List<SensorEntity> getSensorEntitiesForIds(List<Integer> ids) {
        log.info("Fetching sensor data with ids[{}] from repository", ids);
        return sensorRepository.findByIdIn(ids);
    }

    public List<SensorEntity> getAllSensorEntities() {
        log.info("Fetching ALL sensor data from repository.");
        return sensorRepository.findAll();
    }

    public List<SensorModel> getAllSensorData() {
        List<SensorEntity> sensorEntityList = getAllSensorEntities();
        if (sensorEntityList.isEmpty()) {
            return List.of();
        }
        return mapperService.mapSensorEntityToModel(sensorEntityList);
    }

    public List<SensorModel> addMetricsToSensorModels(List<SensorEntity> sensorEntities, List<SensorModel> sensorModels, List<String> inputMetrics) {
        log.info("Adding metric data to sensor model.");
        Map<Integer, String> entityIdMap = mapperService.getEntityIdMetricsMap(sensorEntities);
        sensorModels.forEach(sensorModel ->
                sensorModel.setMetricModelList(metricService.getMetricModels(entityIdMap.get(sensorModel.getId()), inputMetrics)));
        return sensorModels;
    }

    public List<SensorModel> addReadingsToSensorModels(String statistic, String begin, String end, List<SensorModel> sensorModels) {
        log.info("Adding reading data to sensor model.");
        sensorModels.forEach(sensorModel ->
                sensorModel.getMetricModelList().forEach(metricModel ->
                        metricModel.setReading(readingService.getReading(begin, end, statistic, sensorModel.getId(), metricModel.getName())))
        );
        return sensorModels;
    }
}
