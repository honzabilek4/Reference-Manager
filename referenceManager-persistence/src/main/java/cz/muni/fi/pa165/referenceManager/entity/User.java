package cz.muni.fi.pa165.referenceManager.entity;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Jan Bílek
 * @since 2017-10-22
 **/

@Entity
@Table(name = "Users_table")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    @Column(unique = true)
    @NotNull
    @Pattern(regexp=".+@.+\\....?")
    private String email;

    @NotNull
    private String name;

    @NotNull
    private String passwordHash;

    @ManyToMany(mappedBy = "users")
    private Set<Tag> tags = new HashSet<>();

    @OneToMany(mappedBy = "owner", cascade = CascadeType.PERSIST)
    private Set<Reference> references = new HashSet<>();

    private Boolean isAdmin = false;

    public User() {}

    public User(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void addReference(Reference reference){
        references.add(reference);
    }

    public void removeReference(Reference reference){
        references.remove(reference);
    }

    public void addTag(Tag tag){
        tags.add(tag);
    }

    public void removeTag(Tag tag){
        tags.remove(tag);
    }

    public Set<Reference> getReferences() {
        return references;
    }

    public void setReferences(Set<Reference> references) {
        this.references = references;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof User)) return false;

        User user = (User) o;

        return email != null ? email.equals(user.getEmail()) : user.getEmail() == null;
    }

    @Override
    public int hashCode() {
        return email != null ? email.hashCode() : 0;
    }
}
