package cz.muni.fi.pa165.referenceManager.dto;

import java.util.ArrayList;
import java.util.List;

public class ReferenceUpdateDTO extends ReferenceCreateDTO {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
}
