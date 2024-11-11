package cz.tieto.simcakry.notes.model.dto.user;

import cz.tieto.simcakry.notes.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String firstname;
    private String lastname;
    private Role role;
}
