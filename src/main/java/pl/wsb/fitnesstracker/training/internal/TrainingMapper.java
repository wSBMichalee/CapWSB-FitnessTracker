package pl.wsb.fitnesstracker.training.internal;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Component;
import pl.wsb.fitnesstracker.training.api.Training;
import pl.wsb.fitnesstracker.training.api.TrainingDto;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.api.UserDto;

@Component
public class TrainingMapper {

    private final ObjectMapper objectMapper;

    public TrainingMapper() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public TrainingDto toDto(Training training) {
        if (training == null) return null;

        TrainingDto dto = new TrainingDto();
        dto.setId(training.getId());
        dto.setUserId(training.getUser() != null ? training.getUser().getId() : null);
        dto.setUser(toUserDto(training.getUser()));
        dto.setStartTime(training.getStartTime());
        dto.setEndTime(training.getEndTime());
        dto.setActivityType(training.getActivityType());
        dto.setDistance(training.getDistance());
        dto.setAverageSpeed(training.getAverageSpeed());

        System.out.println("Mapping Training to TrainingDto with user: " + dto.getUser() +
                ", activityType: " + dto.getActivityType() +
                ", distance: " + dto.getDistance() +
                ", averageSpeed: " + dto.getAverageSpeed());

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

        UserDto userDto = new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getBirthdate(),
                user.getEmail()

        );
        return userDto;
    }
}
