package station.poc.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import station.poc.data.entity.SensorEntity;
import java.util.List;

@Repository
public interface SensorRepository extends JpaRepository<SensorEntity, Integer> {
    List<SensorEntity> findByIdIn(List<Integer> id);
    List<SensorEntity> findAll();
}