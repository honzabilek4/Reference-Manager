package cz.muni.fi.pa165.referenceManager.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Class representing a Tag cz.muni.fi.pa165.referenceManager.entity, used to group multiple References together.
 * @author David Šarman
 */
@Entity
@Table(name = "Tags_table")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TAG_ID")
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String name;

    @ManyToMany()
    @JoinTable(
        name = "Reference_Tag",
        joinColumns = { @JoinColumn(name = "TAG_ID") },
        inverseJoinColumns = { @JoinColumn(name = "REFERENCE_ID") }
    )
    private Set<Reference> references = new HashSet<>();

    public Tag() {}

    public Tag(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Reference> getReferences() {
        return references;
    }

    public void setReferences(Set<Reference> references) {
        this.references = references;
    }

    public void addReference(Reference reference) {
        references.add(reference);
    }

    public void removeReference(Reference reference) {
        references.remove(reference);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Tag)) return false;

        Tag tag = (Tag) o;

        return Objects.equals(name, tag.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
