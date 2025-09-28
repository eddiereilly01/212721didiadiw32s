package station.poc;

import org.springframework.stereotype.Service;
import station.poc.data.models.SendReadingModel;


@Service
public class ApiService {

    private final ReadingService readingService;

    public ApiService(ReadingService readingService){
        this.readingService = readingService;
    }

    public void addReading(SendReadingModel createReadingModel){
        readingService.save(createReadingModel);
    }
}
