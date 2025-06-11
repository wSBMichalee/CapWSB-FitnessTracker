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

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserServiceImpl userService;
    private final UserMapper userMapper;

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User addUser(@RequestBody User user) {
        return userService.createUser(user);
    }


        @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        return userService.findById(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") Long id) {
        if (userService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        }
    }

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

    @GetMapping("/email")
    public List<User> searchByEmailFragment(@RequestParam String email) {
        return userService.searchUsersByEmailFragment(email)
                .stream()
                .toList();
    }

    @GetMapping("/older/{time}")
    public List<UserDto> searchByAge(@PathVariable("time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate age) {
        return userService.searchUsersByAgeGreaterThan(age)
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    @GetMapping("/simple")
    public List<UserBasicDTO> getAllSimpleUsers() {
        return userService.findAllUsers().stream()
                .map(user -> new UserBasicDTO(user.getId(),user.getFirstName(), user.getLastName()))
                .toList();
    }
}
