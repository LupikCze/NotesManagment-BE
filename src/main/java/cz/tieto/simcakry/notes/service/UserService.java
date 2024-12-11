package cz.tieto.simcakry.notes.service;

import cz.tieto.simcakry.notes.model.dto.user.UserCreateDTO;
import cz.tieto.simcakry.notes.model.dto.user.UserDTO;
import cz.tieto.simcakry.notes.model.dto.user.UserUpdateDTO;
import cz.tieto.simcakry.notes.model.dto.user.UserWithAll;
import cz.tieto.simcakry.notes.model.entity.User;

import java.util.List;
import java.util.UUID;


public interface UserService {
    List<UserDTO> getAll();
    UserWithAll getUserWithAllById(UUID id);
    UserDTO getById(UUID id);
    User getUserById(UUID id);
    UserDTO update(UUID id, UserUpdateDTO userUpdateDTO);
    UserDTO create(UserCreateDTO newUserDTO);
    String deleteById(UUID id);
    String addTagToUser(UUID userId, UUID tagId);
    String addGroupToUser(UUID userId, UUID groupId);
    String addNoteToUser(UUID userId, UUID noteId);
    String removeTagFromUser(UUID userId, UUID tagId);
    String removeNoteFromUser(UUID userId, UUID noteId);
    String removeGroupFromUser(UUID userId, UUID groupId);
    void delete(User userToDelete);
    void deleteAll(Iterable<? extends User> usersToDelete);
    UserDTO save(User userToSave);
    UserDTO getUserByEmail(String email);
    String authenticateUser(String email,String password);
}
