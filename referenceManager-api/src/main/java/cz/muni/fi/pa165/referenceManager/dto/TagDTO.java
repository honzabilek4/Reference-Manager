package cz.muni.fi.pa165.referenceManager.dto;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author Lenka Smitalova
 */
public class TagDTO {

    private Long id;

    private String name;

    private Set<Long> referencesIds = new HashSet<>();

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

    public Set<Long> getReferencesIds() {
        return referencesIds;
    }

    public void setReferencesIds(Set<Long> referencesIds) {
        this.referencesIds = referencesIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof TagDTO)) return false;

        TagDTO tag = (TagDTO) o;

        return Objects.equals(name, tag.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
