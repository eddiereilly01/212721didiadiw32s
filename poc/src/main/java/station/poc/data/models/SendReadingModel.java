package station.poc.data.models;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class SendReadingModel {

    private Integer sensorId;
    private String metric;
    private Float value;
    private LocalDateTime timestamp;
}
