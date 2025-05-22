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

    /**
     * Deletes a user by their ID.
     * @param userId the ID of the user to delete
     */
    void deleteUser(Long userId);

    /**
     * Searches for users whose email contains the given fragment (case-insensitive).
     * @param emailFragment the fragment to search for
     * @return list of matching users
     */
    List<User> searchUsersByEmailFragment(String emailFragment);

    /**
     * Finds users older than the given age.
     * @param age the minimum age
     * @return list of users older than the given age
     */
    List<User> searchUsersByAgeGreaterThan(int age);

    /**
     * Saves or updates a user.
     * @param user the user to save
     * @return the saved user
     */
    User saveUser(User user);
}