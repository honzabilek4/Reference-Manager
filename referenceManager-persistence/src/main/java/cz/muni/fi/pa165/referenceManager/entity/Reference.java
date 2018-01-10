package cz.muni.fi.pa165.referenceManager.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;


/**
 * @author Lenka Šmitalová
 */
@Entity
@Table(name = "References_table")
public class Reference {

    @Id
    @Column(name = "REFERENCE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String title;

    @NotNull
    @ElementCollection
    private List<String> authors = new ArrayList<>();

    private String venue;

    private Integer pagesStart;
    private Integer pagesEnd;

    @OneToMany
    private Set<Note> notes = new HashSet<>();

    @ManyToOne
    private User owner;

    @ManyToMany(mappedBy = "references")
    private Set<Tag> tags = new HashSet<>();

    public Reference() {}

    public Reference (Long id){
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public Integer getPagesStart() {
        return pagesStart;
    }

    public void setPagesStart(Integer pagesStart) {
        this.pagesStart = pagesStart;
    }

    public Integer getPagesEnd() {
        return pagesEnd;
    }

    public void setPagesEnd(Integer pagesEnd) {
        this.pagesEnd = pagesEnd;
    }

    public void addNote(Note note){
        notes.add(note);
    }

    public void removeNote(Note note){
        notes.remove(note);
    }

    public Set<Note> getNotes() {
        return notes;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public User getOwner() {
        return owner;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public boolean addTag(Tag t) {
        return tags.add(t);
    }

    public boolean removeTag(Tag t) {
        return tags.remove(t);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Reference reference = (Reference) o;

        return title != null ? title.equals(reference.title) : reference.title == null;
    }

    @Override
    public int hashCode() {
        return title != null ? title.hashCode() : 0;
    }
}
