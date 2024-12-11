package cz.tieto.simcakry.notes.model.dto.tag;

import cz.tieto.simcakry.notes.model.dto.group.GroupDTO;
import cz.tieto.simcakry.notes.model.dto.note.NoteDTO;
import cz.tieto.simcakry.notes.model.dto.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class TagDTO {
    private UUID id;
    private String title;
    private UserDTO user;
}
