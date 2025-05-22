package pl.wsb.fitnesstracker.user.api;

import java.time.LocalDate;


public record UserDetailsDTO(Long id, String firstName, String lastName, LocalDate dateOfBirth, String email) {}