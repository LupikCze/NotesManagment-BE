package cz.tieto.simcakry.notes.model.dto.note;

import cz.tieto.simcakry.notes.model.dto.group.GroupDTO;
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
@Getter
@Setter
public class NoteDTO {
    private UUID id;
    private String title;
    private String content;
    private List<TagDTO> tags;
    private UserDTO user;
}
