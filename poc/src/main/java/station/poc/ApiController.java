package station.poc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import station.poc.data.models.ApiResponseModel;
import station.poc.data.models.SendReadingModel;
import station.poc.data.type.MetricType;
import station.poc.services.ApiService;

import java.util.List;

@RequestMapping("/sensors")
@Slf4j
@RestController
public class ApiController {

    private final ApiService apiService;

    public ApiController(ApiService apiService) {
        this.apiService = apiService;
    }

    @GetMapping("/query/")
    ResponseEntity<ApiResponseModel> query(@RequestParam List<Integer> ids, @RequestParam(required = false) List<String> metrics, @RequestParam(required = false) String statistic, @RequestParam(required = false) String begin, @RequestParam(required = false) String end) {
        log.info("GET: Fetching sensor data for sensors with query params: ids[{}], metrics[{}], statistic: {}, begin: {}, end: {}", ids, metrics, statistic, begin, end);
        ApiResponseModel responseModel = apiService.query(ids, metrics, statistic, begin, end);
        return ResponseEntity.ok(responseModel);
    }

    @GetMapping("/query/all")
    ResponseEntity<ApiResponseModel> queryAll(@RequestParam(required = false) List<String> metrics, @RequestParam(required = false) String statistic, @RequestParam(required = false) String begin, @RequestParam(required = false) String end) {
        log.info("GET: Fetching sensor data for all sensors with query params: metrics[{}], statistic: {}, begin: {}, end: {}", metrics, statistic, begin, end);
        ApiResponseModel responseModel = apiService.queryAll(metrics, statistic, begin, end);
        return ResponseEntity.ok(responseModel);
    }


    @PostMapping("/send-reading")
    public ResponseEntity<String> sendReading(@RequestBody SendReadingModel request) {
        log.info("POST: Adding reading data sensor_id={}, value={} metric={} timestamp={}", request.getSensorId(), request.getValue(), request.getMetric(), request.getTimestamp());

        try{
          MetricType type = MetricType.valueOf(request.getMetric().toUpperCase());
        }catch (Exception e){
            return  ResponseEntity.badRequest().body("User input error. Ensure your request includes one of the following metrics: TEMPERATURE, HUMIDITY, PRECIPITATION, WIND_SPEED");
        }

        if((request.getMetric() == null) ){
            return  ResponseEntity.badRequest().body("User input error. POST request must include a metric field.");
        }

        if((request.getValue() == null) ){
            return  ResponseEntity.badRequest().body("User input error. POST request must include a value field.");
        }

        if((request.getSensorId() == null)){
          return  ResponseEntity.badRequest().body("User input error. POST request must include a sensor_id field.");
        }
        
        apiService.addReading(request);
        return ResponseEntity.ok("Successfully saved reading.");
    }
}