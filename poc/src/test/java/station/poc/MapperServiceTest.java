package station.poc;

import org.junit.jupiter.api.Test;
import station.poc.data.entity.ReadingEntity;
import station.poc.data.entity.SensorEntity;
import station.poc.data.models.SendReadingModel;
import station.poc.data.models.SensorModel;
import station.poc.services.MapperService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class MapperServiceTest {

    private final MapperService mapperService = new MapperService();

    @Test
    void testMapSensorEntityToModel() {
        SensorEntity entity1 = new SensorEntity(1, "Sensor 1", "description", "TEMPERATURE,HUMIDITY");
        SensorEntity entity2 = new SensorEntity(2, "Sensor 2", "description", "WIND_SPEED");
        List<SensorEntity> entities = List.of(entity1, entity2);

        List<SensorModel> models = mapperService.mapSensorEntityToModel(entities);

        assertEquals(2, models.size());
        assertEquals(entity1.getId(), models.get(0).getId());
        assertEquals(entity2.getId(), models.get(1).getId());
        assertEquals(entity1.getName(), models.get(0).getName());
        assertEquals(entity1.getDescription(), models.get(0).getDescription());
        assertNull(models.get(0).getMetricModelList()); //MetricModels added later in flow
    }

    @Test
    void testGetEntityIdMetricsMap() {
        SensorEntity entity1 = new SensorEntity(1, "Sensor 1", "description", "TEMPERATURE,HUMIDITY");
        SensorEntity entity2 = new SensorEntity(2, "Sensor 2", "description", "WIND_SPEED");
        List<SensorEntity> entities = List.of(entity1, entity2);

        Map<Integer, String> map = mapperService.getEntityIdMetricsMap(entities);

        assertEquals(2, map.size());
        assertEquals("TEMPERATURE,HUMIDITY", map.get(1));
        assertEquals("WIND_SPEED", map.get(2));
    }

    @Test
    void testMapReadingEntityFromCreateReadingModel() {
        // Arrange
        LocalDateTime timestamp = LocalDateTime.of(2025, 1, 1, 12, 0);
        SendReadingModel model = new SendReadingModel(1, "temperature", 25.5f, timestamp);

        // Act
        ReadingEntity entity = mapperService.mapReadingEntityFromModel(model);

        // Assert
        assertEquals(1, entity.getSensorId());
        assertEquals("temperature", entity.getMetric());
        assertEquals(timestamp, entity.getTimestamp());
        assertEquals(25.5f, entity.getValue());
    }

    @Test
    void testMapReadingEntityFromCreateReadingModelWithNullTimestamp() {
        SendReadingModel model = new SendReadingModel(2, "TEMPERATURE", 45.0f, null);

        ReadingEntity entity = mapperService.mapReadingEntityFromModel(model);

        assertEquals(2, entity.getSensorId());
        assertEquals("TEMPERATURE", entity.getMetric());
        assertNotNull(entity.getTimestamp()); // should be auto-generated
        assertEquals(45.0f, entity.getValue());
    }
}
