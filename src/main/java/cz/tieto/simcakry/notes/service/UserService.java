package cz.tieto.simcakry.notes.service;

import cz.tieto.simcakry.notes.model.dto.user.UserCreateDTO;
import cz.tieto.simcakry.notes.model.dto.user.UserDTO;
import cz.tieto.simcakry.notes.model.dto.user.UserUpdateDTO;
import cz.tieto.simcakry.notes.model.entity.User;

import java.util.List;
import java.util.UUID;


public interface UserService {
    List<UserDTO> getAll();
    UserDTO getById(UUID id);
    User getUserById(UUID id);
    UserDTO update(UUID id, UserUpdateDTO userUpdateDTO);
    UserDTO create(UserCreateDTO newUserDTO);
    String deleteById(UUID id);
    String addTagToUser(UUID userId, UUID tagId);
    String removeTagFromUser(UUID userId, UUID tagId);
    void delete(User userToDelete);
    void deleteAll(Iterable<? extends User> usersToDelete);
    UserDTO save(User userToSave);
    UserDTO getUserByEmail(String email);
    String authenticateUser(String email,String password);
}
