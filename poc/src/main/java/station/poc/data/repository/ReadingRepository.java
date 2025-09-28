package station.poc.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import station.poc.data.entity.ReadingEntity;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReadingRepository extends JpaRepository<ReadingEntity, Integer> {
    List<ReadingEntity> findAllBySensorIdAndMetricAndTimestampBetween(int id, String metric, LocalDateTime beginning, LocalDateTime ending);
}