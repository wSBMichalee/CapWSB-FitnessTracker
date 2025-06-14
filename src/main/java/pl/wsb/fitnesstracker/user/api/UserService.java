package pl.wsb.fitnesstracker.user.api;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 *
 */
public interface UserService {


    User createUser(User user);


    Optional<User> findById(Long id);

    /**
     * Deletes a user by their ID.
     *
     * @param userId the ID of the user to delete
     */
    void deleteUser(Long userId);

    /**
     * Searches for users whose email contains the given fragment (case-insensitive).
     *
     * @param emailFragment the fragment to search for
     * @return list of matching users
     */
    List<User> searchUsersByEmailFragment(String emailFragment);

    /**
     * Finds users older than the given age.
     *
     * @param age the minimum age
     * @return list of users older than the given age
     */
    List<User> searchUsersByAgeGreaterThan(LocalDate age);

    /**
     * Saves or updates a user.
     *
     * @param user the user to save
     * @return the saved user
     */
    User saveUser(User user);
}