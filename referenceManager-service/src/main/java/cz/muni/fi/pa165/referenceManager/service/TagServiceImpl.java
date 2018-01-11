package cz.muni.fi.pa165.referenceManager.service;

import cz.muni.fi.pa165.referenceManager.dao.TagDao;
import cz.muni.fi.pa165.referenceManager.entity.Reference;
import cz.muni.fi.pa165.referenceManager.entity.Tag;
import cz.muni.fi.pa165.referenceManager.entity.User;
import cz.muni.fi.pa165.referenceManager.exceptions.ReferenceManagerServiceException;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Collection;

/**
 * Implementation of TagService interface.
 *
 * @author Lenka Smitalova
 */
@Service
public class TagServiceImpl implements TagService {
    @Inject
    private TagDao tagDao;

    @Inject
    private UserService userService;

    @Override
    public void create(Tag tag) {
        tagDao.create(tag);
    }

    @Override
    public void updateTag(Tag tag) {
        tagDao.update(tag);
    }

    @Override
    public void remove(Long tagId) {
        Tag tag = findById(tagId);
        for (Reference ref : tag.getReferences()) {
            ref.removeTag(tag);
        }
        tagDao.remove(tag);
    }

    @Override
    public Tag findById(Long tagId) {
        return tagDao.findById(tagId);
    }

    @Override
    public Collection<Tag> findAllTags() {
        return tagDao.findAll();
    }

    @Override
    public void addUser(Tag tag, User user) {
        tag.addUser(user);
    }

    @Override
    public void removeUser(Tag tag, User user) {
        tag.removeUser(user);
    }
}
