package cz.tieto.simcakry.notes.controller;

import cz.tieto.simcakry.notes.model.dto.note.NoteCreateDTO;
import cz.tieto.simcakry.notes.model.dto.note.NoteDTO;
import cz.tieto.simcakry.notes.model.dto.note.NoteUpdateDTO;
import cz.tieto.simcakry.notes.model.dto.tag.TagDTO;
import cz.tieto.simcakry.notes.service.NoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@Tag(name = "Note", description = "Endpoints for note management")
@RequestMapping("/v1/notes")
public class NoteController {
    private final NoteService noteService;

    @Operation(
            summary = "Get all notes",
            description = "Get all notes, if no notes are found returns empty list",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of notes",
                            useReturnTypeSchema = true
                    )
            }
    )
    @GetMapping
    public List<NoteDTO> getNotes() {
        return noteService.getAll();
    }

    @Operation(
            summary = "Get note by id",
            description = "Get note by id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Note found",
                            useReturnTypeSchema = true
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid id",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Note not found, note with given id does not exist",
                            content = @Content
                    )
            }
    )
    @GetMapping("/{id}")
    public NoteDTO getNote(@PathVariable UUID id) {
        return noteService.getById(id);
    }

    @Operation(
            summary = "Create note",
            description = "Create note from provided JSON",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Note created",
                            useReturnTypeSchema = true
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request, invalid JSON object",
                            content = @Content
                    )
            }
    )
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public NoteDTO createNote(@RequestBody @Valid NoteCreateDTO newNoteDTO) {
        return noteService.create(newNoteDTO);
    }

    @Operation(
            summary = "Get unused notes",
            description = "Get unused notes, if no notes are found returns empty list",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of unused notes",
                            useReturnTypeSchema = true
                    )
            }
    )
    @GetMapping("/unused")
    public List<NoteDTO> getUnusedNotes() {
        return noteService.getUnused();
    }

    @Operation(
            summary = "Update note",
            description = "Update note by id with provided JSON",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Note updated",
                            useReturnTypeSchema = true
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request, invalid JSON object",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Note not found, user with given id does not exist",
                            content = @Content
                    )
            }
    )
    @PutMapping("/{id}")
    public NoteDTO updateNote(@PathVariable UUID id, @RequestBody @Valid NoteUpdateDTO noteUpdateDTO) {
        return noteService.update(id, noteUpdateDTO);
    }

    @Operation(
            summary = "Delete note",
            description = "Delete note by id and all his tags",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Note deleted",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request, invalid id",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Note not found, note with given id does not exist",
                            content = @Content
                    )
            }
    )
    @DeleteMapping("/{id}")
    public void deleteNote(@PathVariable UUID id) {
        noteService.deleteById(id);
    }

    @Operation(
            summary = "Add tag to note",
            description = "Add tag to note by note id and tag id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Tag added to note",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request, invalid id",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Note or tag not found, note with given id or tag with given id does not exist",
                            content = @Content
                    )
            }
    )
    @PostMapping("/{noteId}/tags/{tagId}")
    public void addTagToNote(@PathVariable UUID noteId, @PathVariable UUID tagId) {
        noteService.addTagToNote(noteId, tagId);
    }

    @Operation(
            summary = "Remove tag from note",
            description = "Remove tag from note by note id and tag id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Tag removed from note",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request, invalid id",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Note or tag not found, note with given id or tag with given id does not exist",
                            content = @Content
                    )
            }
    )
    @DeleteMapping("/{noteId}/tags/{tagId}")
    public void removeTagFromNote(@PathVariable UUID noteId, @PathVariable UUID tagId) {
        noteService.removeTagFromNote(noteId, tagId);
    }
}
