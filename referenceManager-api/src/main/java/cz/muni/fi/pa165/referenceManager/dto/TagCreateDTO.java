package cz.muni.fi.pa165.referenceManager.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author Andrej Staruch
 *
 * DTO object used for creating tag
 */
public class TagCreateDTO {

    @NotNull
    @Size(min = 3, max = 20)
    private String name;

    private Set<ReferenceDTO> referenceDTOSet = new HashSet<>();

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Set<ReferenceDTO> getReferenceDTOSet() {
        return referenceDTOSet;
    }

    public void setReferenceDTOSet(Set<ReferenceDTO> referenceDTOSet) {
        this.referenceDTOSet = referenceDTOSet;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof  TagCreateDTO)) return false;
        TagCreateDTO that = (TagCreateDTO) obj;
        return Objects.equals(name, that.name);
    }
}
