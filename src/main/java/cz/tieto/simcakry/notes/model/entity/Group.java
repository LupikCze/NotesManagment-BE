package cz.tieto.simcakry.notes.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @OneToMany(mappedBy = "group")
    private List<Note> notes;

    @ManyToMany
    @JoinTable(name = "group_tag",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags;

    public Group(String title, List<Note> notes, Set<Tag> tags) {
        this.title = title;
        this.notes = notes;
        this.tags = tags;
    }
}
