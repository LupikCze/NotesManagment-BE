package cz.tieto.simcakry.notes.controller;

import cz.tieto.simcakry.notes.model.dto.tag.TagDTO;
import cz.tieto.simcakry.notes.model.dto.tag.TagUpdateDTO;
import cz.tieto.simcakry.notes.service.TagService;
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

@AllArgsConstructor
@RestController
@Tag(name = "Tag", description = "Endpoints for tag management")
@RequestMapping("/v1/tags")
public class TagController {
    private final TagService tagService;

    @Operation(
            summary = "Get all tags",
            description = "Get all tags, if no tags are found returns empty list",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of tags",
                            useReturnTypeSchema = true
                    )
            }
    )
    @GetMapping
    public List<TagDTO> getTags() {
        return tagService.getAll();
    }

    @Operation(
            summary = "Get unused tags",
            description = "Get unused tags, if no tags are found returns empty list",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of unused tags",
                            useReturnTypeSchema = true
                    )
            }
    )
    @GetMapping("/unused")
    public List<TagDTO> getUnusedTags() {
        return tagService.getUnused();
    }

    @Operation(
            summary = "Get tag by id",
            description = "Get tag by id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Tag found",
                            useReturnTypeSchema = true
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid id",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Tag not found, tag with given id does not exist",
                            content = @Content
                    )
            }
    )
    @GetMapping("/{id}")
    public TagDTO getUser(@PathVariable UUID id) {
        return tagService.getById(id);
    }

    @Operation(
            summary = "Create tag",
            description = "Create tag from provided JSON",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Tag created",
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
    public TagDTO createTag(@RequestBody @Valid TagDTO newTagDTO) {
        return tagService.create(newTagDTO);
    }

    @Operation(
            summary = "Update tag",
            description = "Update tag by id with provided JSON",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Tag updated",
                            useReturnTypeSchema = true
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request, invalid JSON object",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Tag not found, tag with given id does not exist",
                            content = @Content
                    )
            }
    )
    @PutMapping("/{id}")
    public TagDTO updateTag(@PathVariable UUID id, @RequestBody @Valid TagUpdateDTO tagUpdateDTO) {
        return tagService.update(id, tagUpdateDTO);
    }

    @Operation(
            summary = "Delete tag",
            description = "Delete tag by id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Tag deleted",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request, invalid id",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Tag not found, tag with given id does not exist",
                            content = @Content
                    )
            }
    )
    @DeleteMapping("/{id}")
    public void deleteTag(@PathVariable UUID id) {
        tagService.deleteById(id);
    }
}
