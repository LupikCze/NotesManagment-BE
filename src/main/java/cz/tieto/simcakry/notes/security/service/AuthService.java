package cz.tieto.simcakry.notes.security.service;

import cz.tieto.simcakry.notes.model.dto.user.UserDTO;
import cz.tieto.simcakry.notes.model.dto.user.UserRegisterDTO;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.UUID;

public interface AuthService {
    UserDTO authenticate();
    boolean isAdmin();
    UserRegisterDTO register(String email,String password);
    UUID getUserId();
    JwtAuthenticationToken getJwt();
}
