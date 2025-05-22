package pl.wsb.fitnesstracker.user.api;

import java.util.List;

public interface UserService {
    List<UserBasicDTO> findAllBasicInfo();
    UserDetailsDTO findById(Long id);
    UserDetailsDTO create(UserCreateDTO dto);
    void delete(Long id);
    List<UserEmailDTO> findByEmailFragment(String fragment);
    List<UserBasicDTO> findOlderThan(int age);
    UserDetailsDTO update(Long id, UserUpdateDTO dto);

    User createUser(User user);
}