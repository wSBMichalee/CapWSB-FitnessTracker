package pl.wsb.fitnesstracker.user.api;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.Period;

/**
 *Reprezentuje użytkownika systemu, przechowywanego w bazie danych.
 * Klasa jest encją JPA z mapowaniem na tabelę "users".
 * Zawiera podstawowe dane użytkownika, takie jak imię, nazwisko, data urodzenia i adres e-mail.
 * Dostępne są metody umożliwiające obliczenie wieku na podstawie daty urodzenia.
 *
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Nullable
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "birthdate", nullable = false)
    private LocalDate birthdate;

    @Column(nullable = false, unique = true)
    private String email;

    public User(

            final String firstName,
            final String lastName,
            final LocalDate birthdate,
            final String email) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.email = email;
    }
    public int getAge() {
        if (birthdate != null) {
            LocalDate today = LocalDate.now();
            return Period.between(birthdate, today).getYears();
        }
        return 0;
    }
}

