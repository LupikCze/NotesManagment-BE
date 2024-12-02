package cz.tieto.simcakry.notes.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Tag(name = "Note", description = "Endpoints for note management")
@RequestMapping("/v1/notes")
public class NoteController {
}
