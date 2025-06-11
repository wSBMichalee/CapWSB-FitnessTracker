package pl.wsb.fitnesstracker.user.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wsb.fitnesstracker.user.api.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserBasicDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserBasicDTO(user.getId(), user.getFirstName() + " " + user.getLastName()))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserBasicDTO> getAllUsersSimple() {
        return userRepository.findAll().stream()
                .map(user -> new UserBasicDTO(user.getId(), user.getFirstName() + " " + user.getLastName()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserDetailsDTO> getUserById(Long id) {
        return userRepository.findById(id).map(userMapper::toDetailsDto);
    }

    @Override
    public List<UserEmailDTO> searchByEmailFragment(String fragment) {
        return userRepository.findByEmailIgnoreCaseContaining(fragment).stream()
                .map(user -> new UserEmailDTO(user.getId(), user.getEmail()))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserBasicDTO> getUsersOlderThan(int age) {
        LocalDate dateThreshold = LocalDate.now().minusYears(age);
        return userRepository.findAll().stream()
                .filter(user -> user.getBirthdate().isBefore(dateThreshold))
                .map(user -> new UserBasicDTO(user.getId(), user.getFirstName() + " " + user.getLastName()))
                .collect(Collectors.toList());
    }

    @Override
    public UserDetailsDTO createUser(UserCreateDTO dto) {
        // Sprawdź, czy DTO nie zawiera nulli i ma wszystkie wymagane pola
        if (dto.firstName() == null || dto.lastName() == null || dto.birthdate() == null || dto.email() == null) {
            throw new IllegalArgumentException("Wszystkie pola muszą być wypełnione");
        }

        User user = new User();
        user.setFirstName(dto.firstName());
        user.setLastName(dto.lastName());
        user.setBirthdate(dto.birthdate());
        user.setEmail(dto.email());

        return userMapper.toDetailsDto(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<UserDetailsDTO> updateUser(Long id, UserUpdateDTO dto) {
        return userRepository.findById(id).map(user -> {
            if (dto.firstName() != null) user.setFirstName(dto.firstName());
            if (dto.lastName() != null) user.setLastName(dto.lastName());
            if (dto.birthdate() != null) user.setBirthdate(dto.birthdate());
            if (dto.email() != null) user.setEmail(dto.email());
            return userMapper.toDetailsDto(userRepository.save(user));
        });
    }
}
