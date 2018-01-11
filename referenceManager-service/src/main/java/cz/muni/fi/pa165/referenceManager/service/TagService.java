package cz.muni.fi.pa165.referenceManager.service;

import cz.muni.fi.pa165.referenceManager.entity.Tag;
import cz.muni.fi.pa165.referenceManager.entity.User;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Interface representing TagService
 *
 * @author Lenka Smitalova
 */
@Service
public interface TagService {

    /**
     * Inserts a tag into database
     * @param tag tag to be inserted
     */
    void create(Tag tag);

    /**
     * Updates existing tag
     */
    void updateTag(Tag tag);

    /**
     * Remove existing tag from database
     * @param tagId id of tag
     */
    void remove(Long tagId);

    /**
     * Finds existing tag in database
     * @param tagId id of tag
     * @return tag with given id if found, null otherwise
     */
    Tag findById(Long tagId);

    /**
     * Find all stored tags in database
     * @return all stored tags in database
     */
    Collection<Tag> findAllTags();

    /**
     * Creates mapping between tag and user
     * @param tag tag to add reference
     * @param user user to be added
     */
    void addUser(Tag tag, User user);

    /**
     * Remove mapping between tag and user
     * @param tag tag having reference
     * @param user user to be removed
     */
    void removeUser(Tag tag, User user);
}
