package cz.muni.fi.pa165.referenceManager.dto;

public class ImportDTO {
    String content;
    TagDTO tag;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public TagDTO getTag() {
        return tag;
    }

    public void setTag(TagDTO tag) {
        this.tag = tag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ImportDTO)) return false;

        ImportDTO importDTO = (ImportDTO) o;

        if (getContent() != null ? !getContent().equals(importDTO.getContent()) : importDTO.getContent() != null)
            return false;
        return getTag() != null ? getTag().equals(importDTO.getTag()) : importDTO.getTag() == null;
    }

    @Override
    public int hashCode() {
        int result = getContent() != null ? getContent().hashCode() : 0;
        result = 31 * result + (getTag() != null ? getTag().hashCode() : 0);
        return result;
    }
}
