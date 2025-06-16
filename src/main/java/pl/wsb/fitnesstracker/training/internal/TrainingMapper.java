package pl.wsb.fitnesstracker.training.internal;

import org.springframework.stereotype.Component;
import pl.wsb.fitnesstracker.training.api.Training;
import pl.wsb.fitnesstracker.training.api.TrainingDto;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.api.UserDto;

@Component
public class TrainingMapper {

    public TrainingDto toDto(Training training) {
        if (training == null) return null;

        TrainingDto dto = new TrainingDto();

        dto.setUserId(training.getUser() != null ? training.getUser().getId() : null);
        dto.setUser(toUserDto(training.getUser()));
        dto.setStartTime(training.getStartTime());
        dto.setEndTime(training.getEndTime());
        dto.setActivityType(training.getActivityType());
        dto.setDistance(training.getDistance());
        dto.setAverageSpeed(training.getAverageSpeed());

        return dto;
    }

    public Training fromDto(TrainingDto dto, User user) {
        if (dto == null) return null;

        Training training = new Training(
                user,
                dto.getStartTime(),
                dto.getEndTime(),
                dto.getActivityType(),
                dto.getDistance(),
                dto.getAverageSpeed()
        );

        training.setId(dto.getId());
        return training;
    }

    private UserDto toUserDto(User user) {
        if (user == null) return null;

        return new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getBirthdate(),
                user.getEmail()
        );
    }

}