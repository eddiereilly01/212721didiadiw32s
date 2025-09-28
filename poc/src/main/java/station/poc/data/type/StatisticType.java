package station.poc.data.type;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatisticType {

    AVG("AVG"),
    MIN("MIN"),
    MAX("MAX"),
    SUM("SUM");

    private final String type;
}