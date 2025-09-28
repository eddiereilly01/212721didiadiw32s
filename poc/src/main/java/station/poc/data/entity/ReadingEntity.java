package station.poc.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
@Entity
@Table(name = "readings")
@Getter
@Setter
public class ReadingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "sensor_id")
    private Integer sensorId;
    private String metric;
    @Column(name = "reading_timestamp")
    private LocalDateTime timestamp;
    @Column(name = "reading_value")
    private Float value;
}