package station.poc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import station.poc.data.entity.SensorEntity;
import station.poc.data.models.MetricModel;
import station.poc.data.models.ReadingModel;
import station.poc.data.models.SensorModel;
import station.poc.data.repository.SensorRepository;
import station.poc.data.type.MetricType;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SensorServiceTest {

    private SensorRepository sensorRepository;
    private MapperService mapperService;
    private MetricService metricService;
    private ReadingService readingService;
    private SensorService sensorService;

    @BeforeEach
    void setUp() {
        sensorRepository = mock(SensorRepository.class);
        mapperService = mock(MapperService.class);
        metricService = mock(MetricService.class);
        readingService = mock(ReadingService.class);
        sensorService = new SensorService(sensorRepository, mapperService, metricService, readingService);
    }

    @Test
    void testNoSensorEntitiesReturned() {
        when(sensorRepository.findByIdIn(List.of(1, 2))).thenReturn(Collections.emptyList());

        List<SensorModel> result = sensorService.getSensorDataForIds(List.of(1, 2));

        assertTrue(result.isEmpty());
        verifyNoInteractions(mapperService);
    }

    @Test
    void testGetSensorEntityShouldMapToModels() {
        List<SensorEntity> entities = List.of(new SensorEntity(1, "Sensor 1", "desc","TEMPERATURE" ));
        List<SensorModel> models = List.of(new SensorModel(1,"Sensor 1", "desc",null));
        when(sensorRepository.findByIdIn(List.of(1))).thenReturn(entities);
        when(mapperService.mapSensorEntityToModel(entities)).thenReturn(models);

        List<SensorModel> result = sensorService.getSensorDataForIds(List.of(1));

        assertEquals(models, result);
        verify(mapperService).mapSensorEntityToModel(entities);
    }

    @Test
    void testGetAllSensorEntities() {
        List<SensorEntity> entities = List.of(new SensorEntity(1, "Sensor 1", "desc","TEMPERATURE" ));
        when(sensorRepository.findAll()).thenReturn(entities);

        List<SensorEntity> result = sensorService.getAllSensorEntities();

        assertEquals(entities, result);
        verify(sensorRepository).findAll();
    }

    @Test
    void testGetAllSensorWithNoEntityData() {
        when(sensorRepository.findAll()).thenReturn(Collections.emptyList());

        List<SensorModel> result = sensorService.getAllSensorData();

        assertTrue(result.isEmpty());
        verifyNoInteractions(mapperService);
    }

    @Test
    void testGetAllSensorDataShouldMapToModels() {
        List<SensorEntity> entities = List.of(new SensorEntity(1, "Sensor 1", "desc","TEMPERATURE" ));
        List<SensorModel> models = List.of(new SensorModel(1, "Sensor 1", "desc",null ));
        when(sensorRepository.findAll()).thenReturn(entities);
        when(mapperService.mapSensorEntityToModel(entities)).thenReturn(models);

        List<SensorModel> result = sensorService.getAllSensorData();

        assertEquals(models, result);
        verify(mapperService).mapSensorEntityToModel(entities);
    }

    @Test
    void testAddMetricsToSensorModels() {
        SensorEntity entity = new SensorEntity(1, "Sensor 1", "desc","TEMPERATURE" );
        entity.setId(1);
        SensorModel model = new SensorModel(1, "Sensor 1", "desc",null );
        model.setId(1);

        List<SensorEntity> entities = List.of(entity);
        List<SensorModel> models = List.of(model);
        Map<Integer, String> idMetricsMap = Map.of(1, "TEMPERATURE");

        MetricModel metricModel = new MetricModel("TEMPERATURE", "C", null);

        when(mapperService.getEntityIdMetricsMap(entities)).thenReturn(idMetricsMap);
        when(metricService.getMetricModels("TEMPERATURE", List.of("TEMPERATURE")))
                .thenReturn(List.of(metricModel));

        List<SensorModel> result = sensorService.addMetricsToSensorModels(entities, models, List.of("TEMPERATURE"));

        assertEquals(1, result.get(0).getMetricModelList().size());
        assertEquals(metricModel, result.get(0).getMetricModelList().get(0));
    }

    @Test
    void testAddReadingsToSensorModels() {
        SensorModel sensorModel = new SensorModel(1, "Sensor 1", "desc",null);
        sensorModel.setId(1);
        MetricModel metricModel = new MetricModel("TEMPERATURE", "C", null);
        sensorModel.setMetricModelList(List.of(metricModel));

        ReadingModel readingModel = new ReadingModel("2025-01-01", "2025-01-02", 1f, null,null,null);
        when(readingService.getReading("2025-01-01", "2025-01-02", "AVG", 1, "TEMPERATURE"))
                .thenReturn(readingModel);

        List<SensorModel> result = sensorService.addReadingsToSensorModels("AVG", "2025-01-01", "2025-01-02", List.of(sensorModel));

        assertEquals(readingModel, result.get(0).getMetricModelList().get(0).getReading());
        verify(readingService).getReading("2025-01-01", "2025-01-02", "AVG", 1, "TEMPERATURE");
    }
}
