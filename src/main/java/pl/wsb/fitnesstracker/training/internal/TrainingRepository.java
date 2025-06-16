package pl.wsb.fitnesstracker.training.internal;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.wsb.fitnesstracker.training.api.Training;

import java.util.List;

public interface TrainingRepository extends JpaRepository<Training, Long> {
    List<Training> findByUserId(Long userId);
    List<Training> findByActivityType(ActivityType activityType);

}