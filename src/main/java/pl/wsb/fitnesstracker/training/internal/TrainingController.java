package pl.wsb.fitnesstracker.training.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wsb.fitnesstracker.training.api.Training;
import pl.wsb.fitnesstracker.training.api.TrainingDto;
import pl.wsb.fitnesstracker.training.api.TrainingService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/trainings")
@RequiredArgsConstructor
public class TrainingController {
    private final TrainingService trainingService;
    private final TrainingMapper trainingMapper;


    @GetMapping
    public List<TrainingDto> getAllTrainings() {
        return trainingService.findAll().stream()
                .map(trainingMapper::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Training createTraining(@RequestBody TrainingDto trainingDto) {
        Training savedTraining = trainingService.createTraining(trainingDto);
        return trainingService.createTraining(trainingDto);
    }

    @PatchMapping("/{id}")
    public TrainingDto updateTraining(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Training updated = trainingService.updateTraining(id, updates);
        return trainingMapper.toDto(updated);
    }

    @PutMapping("/{trainingId}")
    public ResponseEntity<?> updateTrainingFully(
            @PathVariable Long trainingId,
            @RequestBody TrainingDto trainingDto
    ) {
        if (trainingDto == null || trainingDto.getUser() == null) {
            return ResponseEntity.badRequest().body("User ID is required.");
        }

        try {
            Training updatedTraining = trainingService.updateTrainingFully(trainingId, trainingDto);
            return ResponseEntity.ok(updatedTraining);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred.");
        }
    }


}