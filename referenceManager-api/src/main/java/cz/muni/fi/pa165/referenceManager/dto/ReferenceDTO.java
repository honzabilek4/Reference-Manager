package cz.muni.fi.pa165.referenceManager.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Jan Bílek
 */

public class ReferenceDTO {

    private Long id;
    private String title;
    private List<String> authors = new ArrayList<>();
    private Set<NoteDTO> notes;
    private String venue;
    private Integer pagesStart;
    private Integer pagesEnd;
    private List<Long> tagIds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Set<NoteDTO> getNotes() {
        return notes;
    }

    public void setNotes(Set<NoteDTO> notes) {
        this.notes = notes;
    }

    public List<Long> getTagIds() {
        return tagIds;
    }

    public void setTagIds(List<Long> tagIds) {
        this.tagIds = tagIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReferenceDTO)) return false;

        ReferenceDTO that = (ReferenceDTO) o;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getTitle() != null ? !getTitle().equals(that.getTitle()) : that.getTitle() != null) return false;
        if (getAuthors() != null ? !getAuthors().equals(that.getAuthors()) : that.getAuthors() != null) return false;
        if (getNotes() != null ? !getNotes().equals(that.getNotes()) : that.getNotes() != null) return false;
        if (getVenue() != null ? !getVenue().equals(that.getVenue()) : that.getVenue() != null) return false;
        if (getPagesStart() != null ? !getPagesStart().equals(that.getPagesStart()) : that.getPagesStart() != null)
            return false;
        return getPagesEnd() != null ? getPagesEnd().equals(that.getPagesEnd()) : that.getPagesEnd() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getTitle() != null ? getTitle().hashCode() : 0);
        result = 31 * result + (getAuthors() != null ? getAuthors().hashCode() : 0);
        result = 31 * result + (getNotes() != null ? getNotes().hashCode() : 0);
        result = 31 * result + (getVenue() != null ? getVenue().hashCode() : 0);
        result = 31 * result + (getPagesStart() != null ? getPagesStart().hashCode() : 0);
        result = 31 * result + (getPagesEnd() != null ? getPagesEnd().hashCode() : 0);
        return result;
    }
}
