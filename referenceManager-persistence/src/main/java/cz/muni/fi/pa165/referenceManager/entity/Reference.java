package cz.muni.fi.pa165.referenceManager.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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

    @Size(min = 1)
    @ElementCollection
    private List<String> authors = new ArrayList<>();

    private String venue;

    private Integer pagesStart;
    private Integer pagesEnd;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "reference")
    private Set<Note> notes = new HashSet<>();

    @ManyToMany(mappedBy = "references")
    private Set<Tag> tags = new HashSet<>();

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE})
    private User owner;

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

    public void addTag(Tag tag){
        tags.add(tag);
    }

    public void removeTag(Tag tag){
        tags.remove(tag);
    }

    public Set<Note> getNotes() {
        return notes;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public User getOwner() {
        return owner;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Reference)) return false;

        Reference reference = (Reference) o;

        return title != null ? title.equals(reference.getTitle()) : reference.getTitle() == null;
    }

    @Override
    public int hashCode() {
        return title != null ? title.hashCode() : 0;
    }
}
