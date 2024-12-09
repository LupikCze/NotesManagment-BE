package cz.tieto.simcakry.notes.model.dto.user;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRegisterDTO {
    String email;
    String password;
}
