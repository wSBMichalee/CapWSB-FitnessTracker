package pl.wsb.fitnesstracker.user.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

//    @PostMapping
//    public UserDto addUser(@RequestBody UserDto userDto) {
//        var user = userMapper.fromDto(userDto);
//        var savedUser = userService.saveUser(user);
//        return userMapper.toDto(savedUser);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
//        return userService.findUserById(id)
//                .map(userMapper::toDto)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
//        if (userService.findUserById(id).isPresent()) {
//            userService.deleteUser(id);
//            return ResponseEntity.noContent().build();
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
//        if (userService.findUserById(id).isPresent()) {
//            var user = userMapper.fromDto(userDto);
//            user.setId(id);
//            var updatedUser = userService.saveUser(user);
//            return ResponseEntity.ok(userMapper.toDto(updatedUser));
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

    @GetMapping("/search/email")
    public List<UserDto> searchByEmailFragment(@RequestParam String email) {
        return userService.searchUsersByEmailFragment(email)
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    @GetMapping("/search/age")
    public List<UserDto> searchByAge(@RequestParam int age) {
        return userService.searchUsersByAgeGreaterThan(age)
                .stream()
                .map(userMapper::toDto)
                .toList();
    }
}
