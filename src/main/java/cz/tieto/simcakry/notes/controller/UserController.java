package cz.tieto.simcakry.notes.controller;

import cz.tieto.simcakry.notes.exception.NotFoundException;
import cz.tieto.simcakry.notes.model.dto.user.UserCreateDTO;
import cz.tieto.simcakry.notes.model.dto.user.UserDTO;
import cz.tieto.simcakry.notes.model.dto.user.UserUpdateDTO;
import cz.tieto.simcakry.notes.model.dto.user.UserWithAll;
import cz.tieto.simcakry.notes.model.entity.User;
import cz.tieto.simcakry.notes.security.service.AuthService;
import cz.tieto.simcakry.notes.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/users")
@Tag(name = "User", description = "Endpoints for user management")
public class UserController {
    private final UserService userService;
    private AuthService authService;

    @Operation(
            summary = "Get currently logged in user",
            description = "Get currently logged in user",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Current user",
                            useReturnTypeSchema = true
                    )
            }
    )
    @GetMapping("/current-user")
    public UserDTO getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            return userService.getUserByEmail(email);
        } else {
            throw new NotFoundException("User not found");
        }
    }

    @Operation(
            summary = "Get all users",
            description = "Get all users, if no users are found returns empty list",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of users",
                            useReturnTypeSchema = true
                    )
            }
    )
    @GetMapping
    public List<UserDTO> getUsers() {
        return userService.getAll();
    }

    @Operation(
            summary = "Get user by id",
            description = "Get user by id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User found",
                            useReturnTypeSchema = true
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid id",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User not found, user with given id does not exist",
                            content = @Content
                    )
            }
    )
    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable UUID id) {
        return userService.getById(id);
    }

    @Operation(
            summary = "Get user with notes,tags,groups by id",
            description = "Get user with notes,tags,groups by id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User found",
                            useReturnTypeSchema = true
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid id",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User not found, user with given id does not exist",
                            content = @Content
                    )
            }
    )
    @GetMapping("/{id}/all")
    public UserWithAll getUserWithAll(@PathVariable UUID id) {
        return userService.getUserWithAllById(id);
    }

    @Operation(
            summary = "Create user",
            description = "Create user from provided JSON",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "User created",
                            useReturnTypeSchema = true
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request, invalid JSON object",
                            content = @Content
                    )
            }
    )
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public UserDTO createUser(@RequestBody @Valid UserCreateDTO newUserDTO) {
        return userService.create(newUserDTO);
    }

    @Operation(
            summary = "Update user",
            description = "Update user by id with provided JSON",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User updated",
                            useReturnTypeSchema = true
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request, invalid JSON object",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User not found, user with given id does not exist",
                            content = @Content
                    )
            }
    )
    @PutMapping("/{id}")
    public UserDTO updateUser(@PathVariable UUID id, @RequestBody @Valid UserUpdateDTO userUpdateDTO) {
        return userService.update(id, userUpdateDTO);
    }

    @Operation(
            summary = "Delete user",
            description = "Delete user by id and all his notes,tags and groups",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User deleted",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request, invalid id",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User not found, user with given id does not exist",
                            content = @Content
                    )
            }
    )
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable UUID id) {
        userService.deleteById(id);
    }

    @Operation(
            summary = "Add tag to user",
            description = "Add tag to user by user id and tag id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Tag added to user",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request, invalid id",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User or tag not found, user with given id or tag with given id does not exist",
                            content = @Content
                    )
            }
    )
    @PostMapping("/{userId}/tags/{tagId}")
    public void addTagToUser(@PathVariable UUID userId, @PathVariable UUID tagId) {
        userService.addTagToUser(userId, tagId);
    }

    @Operation(
            summary = "Remove tag from user",
            description = "Remove tag from user by user id and tag id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Tag removed from user",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request, invalid id",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User or tag not found, user with given id or tag with given id does not exist",
                            content = @Content
                    )
            }
    )
    @DeleteMapping("/{userId}/tags/{tagId}")
    public void removeTagFromUser(@PathVariable UUID userId, @PathVariable UUID tagId) {
        userService.removeTagFromUser(userId, tagId);
    }

    @Operation(
            summary = "Add note to user",
            description = "Add note to user by user id and note id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Note added to user",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request, invalid id",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User or note not found, user with given id or note with given id does not exist",
                            content = @Content
                    )
            }
    )
    @PostMapping("/{userId}/notes/{noteId}")
    public void addNoteToUser(@PathVariable UUID userId, @PathVariable UUID noteId) {
        userService.addNoteToUser(userId, noteId);
    }

    @Operation(
            summary = "Remove note from user",
            description = "Remove note from user by user id and note id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Note removed from user",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request, invalid id",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User or note not found, user with given id or note with given id does not exist",
                            content = @Content
                    )
            }
    )
    @DeleteMapping("/{userId}/notes/{noteId}")
    public void removeNoteFromUser(@PathVariable UUID userId, @PathVariable UUID noteId) {
        userService.removeNoteFromUser(userId, noteId);
    }

    @Operation(
            summary = "Add group to user",
            description = "Add group to user by user id and group id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Group added to user",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request, invalid id",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User or group not found, user with given id or group with given id does not exist",
                            content = @Content
                    )
            }
    )
    @PostMapping("/{userId}/groups/{groupId}")
    public void addGroupToUser(@PathVariable UUID userId, @PathVariable UUID groupId) {
        userService.addGroupToUser(userId, groupId);
    }

    @Operation(
            summary = "Remove group from user",
            description = "Remove group from user by user id and group id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Group removed from user",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request, invalid id",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User or group not found, user with given id or group with given id does not exist",
                            content = @Content
                    )
            }
    )
    @DeleteMapping("/{userId}/groups/{groupId}")
    public void removeGroupFromUser(@PathVariable UUID userId, @PathVariable UUID groupId) {
        userService.removeGroupFromUser(userId, groupId);
    }
}

