package pl.wsb.fitnesstracker.user.internal;

import pl.wsb.fitnesstracker.user.api.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
class UserService {

    private final UserRepository userRepository;

    UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    List<User> getAllUsers() {
        return userRepository.findAll();
    }

    Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    List<User> searchUsersByEmailFragment(String emailFragment) {
        return userRepository.findByEmailIgnoreCaseContaining(emailFragment);
    }

    List<User> searchUsersByAgeGreaterThan(int age) {
        return userRepository.findByAgeGreaterThan(age);
    }

    User createUser(User user) {
        return userRepository.save(user);
    }

    void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    User updateUser(User user) {
        return userRepository.save(user);
    }
}
