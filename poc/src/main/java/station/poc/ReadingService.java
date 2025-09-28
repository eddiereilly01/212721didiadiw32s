package station.poc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import station.poc.data.entity.ReadingEntity;
import station.poc.data.models.SendReadingModel;
import station.poc.data.repository.ReadingRepository;

@Service
@Slf4j
public class ReadingService {

    private final ReadingRepository readingRepository;
    private final MapperService mapperService;

    public ReadingService(ReadingRepository readingRepository, MapperService mapperService){
        this.readingRepository = readingRepository;
        this.mapperService = mapperService;
    }

    public void save(SendReadingModel createReadingModel){
        log.info("Saving reading to DB. {}", createReadingModel);
        ReadingEntity readingEntity = mapperService.mapReadingEntityFromModel(createReadingModel);
        readingRepository.save(readingEntity);
    }

}
