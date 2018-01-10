package cz.muni.fi.pa165.referenceManager.dto;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * @author Andrej Staruch
 */
public class NoteCreateDTO {

    @NotNull
    private String text;

    @NotNull
    private Long referenceId;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(Long referenceId) {
        this.referenceId = referenceId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(text);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof  NoteCreateDTO)) return false;
        NoteCreateDTO that = (NoteCreateDTO) obj;
        return Objects.equals(text, that.text);
    }

    @Override
    public String toString() {
        return "Note:{" +
            "text = " + text +
            "referenceId=" + referenceId + "}";
    }
}
