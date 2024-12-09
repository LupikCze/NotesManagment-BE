package cz.tieto.simcakry.notes.controller;

import cz.tieto.simcakry.notes.model.dto.Authentication.AuthenticationRequest;
import cz.tieto.simcakry.notes.model.dto.Authentication.AuthenticationResponse;
import cz.tieto.simcakry.notes.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/users")
public class AuthController {
    private final UserService userService;

    @PostMapping("/authenticate")
    @ResponseStatus(HttpStatus.OK)
    public AuthenticationResponse authenticateUser(@Valid @RequestBody AuthenticationRequest authenticationRequest) {

        String token = userService.authenticateUser(
                authenticationRequest.getEmail(),
                authenticationRequest.getPassword()
        );

        return new AuthenticationResponse(token);
    }
}
