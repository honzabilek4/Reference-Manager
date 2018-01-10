package cz.muni.fi.pa165.referenceManager.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;

public class UserCreateDTO {

    @NotNull
    @Pattern(regexp = ".+@.+\\....?")
    private String email;

    @Size(min = 3, max = 20)
    private String name;

    @NotNull
    private String passwordHash;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String plainPassword) {
        this.passwordHash = plainPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof UserCreateDTO)) return false;
        UserCreateDTO userCreateDTO = (UserCreateDTO) o;
        return Objects.equals(getEmail(), userCreateDTO.getEmail()) &&
            Objects.equals(getName(), userCreateDTO.getName()) &&
            Objects.equals(getPasswordHash(), userCreateDTO.getPasswordHash());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getEmail(), getName(), getPasswordHash());
    }
}
