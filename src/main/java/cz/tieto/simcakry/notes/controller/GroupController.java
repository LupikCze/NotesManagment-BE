package cz.tieto.simcakry.notes.controller;

import cz.tieto.simcakry.notes.model.dto.group.GroupCreateDTO;
import cz.tieto.simcakry.notes.model.dto.group.GroupDTO;
import cz.tieto.simcakry.notes.model.dto.group.GroupUpdateDTO;
import cz.tieto.simcakry.notes.model.dto.note.NoteDTO;
import cz.tieto.simcakry.notes.model.dto.user.UserCreateDTO;
import cz.tieto.simcakry.notes.model.dto.user.UserDTO;
import cz.tieto.simcakry.notes.model.dto.user.UserUpdateDTO;
import cz.tieto.simcakry.notes.service.GroupService;
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
@RequestMapping("/v1/groups")
@Tag(name = "Group", description = "Endpoints for group management")
public class GroupController {
    private final GroupService groupService;

    @Operation(
            summary = "Get unused groups",
            description = "Get unused groups, if no groups are found returns empty list",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of unused groups",
                            useReturnTypeSchema = true
                    )
            }
    )
    @GetMapping("/unused")
    public List<GroupDTO> getUnusedGroups() {
        return groupService.getUnused();
    }

    @Operation(
            summary = "Get all groups",
            description = "Get all groups, if no groups are found returns empty list",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of groups",
                            useReturnTypeSchema = true
                    )
            }
    )
    @GetMapping
    public List<GroupDTO> getGroups() {
        return groupService.getAll();
    }

    @Operation(
            summary = "Get group by id",
            description = "Get group by id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Group found",
                            useReturnTypeSchema = true
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid id",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Group not found, group with given id does not exist",
                            content = @Content
                    )
            }
    )
    @GetMapping("/{id}")
    public GroupDTO getGroup(@PathVariable UUID id) {
        return groupService.getById(id);
    }

    @Operation(
            summary = "Create group",
            description = "Create group from provided JSON",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Group created",
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
    public GroupDTO createGroup(@RequestBody @Valid GroupCreateDTO newGroupDTO) {
        return groupService.create(newGroupDTO);
    }

    @Operation(
            summary = "Update group",
            description = "Update group by id with provided JSON",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Group updated",
                            useReturnTypeSchema = true
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request, invalid JSON object",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Group not found, group with given id does not exist",
                            content = @Content
                    )
            }
    )
    @PutMapping("/{id}")
    public GroupDTO updateGroup(@PathVariable UUID id, @RequestBody @Valid GroupUpdateDTO groupUpdateDTO) {
        return groupService.update(id, groupUpdateDTO);
    }

    @Operation(
            summary = "Delete group",
            description = "Delete group by id and all his notes and tags",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Group deleted",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request, invalid id",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Group not found, group with given id does not exist",
                            content = @Content
                    )
            }
    )
    @DeleteMapping("/{id}")
    public void deleteGroup(@PathVariable UUID id) {
        groupService.deleteById(id);
    }

    @Operation(
            summary = "Add tag to group",
            description = "Add tag to group by group id and tag id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Tag added to group",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request, invalid id",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Group or tag not found, group with given id or tag with given id does not exist",
                            content = @Content
                    )
            }
    )
    @PostMapping("/{groupId}/tags/{tagId}")
    public void addTagToGroup(@PathVariable UUID groupId, @PathVariable UUID tagId) {
        groupService.addTagToGroup(groupId, tagId);
    }

    @Operation(
            summary = "Remove tag from group",
            description = "Remove tag from group by group id and tag id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Tag removed from group",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request, invalid id",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Group or tag not found, group with given id or tag with given id does not exist",
                            content = @Content
                    )
            }
    )
    @DeleteMapping("/{groupId}/tags/{tagId}")
    public void removeTagFromGroup(@PathVariable UUID groupId, @PathVariable UUID tagId) {
        groupService.removeTagFromGroup(groupId, tagId);
    }

    @Operation(
            summary = "Add note to group",
            description = "Add note to group by group id and note id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Note added to group",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request, invalid id",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Group or note not found, group with given id or note with given id does not exist",
                            content = @Content
                    )
            }
    )
    @PostMapping("/{groupId}/notes/{noteId}")
    public void addNoteToGroup(@PathVariable UUID groupId, @PathVariable UUID noteId) {
        groupService.addNoteToGroup(groupId, noteId);
    }

    @Operation(
            summary = "Remove note from group",
            description = "Remove note from group by group id and note id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Note removed from group",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request, invalid id",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Group or note not found, group with given id or note with given id does not exist",
                            content = @Content
                    )
            }
    )
    @DeleteMapping("/{groupId}/notes/{noteId}")
    public void removeNoteFromGroup(@PathVariable UUID groupId, @PathVariable UUID noteId) {
        groupService.removeNoteFromGroup(groupId, noteId);
    }
}
