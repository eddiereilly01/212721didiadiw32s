package station.poc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import station.poc.data.entity.ReadingEntity;
import station.poc.data.models.SendReadingModel;
import java.time.LocalDateTime;

@Service
@Slf4j
public class MapperService {

    public ReadingEntity mapReadingEntityFromModel(SendReadingModel createReadingModel){
        log.info("Mapping ReadingEntity from POST request. {}", createReadingModel);
        ReadingEntity readingEntity = new ReadingEntity();
        readingEntity.setSensorId(createReadingModel.getSensorId());
        readingEntity.setMetric(createReadingModel.getMetric());
        if(createReadingModel.getTimestamp() != null){
            readingEntity.setTimestamp(createReadingModel.getTimestamp());
        }else{
            readingEntity.setTimestamp(LocalDateTime.now());
        }
        readingEntity.setValue(createReadingModel.getValue());
        return readingEntity;
    }
}
