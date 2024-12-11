package cz.tieto.simcakry.notes.service.impl;

import cz.tieto.simcakry.notes.exception.NotFoundException;
import cz.tieto.simcakry.notes.model.dto.note.NoteCreateDTO;
import cz.tieto.simcakry.notes.model.dto.note.NoteDTO;
import cz.tieto.simcakry.notes.model.dto.note.NoteUpdateDTO;
import cz.tieto.simcakry.notes.model.dto.tag.TagDTO;
import cz.tieto.simcakry.notes.model.entity.Note;
import cz.tieto.simcakry.notes.model.entity.Tag;
import cz.tieto.simcakry.notes.repository.NoteRepository;
import cz.tieto.simcakry.notes.service.NoteService;
import cz.tieto.simcakry.notes.service.TagService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class NoteServiceImpl implements NoteService {
    private final NoteRepository noteRepository;

    private final TagService tagService;

    private final ModelMapper modelMapper;

    @Override
    public List<NoteDTO> getAll() {
        return noteRepository.findAll().stream().map((note)->modelMapper.map(note,NoteDTO.class)).toList();
    }

    @Override
    public NoteDTO getById(UUID id) {
        Note note = getNoteById(id);

        return modelMapper.map(note,NoteDTO.class);
    }

    @Override
    public Note getNoteById(UUID id) {
        return noteRepository.findById(id).orElseThrow(()->new NotFoundException("Note with id:"+id+" note found"));
    }

    @Override
    public NoteDTO update(UUID id, NoteUpdateDTO noteUpdateDTO) {
        Note note = this.getNoteById(id);

        modelMapper.map(noteUpdateDTO,note);

        return this.save(note);
    }

    @Override
    public NoteDTO create(NoteCreateDTO newNoteDTO) {
        Note newNote = modelMapper.map(newNoteDTO,Note.class);

        return this.save(newNote);
    }

    @Override
    public String deleteById(UUID id) {
        Note noteToDelete = this.getNoteById(id);

        this.delete(noteToDelete);

        return "Note with id:"+id+" deleted";
    }

    @Override
    public String addTagToNote(UUID noteId, UUID tagId) {
        Note note = this.getNoteById(noteId);
        Tag tag = tagService.getTagById(tagId);

        note.getTags().add(tag);
        tag.getNotes().add(note);

        this.save(note);
        tagService.save(tag);

        return "Tag with id:"+tagId+" added to note with id:"+noteId;
    }

    @Override
    public String removeTagFromNote(UUID noteId, UUID tagId) {
        Note note = this.getNoteById(noteId);
        Tag tag = tagService.getTagById(tagId);

        note.getTags().remove(tag);
        tag.getNotes().remove(note);

        this.save(note);
        tagService.save(tag);

        return "Tag with id:"+tagId+" removed from note with id:"+noteId;
    }

    @Override
    public void delete(Note noteToDelete) {
        noteRepository.delete(noteToDelete);
    }

    @Override
    public void deleteAll(Iterable<? extends Note> notesToDelete) {
        noteRepository.deleteAll(notesToDelete);
    }

    @Override
    public NoteDTO save(Note noteToSave) {
        return modelMapper.map(noteRepository.save(noteToSave),NoteDTO.class);
    }

    @Override
    public List<NoteDTO> getUnused(){
        List<NoteDTO> notes = getAll();
        return notes.stream().filter((note)->note.getUser()==null ).toList();
    }
}
