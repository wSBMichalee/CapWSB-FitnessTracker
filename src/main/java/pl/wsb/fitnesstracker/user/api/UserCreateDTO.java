package pl.wsb.fitnesstracker.user.api;

import java.time.LocalDate;

public record UserCreateDTO(
        String username,
        String firstName,
        String lastName,
        String email,
        LocalDate birthDate,
        String gender,
        String password
) {
}