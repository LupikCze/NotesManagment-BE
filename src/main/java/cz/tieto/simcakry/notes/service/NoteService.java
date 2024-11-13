package cz.tieto.simcakry.notes.service;

import cz.tieto.simcakry.notes.model.dto.note.NoteCreateDTO;
import cz.tieto.simcakry.notes.model.dto.note.NoteDTO;
import cz.tieto.simcakry.notes.model.dto.note.NoteUpdateDTO;
import cz.tieto.simcakry.notes.model.entity.Note;

import java.util.List;
import java.util.UUID;

public interface NoteService {
    List<NoteDTO> getAll();
    NoteDTO getById(UUID id);
    Note getNoteById(UUID id);
    NoteDTO update(UUID id, NoteUpdateDTO noteUpdateDTO);
    NoteDTO create(NoteCreateDTO newNoteDTO);
    String deleteById(UUID id);
    String addTagToNote(UUID noteId,UUID tagId);
    String removeTagFromNote(UUID noteId, UUID tagId);
    void delete(Note noteToDelete);
    void deleteAll(Iterable<? extends Note> notesToDelete);
    NoteDTO save(Note noteToSave);
}
