package cz.tieto.simcakry.notes.controller;

import cz.tieto.simcakry.notes.model.dto.user.UserCreateDTO;
import cz.tieto.simcakry.notes.model.dto.user.UserDTO;
import cz.tieto.simcakry.notes.model.dto.user.UserUpdateDTO;
import cz.tieto.simcakry.notes.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/users")
@Tag(name = "User", description = "Endpoints for user management")
public class UserController {
    private final UserService userService;

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
}

