package station.poc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import station.poc.data.models.ApiResponseModel;
import station.poc.data.models.SendReadingModel;
import java.util.List;

@RequestMapping("/sensors")
@Slf4j
@RestController
public class ApiController {

    private final ApiService apiService;
    public ApiController(ApiService apiService){
        this.apiService = apiService;
    }


    @GetMapping("/query/")
    ResponseEntity<ApiResponseModel> query(@RequestParam List<Integer> ids, @RequestParam(required = false) List<String> metrics, @RequestParam(required = false) String statistic, @RequestParam(required = false) String begin, @RequestParam(required = false) String end) {
        log.info("GET: Fetching sensor data for sensors with query params: ids[{}], metrics[{}], statistic: {}, begin: {}, end: {}", ids, metrics, statistic, begin, end);
        return ResponseEntity.ok(new ApiResponseModel(null));

    }

    @GetMapping("/query/all")
    ResponseEntity<ApiResponseModel> queryAll(@RequestParam(required = false) List<String> metrics, @RequestParam(required = false) String statistic, @RequestParam(required = false) String begin, @RequestParam(required = false) String end){
        log.info("GET: Fetching sensor data for all sensors with query params: metrics[{}], statistic: {}, begin: {}, end: {}", metrics, statistic, begin, end);
        return ResponseEntity.ok(new ApiResponseModel(null));
    }

    @PostMapping("/send-reading")
    public ResponseEntity<String> createReading(@RequestBody SendReadingModel request){
        log.info("POST: Adding reading data {}", request);
        apiService.addReading(request);
        return ResponseEntity.ok("Successfully saved reading.");
    }
}