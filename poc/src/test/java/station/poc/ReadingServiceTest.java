package station.poc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import station.poc.data.entity.ReadingEntity;
import station.poc.data.models.ReadingModel;
import station.poc.data.models.SendReadingModel;
import station.poc.data.repository.ReadingRepository;
import station.poc.services.MapperService;
import station.poc.services.ReadingService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReadingServiceTest {

    private ReadingRepository readingRepository;
    private MapperService mapperService;
    private ReadingService readingService;

    @BeforeEach
    void setUp() {
        readingRepository = mock(ReadingRepository.class);
        mapperService = mock(MapperService.class);
        readingService = new ReadingService(readingRepository, mapperService);
    }

    @Test
    void testSaveEntity() {
        SendReadingModel sendReadingModel = new SendReadingModel(1, "TEMPERATURE", 20.5f, LocalDateTime.now());
        ReadingEntity mappedEntity = new ReadingEntity(null,null,null,null, null);
        when(mapperService.mapReadingEntityFromModel(sendReadingModel)).thenReturn(mappedEntity);

        readingService.save(sendReadingModel);

        verify(mapperService).mapReadingEntityFromModel(sendReadingModel);
        verify(readingRepository).save(mappedEntity);
    }

    @Test
    void testGetReadingNoEntitiesFoundShouldReturnNull() {
        when(readingRepository.findAllBySensorIdAndMetricAndTimestampBetween(anyInt(), anyString(), any(), any()))
                .thenReturn(Collections.emptyList());

        ReadingModel result = readingService.getReading("2025-01-01", "2025-01-02", "AVG", 1, "TEMPERATURE");
        assertNull(result);
    }

    @Test
    void testGetReadingWithAverage() {
        ReadingEntity e1 = new ReadingEntity(null,null,null,null, null);
        e1.setValue(10f);
        ReadingEntity e2 = new ReadingEntity(null,null,null,null, null);
        e2.setValue(20f);
        List<ReadingEntity> entities = Arrays.asList(e1, e2);

        when(readingRepository.findAllBySensorIdAndMetricAndTimestampBetween(anyInt(), anyString(), any(), any()))
                .thenReturn(entities);

        ReadingModel result = readingService.getReading("2025-01-01", "2025-01-02", "AVG", 1, "TEMPERATURE");

        // Assert
        assertNotNull(result);
        assertEquals(15f, result.getAverage());
    }

    @Test
    void testGetReadingWithMin() {
        ReadingEntity e1 = new ReadingEntity(null,null,null,null, null);
        e1.setValue(5f);
        ReadingEntity e2 = new ReadingEntity(null,null,null,null, null);
        e2.setValue(8f);
        when(readingRepository.findAllBySensorIdAndMetricAndTimestampBetween(anyInt(), anyString(), any(), any()))
                .thenReturn(Arrays.asList(e1, e2));

        ReadingModel result = readingService.getReading("2025-01-01", "2025-01-02", "MIN", 1, "TEMPERATURE");

        assertEquals(5f, result.getMin());
    }

    @Test
    void testGetReadingWithMax() {
        ReadingEntity e1 = new ReadingEntity(null,null,null,null, null);
        e1.setValue(15f);
        ReadingEntity e2 = new ReadingEntity(null,null,null,null, null);
        e2.setValue(25f);
        when(readingRepository.findAllBySensorIdAndMetricAndTimestampBetween(anyInt(), anyString(), any(), any()))
                .thenReturn(Arrays.asList(e1, e2));

        ReadingModel result = readingService.getReading("2025-01-01", "2025-01-02", "MAX", 1, "TEMPERATURE");

        assertEquals(25f, result.getMax());
    }

    @Test
    void testGetReadingWithSum() {
        ReadingEntity e1 = new ReadingEntity(null,null,null,null, null);
        e1.setValue(2f);
        ReadingEntity e2 = new ReadingEntity(null,null,null,null, null);
        e2.setValue(3f);
        when(readingRepository.findAllBySensorIdAndMetricAndTimestampBetween(anyInt(), anyString(), any(), any()))
                .thenReturn(Arrays.asList(e1, e2));

        ReadingModel result = readingService.getReading("2025-01-01", "2025-01-02", "SUM", 1, "TEMPERATURE");

        assertEquals(5f, result.getSum());
    }

    @Test
    void testGetReadingInvalidStatisticShouldDefaultToAvg() {
        ReadingEntity e1 = new ReadingEntity(null,null,null,null, null);
        e1.setValue(10f);
        ReadingEntity e2 = new ReadingEntity(null,null,null,null, null);
        e2.setValue(30f);
        when(readingRepository.findAllBySensorIdAndMetricAndTimestampBetween(anyInt(), anyString(), any(), any()))
                .thenReturn(Arrays.asList(e1, e2));

        ReadingModel result = readingService.getReading("2025-01-01", "2025-01-02", "ABCDEF", 1, "TEMPERATURE");

        assertEquals(20f, result.getAverage());
    }

    @Test
    void testGetReadingWithNullDatesShouldUseDefaultRange() {
        ReadingEntity e1 = new ReadingEntity(null,null,null,null, null);
        e1.setValue(10f);
        when(readingRepository.findAllBySensorIdAndMetricAndTimestampBetween(anyInt(), anyString(), any(), any()))
                .thenReturn(List.of(e1));

        ReadingModel result = readingService.getReading(null, null, "AVG", 1, "TEMPERATURE");

        assertNotNull(result);
        verify(readingRepository).findAllBySensorIdAndMetricAndTimestampBetween(eq(1), eq("TEMPERATURE"), any(), any());
    }
}


