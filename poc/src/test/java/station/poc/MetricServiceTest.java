package station.poc;

import org.junit.jupiter.api.Test;
import station.poc.data.models.MetricModel;
import station.poc.data.type.MetricType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MetricServiceTest {

    private final MetricService metricService = new MetricService();

    @Test
    void testGetMetricModelsWithInputMetricsAndMatchingEntityMetrics() {
        String entityMetrics = "TEMPERATURE,HUMIDITY";
        List<String> inputMetrics = List.of("temperature", "humidity");

        List<MetricModel> metricModels = metricService.getMetricModels(entityMetrics, inputMetrics);

        assertEquals(2, metricModels.size());
        assertEquals("TEMPERATURE", metricModels.get(0).getName());
        assertEquals(MetricType.TEMPERATURE.getUnit(), metricModels.get(0).getUnit());
        assertEquals("HUMIDITY", metricModels.get(1).getName());
        assertEquals(MetricType.HUMIDITY.getUnit(), metricModels.get(1).getUnit());
    }

    @Test
    void testGetMetricModelsWithInputMetricsButNoMatchingEntityMetrics() {
        String entityMetrics = "WIND_SPEED";
        List<String> inputMetrics = List.of("temperature", "humidity");

        List<MetricModel> metricModels = metricService.getMetricModels(entityMetrics, inputMetrics);

        assertTrue(metricModels.isEmpty());
    }

    @Test
    void testGetMetricModelsWithNullInputMetrics() {
        String entityMetrics = "TEMPERATURE,HUMIDITY";

        List<MetricModel> metricModels = metricService.getMetricModels(entityMetrics, null);

        //NO USER INPUT, USE ENTITY METRICS
        assertFalse(metricModels.isEmpty());
        assertEquals("TEMPERATURE", metricModels.get(0).getName());
        assertEquals("HUMIDITY", metricModels.get(1).getName());
        assertEquals(2, metricModels.size());
    }

    @Test
    void testGetMetricModelsWithNullEntityMetrics() {
        List<String> inputMetrics = List.of("temperature", "humidity");
        List<MetricModel> metricModels = metricService.getMetricModels(null, inputMetrics);
        assertTrue(metricModels.isEmpty());
    }


}
