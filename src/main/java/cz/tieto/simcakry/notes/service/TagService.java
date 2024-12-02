package cz.tieto.simcakry.notes.service;

import cz.tieto.simcakry.notes.model.dto.tag.TagDTO;
import cz.tieto.simcakry.notes.model.dto.tag.TagUpdateDTO;
import cz.tieto.simcakry.notes.model.entity.Tag;

import java.util.List;
import java.util.UUID;

public interface TagService {
    List<TagDTO> getAll();
    TagDTO getById(UUID id);
    Tag getTagById(UUID id);
    TagDTO update(UUID id, TagUpdateDTO tagUpdateDTO);
    TagDTO create(TagDTO newTagDTO);
    String deleteById(UUID id);
    void delete(Tag tagToDelete);
    void deleteAll(Iterable<? extends Tag> tagsToDelete);
    TagDTO save(Tag tagToSave);
}
