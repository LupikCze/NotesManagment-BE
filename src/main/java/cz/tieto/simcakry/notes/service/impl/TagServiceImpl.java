package cz.tieto.simcakry.notes.service.impl;

import cz.tieto.simcakry.notes.exception.NotFoundException;
import cz.tieto.simcakry.notes.model.dto.tag.TagDTO;
import cz.tieto.simcakry.notes.model.dto.tag.TagUpdateDTO;
import cz.tieto.simcakry.notes.model.entity.Tag;
import cz.tieto.simcakry.notes.repository.TagRepository;
import cz.tieto.simcakry.notes.service.TagService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;

    private final ModelMapper modelMapper;

    @Override
    public List<TagDTO> getAll() {
        return tagRepository.findAll().stream().map((tag)->modelMapper.map(tag,TagDTO.class)).toList();
    }

    @Override
    public List<TagDTO> getUnused(){
        List<TagDTO> tags = getAll();
        return tags.stream().filter((tag)->tag.getUser()==null).toList();
    }

    @Override
    public TagDTO getById(UUID id) {
        Tag tag = this.getTagById(id);

        return modelMapper.map(tag,TagDTO.class);
    }

    @Override
    public Tag getTagById(UUID id) {
        return tagRepository.findById(id).orElseThrow(()-> new NotFoundException("Tag with id:"+id+" note found"));
    }

    @Override
    public TagDTO update(UUID id, TagUpdateDTO tagUpdateDTO) {
        Tag tag = this.getTagById(id);

        modelMapper.map(tagUpdateDTO,tag);

        return this.save(tag);
    }

    @Override
    public TagDTO create(TagDTO newTagDTO) {
        Tag newTag = modelMapper.map(newTagDTO,Tag.class);

        return this.save(newTag);
    }

    @Override
    public String deleteById(UUID id) {
        Tag tagToDelete = this.getTagById(id);

        this.delete(tagToDelete);

        return "Tag with id:"+id+" deleted";
    }

    @Override
    public void delete(Tag tagToDelete) {
        tagRepository.delete(tagToDelete);
    }

    @Override
    public void deleteAll(Iterable<? extends Tag> tagsToDelete) {
        tagRepository.deleteAll(tagsToDelete);
    }

    @Override
    public TagDTO save(Tag tagToSave) {
        return modelMapper.map(tagRepository.save(tagToSave),TagDTO.class);
    }
}
