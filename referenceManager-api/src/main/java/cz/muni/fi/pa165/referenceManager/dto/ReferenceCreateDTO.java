package cz.muni.fi.pa165.referenceManager.dto;

import java.util.*;

/**
 * @author Andrej Staruch
 */
public class ReferenceCreateDTO {

    private String title;

    private String venue;

    private List<String> authors = new ArrayList<>();

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    private Integer pagesStart;

    private Integer pagesEnd;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReferenceDTO)) return false;

        ReferenceDTO that = (ReferenceDTO) o;

        if (getTitle() != null ? !getTitle().equals(that.getTitle()) : that.getTitle() != null) return false;
        if (getVenue() != null ? !getVenue().equals(that.getVenue()) : that.getVenue() != null) return false;
        if (getPagesStart() != null ? !getPagesStart().equals(that.getPagesStart()) : that.getPagesStart() != null)
            return false;
        return getPagesEnd() != null ? getPagesEnd().equals(that.getPagesEnd()) : that.getPagesEnd() == null;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (getTitle() != null ? getTitle().hashCode() : 0);
        result = 31 * result + (getVenue() != null ? getVenue().hashCode() : 0);
        result = 31 * result + (getPagesStart() != null ? getPagesStart().hashCode() : 0);
        result = 31 * result + (getPagesEnd() != null ? getPagesEnd().hashCode() : 0);
        return result;
    }
}
