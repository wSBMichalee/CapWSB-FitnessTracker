package pl.wsb.fitnesstracker.training.api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wsb.fitnesstracker.training.internal.TrainingMapper;
import pl.wsb.fitnesstracker.training.internal.TrainingRepository;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.api.UserProvider;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TrainingService {

    private final TrainingRepository trainingRepository;
    private final TrainingMapper trainingMapper;
    private final UserProvider userProvider;

    public Training createTraining(TrainingDto training) {

        Optional<User> user = userProvider.getUser(training.getUser());
        if (user.isPresent()) {
            Training newTraining = this.trainingMapper.fromDto(training, user.get());
            return trainingRepository.save(newTraining);

        }
        return null;
    }

    public List<Training> getTrainingsByUserId(Long userId) {
        return trainingRepository.findByUserId(userId);
    }

    public List<Training> findAll() {
        return trainingRepository.findAll();
    }

    public Training updateTraining(Long id, Map<String, Object> updates) {
        Training training = trainingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Training not found"));

        if (updates.containsKey("distance")) {
            training.setDistance(Double.parseDouble(updates.get("distance").toString()));
        }
        return trainingRepository.save(training);
    }

    public Training updateTrainingFully(Long id, TrainingDto trainingDto) {
        Training existingTraining = trainingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Training with ID " + id + " not found"));

        User user = userProvider.getUser(trainingDto.getUser())
                .orElseThrow(() -> new IllegalArgumentException("User with ID " + trainingDto.getUser() + " not found"));

        if (trainingDto.getStartTime() == null || trainingDto.getEndTime() == null) {
            throw new IllegalArgumentException("Start time and end time cannot be null.");
        }

        if (trainingDto.getStartTime().after(trainingDto.getEndTime())) {
            throw new IllegalArgumentException("Start time must be before end time.");
        }

        existingTraining.setUser(user);
        existingTraining.setStartTime(trainingDto.getStartTime());
        existingTraining.setEndTime(trainingDto.getEndTime());
        existingTraining.setActivityType(trainingDto.getActivityType());
        existingTraining.setDistance(trainingDto.getDistance());
        existingTraining.setAverageSpeed(trainingDto.getAverageSpeed());

        return trainingRepository.save(existingTraining);
    }

}

