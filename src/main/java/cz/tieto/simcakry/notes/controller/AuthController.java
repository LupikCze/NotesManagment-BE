package cz.tieto.simcakry.notes.controller;

import cz.tieto.simcakry.notes.model.dto.Authentication.AuthenticationRequest;
import cz.tieto.simcakry.notes.model.dto.Authentication.AuthenticationResponse;
import cz.tieto.simcakry.notes.model.dto.user.UserCreateDTO;
import cz.tieto.simcakry.notes.model.dto.user.UserDTO;
import cz.tieto.simcakry.notes.model.enums.Role;
import cz.tieto.simcakry.notes.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/users")
@Tag(name = "Authentication", description = "Endpoints for authentication")
public class AuthController {
    private final UserService userService;

    @Operation(
            summary = "Authenticate user",
            description = "Authenticate user by email and password",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User authenticated",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid request content",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Invalid email or password",
                            content = @Content
                    ),
            }
    )
    @PostMapping("/authenticate")
    @ResponseStatus(HttpStatus.OK)
    public AuthenticationResponse authenticateUser(@Valid @RequestBody AuthenticationRequest authenticationRequest) {

        String token = userService.authenticateUser(
                authenticationRequest.getEmail(),
                authenticationRequest.getPassword()
        );

        return new AuthenticationResponse(token);
    }

    @Operation(
            summary = "Create user",
            description = "Create user by provided data",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User created",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid request content",
                            content = @Content
                    ),
            }
    )
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO registerUser(@Valid @RequestBody UserCreateDTO user) {
        user.setRole(Role.USER);
        return userService.create(user);
    }
}
