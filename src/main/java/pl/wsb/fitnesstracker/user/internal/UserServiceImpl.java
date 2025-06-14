package pl.wsb.fitnesstracker.user.internal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.wsb.fitnesstracker.user.api.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Implementacja logiki biznesowej związanej z użytkownikami.
 * Odpowiada za operacje CRUD oraz wyszukiwanie użytkowników na podstawie kryteriów (e-mail, wiek).
 * Klasa korzysta z repozytorium {@link UserRepository} do komunikacji z bazą danych.
 * Działa jako komponent serwisowy w kontekście Springa.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService, UserProvider {

    private final UserRepository userRepository;


    /**
     * Wyszukuje użytkownika po jego ID.
     *
     * @param id identyfikator użytkownika
     * @return {@link Optional} zawierający użytkownika, jeśli istnieje
     */
    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Tworzy nowego użytkownika w systemie.
     * Jeśli użytkownik ma już przypisane ID, zgłaszany jest wyjątek.
     *
     * @param user nowy użytkownik do zapisania
     * @return zapisany użytkownik
     * @throws IllegalArgumentException jeśli użytkownik ma już ID (czyli istnieje w bazie)
     */
    @Override
    public User createUser(final User user) {
        log.info("Creating User {}", user);
        if (user.getId() != null) {
            throw new IllegalArgumentException("User has already DB ID, update is not permitted!");
        }
        return userRepository.save(user);
    }

    /**
     * Pobiera użytkownika po ID.
     *
     * @param userId identyfikator użytkownika
     * @return użytkownik jako {@link Optional}
     */
    @Override
    public Optional<User> getUser(final Long userId) {
        return userRepository.findById(userId);
    }

    /**
     * Wyszukuje użytkownika na podstawie adresu e-mail.
     *
     * @param email pełny adres e-mail
     * @return użytkownik jako {@link Optional}, jeśli istnieje
     */
    @Override
    public Optional<User> getUserByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Zwraca wszystkich użytkowników znajdujących się w bazie danych.
     *
     * @return lista wszystkich użytkowników
     */
    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Usuwa użytkownika na podstawie jego ID.
     *
     * @param userId identyfikator użytkownika do usunięcia
     */
    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    /**
     * Wyszukuje użytkowników, których adres e-mail zawiera podany fragment (ignorując wielkość liter).
     *
     * @param emailFragment fragment adresu e-mail
     * @return lista pasujących użytkowników
     */
    @Override
    public List<User> searchUsersByEmailFragment(String emailFragment) {
        String lowerFragment = emailFragment.toLowerCase();
        return userRepository.findAll().stream()
                .filter(user -> user.getEmail() != null &&
                        user.getEmail().toLowerCase().contains(lowerFragment))
                .toList();
    }

    /**
     * Wyszukuje użytkowników, którzy są starsi niż podana data (czyli urodzili się wcześniej).
     *
     * @param age data graniczna (np. 2000-01-01)
     * @return lista użytkowników starszych niż wskazana data
     */
    @Override
    public List<User> searchUsersByAgeGreaterThan(LocalDate age) {
        return userRepository.findAll().stream()
                .filter(user -> user.getBirthdate().isBefore(age))
                .toList();
    }

    /**
     * Zapisuje zmodyfikowanego użytkownika do bazy danych.
     * Może służyć do aktualizacji istniejących danych.
     *
     * @param user użytkownik do zapisania
     * @return zaktualizowany użytkownik
     */
    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }
}