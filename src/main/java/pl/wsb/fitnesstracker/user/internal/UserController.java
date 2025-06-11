package pl.wsb.fitnesstracker.user.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wsb.fitnesstracker.user.api.UserDetailsDTO;
import pl.wsb.fitnesstracker.user.api.UserUpdateDTO;

import java.util.List;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserService userService;

    @GetMapping("/simple")
    public List<UserBasicDTO> getAllUsersSimple() {
        return userService.getAllUsersSimple();
    }

    @GetMapping
    public List<UserDetailsDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDetailsDTO> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserEmailDTO>> searchByEmail(@RequestParam("email") String fragment) {
        return ResponseEntity.ok(userService.searchByEmailFragment(fragment));
    }

    @GetMapping("/older-than")
    public ResponseEntity<List<UserBasicDTO>> getUsersOlderThan(@RequestParam("age") int age) {
        return ResponseEntity.ok(userService.getUsersOlderThan(age));
    }

    @PostMapping
    public ResponseEntity<UserDetailsDTO> createUser(@RequestBody UserCreateDTO dto) {
        return ResponseEntity.ok(userService.createUser(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDetailsDTO> updateUser(@PathVariable Long id, @RequestBody UserUpdateDTO dto) {
        return userService.updateUser(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
