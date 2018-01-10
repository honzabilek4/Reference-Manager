package cz.muni.fi.pa165.referenceManager.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    @NotEmpty
    @ElementCollection
    private List<String> authors = new ArrayList<>();

    private String venue;

    private Integer pagesStart;
    private Integer pagesEnd;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="REFERENCE_ID")
    private Set<Note> notes = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "Reference_Tag",
        inverseJoinColumns = @JoinColumn(name = "TAG_ID", nullable = false),
        joinColumns = @JoinColumn(name = "REFERENCE_ID", nullable = false)
    )
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

    public Set<Tag> getTags() {
        return tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Reference)) return false;

        Reference reference = (Reference) o;

        if (!title.equals(reference.title)) return false;
        if (!authors.equals(reference.authors)) return false;
        if (venue != null ? !venue.equals(reference.venue) : reference.venue != null) return false;
        if (pagesStart != null ? !pagesStart.equals(reference.pagesStart) : reference.pagesStart != null) return false;
        return pagesEnd != null ? pagesEnd.equals(reference.pagesEnd) : reference.pagesEnd == null;
    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + authors.hashCode();
        result = 31 * result + (venue != null ? venue.hashCode() : 0);
        result = 31 * result + (pagesStart != null ? pagesStart.hashCode() : 0);
        result = 31 * result + (pagesEnd != null ? pagesEnd.hashCode() : 0);
        return result;
    }
}
