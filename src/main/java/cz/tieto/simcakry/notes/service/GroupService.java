package cz.tieto.simcakry.notes.service;

import cz.tieto.simcakry.notes.model.dto.group.GroupCreateDTO;
import cz.tieto.simcakry.notes.model.dto.group.GroupDTO;
import cz.tieto.simcakry.notes.model.dto.group.GroupUpdateDTO;
import cz.tieto.simcakry.notes.model.dto.tag.TagDTO;
import cz.tieto.simcakry.notes.model.entity.Group;

import java.util.List;
import java.util.UUID;

public interface GroupService {
    List<GroupDTO> getAll();
    GroupDTO getById(UUID id);
    List<GroupDTO> getUnused();
    Group getGroupById(UUID id);
    GroupDTO update(UUID id, GroupUpdateDTO groupUpdateDTO);
    GroupDTO create(GroupCreateDTO newGroupDTO);
    String deleteById(UUID id);
    String addTagToGroup(UUID groupId, UUID tagId);
    String removeTagFromGroup(UUID groupId, UUID tagId);
    String addNoteToGroup(UUID groupId, UUID noteId);
    String removeNoteFromGroup(UUID groupId, UUID noteId);
    void delete(Group groupToDelete);
    void deleteAll(Iterable<? extends Group> groupsToDelete);
    GroupDTO save(Group groupToSave);
}
