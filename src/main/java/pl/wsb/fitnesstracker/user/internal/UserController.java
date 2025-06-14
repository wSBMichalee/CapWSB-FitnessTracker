package pl.wsb.fitnesstracker.user.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.api.UserBasicDTO;

import java.time.LocalDate;
import java.util.List;

/**
 * Kontroler REST obsługujący operacje na użytkownikach systemu.
 * Udostępnia końcówki API do pobierania, tworzenia, edycji, usuwania
 * oraz wyszukiwania użytkowników na podstawie różnych kryteriów.
 * Endpoint bazowy: /v1/users
 */
@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserServiceImpl userService;
    private final UserMapper userMapper;

    /**
     * Zwraca listę wszystkich użytkowników jako obiekty DTO.
     *
     * @return lista użytkowników w formacie {@link UserDto}
     */
    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    /**
     * Tworzy nowego użytkownika na podstawie danych wejściowych.
     *
     * @param user dane użytkownika do utworzenia
     * @return nowo utworzony użytkownik
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User addUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    /**
     * Zwraca dane użytkownika o podanym identyfikatorze.
     *
     * @param id identyfikator użytkownika
     * @return dane użytkownika jako {@link UserDto}
     * @throws ResponseStatusException jeśli użytkownik nie istnieje
     */
    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        return userService.findById(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    /**
     * Usuwa użytkownika o danym identyfikatorze.
     *
     * @param id identyfikator użytkownika do usunięcia
     * @return odpowiedź HTTP 204 jeśli usunięto, lub 404 jeśli użytkownik nie istnieje
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") Long id) {
        if (userService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * Aktualizuje dane użytkownika o podanym identyfikatorze.
     *
     * @param id identyfikator użytkownika
     * @param userDto nowe dane użytkownika
     * @return zaktualizowany użytkownik w formacie {@link UserDto},
     * lub 404 jeśli użytkownik nie istnieje
     */
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("userId") Long id, @RequestBody UserDto userDto) {
        return userService.findById(id)
                .map(existing -> {
                    var user = userMapper.toEntity(userDto);
                    user.setId(id);
                    var updated = userService.saveUser(user);
                    return ResponseEntity.ok(userMapper.toDto(updated));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Wyszukuje użytkowników, których adres e-mail zawiera podany fragment.
     *
     * @param email fragment e-maila do wyszukania
     * @return lista pasujących użytkowników
     */
    @GetMapping("/email")
    public List<User> searchByEmailFragment(@RequestParam String email) {
        return userService.searchUsersByEmailFragment(email)
                .stream()
                .toList();
    }

    /**
     * Zwraca użytkowników, którzy są starsi niż podana data (czyli urodzeni przed nią).
     *
     * @param age data graniczna (format ISO, np. 2000-01-01)
     * @return lista użytkowników w formacie {@link UserDto}
     */
    @GetMapping("/older/{time}")
    public List<UserDto> searchByAge(@PathVariable("time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate age) {
        return userService.searchUsersByAgeGreaterThan(age)
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    /**
     * Zwraca uproszczoną listę użytkowników zawierającą tylko ID, imię i nazwisko.
     *
     * @return lista użytkowników w formacie {@link UserBasicDTO}
     */
    @GetMapping("/simple")
    public List<UserBasicDTO> getAllSimpleUsers() {
        return userService.findAllUsers().stream()
                .map(user -> new UserBasicDTO(user.getId(),user.getFirstName(), user.getLastName()))
                .toList();
    }
}
