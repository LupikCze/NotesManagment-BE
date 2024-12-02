package cz.tieto.simcakry.notes.controller;

import cz.tieto.simcakry.notes.service.GroupService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/groups")
@Tag(name = "Group", description = "Endpoints for group management")
public class GroupController {
    private final GroupService groupService;
}
