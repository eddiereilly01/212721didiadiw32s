package station.poc.services;

import org.springframework.stereotype.Service;
import station.poc.data.entity.SensorEntity;
import station.poc.data.models.ApiResponseModel;
import station.poc.data.models.SendReadingModel;
import station.poc.data.models.SensorModel;

import java.util.List;


@Service
public class ApiService {

    private final SensorService sensorService;
    private final ReadingService readingService;

    public ApiService(SensorService sensorService, ReadingService readingService){
        this.sensorService = sensorService;
        this.readingService = readingService;
    }

    public void addReading(SendReadingModel sendReadingModel){
        readingService.save(sendReadingModel);
    }

    public ApiResponseModel query(List<Integer>ids, List<String> inputMetrics, String statistic, String begin, String end){
        List<SensorModel> sensorModels = sensorService.getSensorDataForIds(ids);
        List<SensorEntity> sensorEntities = sensorService.getSensorEntitiesForIds(ids);
        return getApiResponseModel(inputMetrics, statistic, begin, end, sensorEntities, sensorModels);
    }

    public ApiResponseModel queryAll(List<String> inputMetrics, String statistic, String begin, String end){
        //This could be improved.. going Db twice for same thing
        List<SensorModel> sensorModels = sensorService.getAllSensorData();
        List<SensorEntity> sensorEntities = sensorService.getAllSensorEntities();
        return getApiResponseModel(inputMetrics, statistic, begin, end, sensorEntities, sensorModels);
    }

    private ApiResponseModel getApiResponseModel(List<String> inputMetrics, String statistic, String begin, String end, List<SensorEntity> sensorEntities, List<SensorModel> sensorModels) {
        if(sensorModels == null || sensorModels.isEmpty()){
            return new ApiResponseModel(List.of());
        }

        List<SensorModel> sensorModelListWithMetrics = sensorService.addMetricsToSensorModels(sensorEntities, sensorModels, inputMetrics);
        List<SensorModel> sensorModelListWithReadings = sensorService.addReadingsToSensorModels(statistic, begin, end, sensorModelListWithMetrics);
        return new ApiResponseModel(sensorModelListWithReadings);
    }
}
