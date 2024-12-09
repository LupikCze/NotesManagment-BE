package cz.tieto.simcakry.notes.security.service.impl;


import cz.tieto.simcakry.notes.config.PasswordEncoderConfig;
import cz.tieto.simcakry.notes.exception.EmailExistsException;
import cz.tieto.simcakry.notes.model.dto.user.UserDTO;
import cz.tieto.simcakry.notes.model.dto.user.UserRegisterDTO;
import cz.tieto.simcakry.notes.model.enums.Role;
import cz.tieto.simcakry.notes.repository.UserRepository;
import cz.tieto.simcakry.notes.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoderConfig passwordEncoderConfig;

    @Override
    public UserDTO authenticate(){
        Jwt jwt = getJwt().getToken();
        return new UserDTO(UUID.randomUUID(),
                jwt.getClaim("username"),
                jwt.getClaim("email"),
                jwt.getClaim("firstname"),
                jwt.getClaim("lastname"),
                isAdmin() ? Role.ADMIN : Role.USER);
    }

    public UUID getUserId(){
        return authenticate().getId();
    }

    public UserRegisterDTO register(String email,String password){
        if(userRepository.existsByEmail(email)) throw new EmailExistsException("User with this email already exists");
        String passwordHash = passwordEncoderConfig.encoder().encode(password);

        return new UserRegisterDTO(email,passwordHash);
    }

    public JwtAuthenticationToken getJwt(){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        return (JwtAuthenticationToken) authentication;
    }

    @Override
    public boolean isAdmin() {
        Jwt token = getJwt().getToken();
        return token.getClaimAsMap("resource_access").get("ncp-fe").toString().contains(Role.ADMIN.toString());
    }

}
