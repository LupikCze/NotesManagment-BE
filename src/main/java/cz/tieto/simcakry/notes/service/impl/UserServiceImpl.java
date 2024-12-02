package cz.tieto.simcakry.notes.service.impl;

import cz.tieto.simcakry.notes.exception.NotFoundException;
import cz.tieto.simcakry.notes.model.dto.user.UserCreateDTO;
import cz.tieto.simcakry.notes.model.dto.user.UserDTO;
import cz.tieto.simcakry.notes.model.dto.user.UserUpdateDTO;
import cz.tieto.simcakry.notes.model.entity.Tag;
import cz.tieto.simcakry.notes.model.entity.User;
import cz.tieto.simcakry.notes.repository.UserRepository;
import cz.tieto.simcakry.notes.service.NoteService;
import cz.tieto.simcakry.notes.service.TagService;
import cz.tieto.simcakry.notes.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final TagService tagService;
    private final NoteService noteService;

    private final ModelMapper modelMapper;

    public List<UserDTO> getAll() {
        return userRepository.findAll().stream().map(user -> modelMapper.map(user, UserDTO.class)).toList();
    }

    public UserDTO getById(UUID id) {
        User user = this.getUserById(id);

        return modelMapper.map(user,UserDTO.class);
    }

    public User getUserById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User with id " + id + " not found"));
    }

    public UserDTO create(UserCreateDTO newUserDTO) {
        User newUser = modelMapper.map(newUserDTO, User.class);

        return this.save(newUser);
    }

    public UserDTO update(UUID id, UserUpdateDTO userUpdateDTO) {
        User user = this.getUserById(id);

        modelMapper.map(userUpdateDTO, user);

        return this.save(user);
    }

    public String deleteById(UUID id) {
        User user = this.getUserById(id);
        
        this.delete(user);

        return "User with id: " + id + " deleted";
    }

    public String addTagToUser(UUID userId, UUID tagId) {
        User user = this.getUserById(userId);
        Tag tag = tagService.getTagById(tagId);

        tag.setUser(user);
        user.getTags().add(tag);

        this.save(user);
        tagService.save(tag);

        return "Tag with id: " + tagId + " added to user with id: " + userId;
    }

    public String removeTagFromUser(UUID userId, UUID tagId) {
        User user = this.getUserById(userId);
        Tag tag = tagService.getTagById(tagId);

        tag.setUser(null);
        user.getTags().remove(tag);

        this.save(user);
        tagService.save(tag);

        return "Tag with id: " + tagId + " removed from user with id: " + userId;
    }

    public void delete(User userToDelete) {
        userRepository.delete(userToDelete);
    }

    @Override
    public void deleteAll(Iterable<? extends User> usersToDelete) {
        userRepository.deleteAll(usersToDelete);
    }

    @Override
    public UserDTO save(User userToSave) {
        return modelMapper.map(userRepository.save(userToSave),UserDTO.class);
    }
}
