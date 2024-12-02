package cz.tieto.simcakry.notes.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@Tag(name = "Tag", description = "Endpoints for tag management")
@RequestMapping("/v1/tags")
public class TagController {
}
