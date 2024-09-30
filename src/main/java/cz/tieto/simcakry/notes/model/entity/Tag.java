package cz.tieto.simcakry.notes.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false, unique = true)
    private String title;

    @ManyToMany(mappedBy = "tags")
    private List<Note> notes;
    @ManyToMany(mappedBy = "tags")
    private List<Group> groups;

    public Tag(String title, List<Note> notes, List<Group> groups) {
        this.title = title;
        this.notes = notes;
        this.groups = groups;
    }
}
