package pl.wsb.fitnesstracker.user.internal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.wsb.fitnesstracker.user.api.*;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService, UserProvider {

    private final UserRepository userRepository;

    @Override
    public List<UserBasicDTO> findAllBasicInfo() {
        return List.of();
    }

    @Override
    public UserDetailsDTO findById(Long id) {
        return null;
    }

    @Override
    public UserDetailsDTO create(UserCreateDTO dto) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<UserEmailDTO> findByEmailFragment(String fragment) {
        return List.of();
    }

    @Override
    public List<UserBasicDTO> findOlderThan(int age) {
        return List.of();
    }

    @Override
    public UserDetailsDTO update(Long id, UserUpdateDTO dto) {
        return null;
    }

    @Override
    public User createUser(final User user) {
        log.info("Creating User {}", user);
        if (user.getId() != null) {
            throw new IllegalArgumentException("User has already DB ID, update is not permitted!");
        }
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUser(final Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public Optional<User> getUserByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public List<User> searchUsersByEmailFragment(String emailFragment) {
        String lowerFragment = emailFragment.toLowerCase();
        return userRepository.findAll().stream()
                .filter(user -> user.getEmail() != null &&
                        user.getEmail().toLowerCase().contains(lowerFragment))
                .toList();
    }

    @Override
    public List<User> searchUsersByAgeGreaterThan(int age) {
        return userRepository.findAll().stream()
                .filter(user -> user.getAge() > age)
                .toList();
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }
}