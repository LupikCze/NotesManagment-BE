package cz.tieto.simcakry.notes.model.dto.group;

import cz.tieto.simcakry.notes.model.dto.note.NoteDTO;
import cz.tieto.simcakry.notes.model.dto.tag.TagDTO;
import cz.tieto.simcakry.notes.model.dto.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class GroupDTO {
    private UUID id;
    private String title;
    private UserDTO user;
    private List<NoteDTO> notes;
    private List<TagDTO> tags;
}
