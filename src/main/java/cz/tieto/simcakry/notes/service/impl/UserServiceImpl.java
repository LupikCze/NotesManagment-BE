package cz.tieto.simcakry.notes.service.impl;

import cz.tieto.simcakry.notes.exception.NotFoundException;
import cz.tieto.simcakry.notes.model.dto.group.GroupDTO;
import cz.tieto.simcakry.notes.model.dto.note.NoteDTO;
import cz.tieto.simcakry.notes.model.dto.tag.TagDTO;
import cz.tieto.simcakry.notes.model.dto.user.*;
import cz.tieto.simcakry.notes.model.entity.Group;
import cz.tieto.simcakry.notes.model.entity.Note;
import cz.tieto.simcakry.notes.model.entity.Tag;
import cz.tieto.simcakry.notes.model.entity.User;
import cz.tieto.simcakry.notes.repository.UserRepository;
import cz.tieto.simcakry.notes.security.JwtTokenUtil;
import cz.tieto.simcakry.notes.security.service.AuthService;
import cz.tieto.simcakry.notes.service.GroupService;
import cz.tieto.simcakry.notes.service.NoteService;
import cz.tieto.simcakry.notes.service.TagService;
import cz.tieto.simcakry.notes.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final TagService tagService;
    private final NoteService noteService;
    private final GroupService groupService;

    private final ModelMapper modelMapper;
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;

    public UserDTO getUserByEmail(String email){
        return modelMapper.map(userRepository.findByEmail(email),UserDTO.class);
    }

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


    public UserWithAll getUserWithAllById(UUID id) {
        UserDTO userDTO = this.getById(id);
        User user =this.getUserById(id);

        UserWithAll userWithAll = modelMapper.map(userDTO, UserWithAll.class);

        userWithAll.setGroups(
                user.getGroups().stream()
                        .map(group -> modelMapper.map(group, GroupDTO.class))
                        .collect(Collectors.toList())
        );
        userWithAll.setNotes(
                user.getNotes().stream()
                        .map(note -> modelMapper.map(note, NoteDTO.class))
                        .collect(Collectors.toList())
        );
        userWithAll.setTags(
                user.getTags().stream()
                        .map(tag -> modelMapper.map(tag, TagDTO.class))
                        .collect(Collectors.toList())
        );

        return userWithAll;
    }

    public UserDTO create(UserCreateDTO newUserDTO) {
        User newUser = modelMapper.map(newUserDTO, User.class);
        UserRegisterDTO userRegister = authService.register(newUser.getEmail(),newUser.getPassword());

        newUser.setEmail(userRegister.getEmail());
        newUser.setPassword(userRegister.getPassword());

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

    public String addNoteToUser(UUID userId, UUID noteId) {
        User user = this.getUserById(userId);
        Note note = noteService.getNoteById(noteId);

        note.setUser(user);
        user.getNotes().add(note);

        this.save(user);
        noteService.save(note);

        return "Note with id: " + noteId + " added to user with id: " + userId;
    }

    public String removeNoteFromUser(UUID userId, UUID noteId) {
        User user = this.getUserById(userId);
        Note note = noteService.getNoteById(noteId);

        note.setUser(null);
        user.getNotes().remove(note);

        this.save(user);
        noteService.save(note);

        return "Note with id: " + noteId + " removed from user with id: " + userId;
    }

    public String addGroupToUser(UUID userId, UUID groupId) {
        User user = this.getUserById(userId);
        Group group = groupService.getGroupById(groupId);

        group.setUser(user);
        user.getGroups().add(group);

        this.save(user);
        groupService.save(group);

        return "Group with id: " + groupId + " added to user with id: " + userId;
    }

    public String removeGroupFromUser(UUID userId, UUID groupId) {
        User user = this.getUserById(userId);
        Group group = groupService.getGroupById(groupId);

        group.setUser(null);
        user.getGroups().remove(group);

        this.save(user);
        groupService.save(group);

        return "Group with id: " + groupId + " removed from user with id: " + userId;
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

    @Override
    public String authenticateUser(String email, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

        return jwtTokenUtil.generateAccessToken(userDetailsService.loadUserByUsername(email));

    }
}
