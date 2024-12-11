package cz.tieto.simcakry.notes.service.impl;

import cz.tieto.simcakry.notes.exception.NotFoundException;
import cz.tieto.simcakry.notes.model.dto.group.GroupCreateDTO;
import cz.tieto.simcakry.notes.model.dto.group.GroupDTO;
import cz.tieto.simcakry.notes.model.dto.group.GroupUpdateDTO;
import cz.tieto.simcakry.notes.model.dto.note.NoteDTO;
import cz.tieto.simcakry.notes.model.entity.Group;
import cz.tieto.simcakry.notes.model.entity.Note;
import cz.tieto.simcakry.notes.model.entity.Tag;
import cz.tieto.simcakry.notes.repository.GroupRepository;
import cz.tieto.simcakry.notes.service.GroupService;
import cz.tieto.simcakry.notes.service.NoteService;
import cz.tieto.simcakry.notes.service.TagService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;

    private final TagService tagService;
    private final NoteService noteService;

    private final ModelMapper modelMapper;

    @Override
    public List<GroupDTO> getAll() {
        return groupRepository.findAll().stream().map(group -> modelMapper.map(group,GroupDTO.class)).toList();
    }

    @Override
    public GroupDTO getById(UUID id) {
        Group group = this.getGroupById(id);

        return modelMapper.map(group,GroupDTO.class);
    }

    @Override
    public Group getGroupById(UUID id) {
        return groupRepository.findById(id).orElseThrow(()->new NotFoundException("Group with id:"+id+" not found."));
    }

    @Override
    public GroupDTO update(UUID id, GroupUpdateDTO groupUpdateDTO) {
        Group group = this.getGroupById(id);

        modelMapper.map(groupUpdateDTO,group);

        return this.save(group);
    }

    @Override
    public GroupDTO create(GroupCreateDTO newGroupDTO) {
        Group newGroup = modelMapper.map(newGroupDTO,Group.class);

        return this.save(newGroup);
    }

    @Override
    public String deleteById(UUID id) {
        Group group = this.getGroupById(id);

        groupRepository.delete(group);

        return "Group with id:"+id+" deleted";
    }

    @Override
    public String addTagToGroup(UUID groupId, UUID tagId) {
        Group group = this.getGroupById(groupId);
        Tag tag = tagService.getTagById(tagId);

        group.getTags().add(tag);
        tag.getGroups().add(group);

        this.save(group);
        tagService.save(tag);

        return "Tag with id:"+tagId+" added to group with id:"+groupId;
    }

    @Override
    public String removeTagFromGroup(UUID groupId, UUID tagId) {
        Group group = this.getGroupById(groupId);
        Tag tag = tagService.getTagById(tagId);

        group.getTags().remove(tag);
        tag.getGroups().remove(group);

        this.save(group);
        tagService.save(tag);

        return "Tag with id:"+tagId+" removed from group with id:"+groupId;
    }

    @Override
    public String addNoteToGroup(UUID groupId, UUID noteId) {
        Group group = this.getGroupById(groupId);
        Note note = noteService.getNoteById(noteId);

        group.getNotes().add(note);
        note.setGroup(group);

        this.save(group);
        noteService.save(note);

        return "Note with id:"+noteId+" added to group with id:"+groupId;
    }

    @Override
    public String removeNoteFromGroup(UUID groupId, UUID noteId) {
        Group group = this.getGroupById(groupId);
        Note note = noteService.getNoteById(noteId);

        group.getNotes().remove(note);
        note.setGroup(null);

        this.save(group);
        noteService.save(note);

        return "Note with id: "+noteId+" removed from group with id: "+groupId;
    }

    @Override
    public void delete(Group groupToDelete) {
        groupRepository.delete(groupToDelete);
    }

    @Override
    public void deleteAll(Iterable<? extends Group> groupsToDelete) {
        groupRepository.deleteAll(groupsToDelete);
    }

    @Override
    public GroupDTO save(Group groupToSave) {
        return modelMapper.map(groupRepository.save(groupToSave),GroupDTO.class);
    }

    @Override
    public List<GroupDTO> getUnused(){
        List<GroupDTO> groups = getAll();
        return groups.stream().filter((group)->group.getUser()==null ).toList();
    }
}
