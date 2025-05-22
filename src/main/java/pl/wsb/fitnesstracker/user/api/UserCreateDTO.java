package pl.wsb.fitnesstracker.user.api;

import java.time.LocalDate;

public  record UserCreateDTO(String firstName, String lastName, LocalDate dateOfBirth, String email) {}