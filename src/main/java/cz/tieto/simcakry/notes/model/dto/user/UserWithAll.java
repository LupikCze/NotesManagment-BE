package cz.tieto.simcakry.notes.model.dto.user;

import cz.tieto.simcakry.notes.model.dto.group.GroupDTO;
import cz.tieto.simcakry.notes.model.dto.note.NoteDTO;
import cz.tieto.simcakry.notes.model.dto.tag.TagDTO;
import cz.tieto.simcakry.notes.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.collection.spi.PersistentBag;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserWithAll{
    private UUID id;
    private String username;
    private String email;
    private String firstname;
    private String lastname;
    private Role role;
    private List<GroupDTO> groups;
    private List<NoteDTO> notes;
    private List<TagDTO> tags;

}
