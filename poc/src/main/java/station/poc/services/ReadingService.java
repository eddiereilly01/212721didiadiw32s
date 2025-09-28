package station.poc.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import station.poc.data.entity.ReadingEntity;
import station.poc.data.models.ReadingModel;
import station.poc.data.models.SendReadingModel;
import station.poc.data.repository.ReadingRepository;
import station.poc.data.type.StatisticType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class ReadingService {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final ReadingRepository readingRepository;
    private final MapperService mapperService;

    public ReadingService(ReadingRepository readingRepository, MapperService mapperService) {
        this.readingRepository = readingRepository;
        this.mapperService = mapperService;
    }

    public void save(SendReadingModel sendReadingModel) {
        log.info("Saving reading to DB. {}", sendReadingModel);
        ReadingEntity readingEntity = mapperService.mapReadingEntityFromModel(sendReadingModel);
        readingRepository.save(readingEntity);
    }

    public ReadingModel getReading(String beginning, String end, String statistic, int sensorId, String metric) {
        LocalDateTime beginDateTime = getBeginningTime(beginning);
        LocalDateTime endDateTime = getEndTime(end);

        ReadingModel readingModel = new ReadingModel(beginDateTime.toString(), endDateTime.toString(), null, null, null, null);
        List<ReadingEntity> readingEntities = readingRepository.findAllBySensorIdAndMetricAndTimestampBetween(sensorId, metric, beginDateTime, endDateTime);
        if (readingEntities.isEmpty()) {
            return null;
        }

        StatisticType statisticType = statistic == null ? StatisticType.AVG : getStatisticType(statistic.toUpperCase());
        switch (statisticType) {
            case MIN -> getMin(readingEntities, readingModel);
            case MAX -> getMax(readingEntities, readingModel);
            case SUM -> getSum(readingEntities, readingModel);
            default -> getAvg(readingEntities, readingModel);
        }
        return readingModel;
    }

    private void getAvg(List<ReadingEntity> entities, ReadingModel model) {
        float total = 0;
        for (ReadingEntity entity : entities) {
            total += entity.getValue();
        }
        model.setAverage(total / entities.size());
    }

    private void getMax(List<ReadingEntity> entities, ReadingModel model) {
        float max = entities.get(0).getValue();
        for (ReadingEntity entity : entities) {
            if (max <= entity.getValue()) {
                max = entity.getValue();
            }
        }
        model.setMax(max);
    }

    private void getMin(List<ReadingEntity> entities, ReadingModel model) {
        float min = entities.get(0).getValue();
        for (ReadingEntity entity : entities) {
            if (min >= entity.getValue()) {
                min = entity.getValue();
            }
        }
        model.setMin(min);
    }

    private void getSum(List<ReadingEntity> entities, ReadingModel model) {
        float sum = 0;
        for (ReadingEntity entity : entities) {
            sum += entity.getValue();
        }
        model.setSum(sum);
    }

    private LocalDateTime getBeginningTime(String beginning) {
        if (beginning == null) {
            //Set to use last 3 days
            beginning = LocalDate.now().minusDays(3).toString();
        }
        return LocalDate.parse(beginning, formatter).atStartOfDay();
    }

    private LocalDateTime getEndTime(String end) {
        if (end == null) {
            //Set to use last 3 days
            end = LocalDate.now().toString();
        }
        return LocalDate.parse(end, formatter).atTime(23, 59);
    }

    private StatisticType getStatisticType(String statistic) {
        StatisticType statisticType;
        List<String> validStatistics = Arrays.stream(StatisticType.values()).map(Enum::name).toList();
        if (statistic != null && validStatistics.contains(statistic)) {
            statisticType = StatisticType.valueOf(statistic.toUpperCase());
        } else {
            statisticType = StatisticType.AVG;
        }
        return statisticType;
    }
}
