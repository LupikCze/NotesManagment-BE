package cz.tieto.simcakry.notes.model.dto.note;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NoteCreateDTO {
    @NotBlank
    private String title;

    @NotBlank
    private String content;
}
