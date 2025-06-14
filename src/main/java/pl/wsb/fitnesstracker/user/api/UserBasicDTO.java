package pl.wsb.fitnesstracker.user.api;

/**
 * @param id
 * @param firstName
 * @param lastName
 */
public record UserBasicDTO(Long id, String firstName,String lastName) {
}