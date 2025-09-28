package station.poc.data.type;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MetricType {

    TEMPERATURE("C"),
    HUMIDITY("%"),
    PRECIPITATION("MM"),
    WIND_SPEED("KM/HR");

    private final String unit;
}
