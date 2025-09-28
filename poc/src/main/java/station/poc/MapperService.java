package station.poc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import station.poc.data.entity.ReadingEntity;
import station.poc.data.entity.SensorEntity;
import station.poc.data.models.SendReadingModel;
import station.poc.data.models.SensorModel;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class MapperService {

    public List<SensorModel> mapSensorEntityToModel(List<SensorEntity> sensorEntity) {
        log.info("Mapping SensorEntities to SensorModel.");
        List<SensorModel> sensorModels = new ArrayList<>();
        sensorEntity.forEach(entity -> sensorModels.add(new SensorModel(entity.id, entity.name, entity.description, null)));
        return sensorModels;
    }

    public Map<Integer, String> getEntityIdMetricsMap(List<SensorEntity> sensorEntities){
        log.info("Getting SensorEntity ID map.");
        Map<Integer, String> entityIdMap = new HashMap<>();
        sensorEntities.forEach(entity -> entityIdMap.put(entity.getId(), entity.getMetrics()));
        return entityIdMap;
    }

    public ReadingEntity mapReadingEntityFromModel(SendReadingModel sendReadingModel){
        log.info("Mapping ReadingEntity from POST request. {}", sendReadingModel);
        ReadingEntity readingEntity = new ReadingEntity(null,null,null,null, null);
        readingEntity.setSensorId(sendReadingModel.getSensorId());
        readingEntity.setMetric(sendReadingModel.getMetric());
        if(sendReadingModel.getTimestamp() != null){
            readingEntity.setTimestamp(sendReadingModel.getTimestamp());
        }else{
            readingEntity.setTimestamp(LocalDateTime.now());
        }
        readingEntity.setValue(sendReadingModel.getValue());
        return readingEntity;
    }
}
