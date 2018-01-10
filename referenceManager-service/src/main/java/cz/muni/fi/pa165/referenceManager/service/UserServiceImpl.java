package cz.muni.fi.pa165.referenceManager.service;

import cz.muni.fi.pa165.referenceManager.dao.ReferenceDao;
import cz.muni.fi.pa165.referenceManager.dao.TagDao;
import cz.muni.fi.pa165.referenceManager.dao.UserDao;
import cz.muni.fi.pa165.referenceManager.entity.Reference;
import cz.muni.fi.pa165.referenceManager.entity.Tag;
import cz.muni.fi.pa165.referenceManager.entity.User;
import org.jasypt.util.password.PasswordEncryptor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Collection;

/**
 * @author Jan Bílek, Andrej Staruch
 */

@Service
public class UserServiceImpl implements UserService {

    @Inject
    private UserDao userDao;

    @Inject
    private TagDao tagDao;

    @Inject
    private ReferenceDao referenceDao;

    @Inject
    private PasswordEncryptor passwordEncryptor;

    @Override
    public void create(User user) {
        userDao.create(user);
    }

    @Override
    public User findUserById(Long id) {
        return userDao.findById(id);
    }

    @Override
    public User findUserByEmail(String email) {
        return userDao.findUserByEmail(email);
    }

    @Override
    public void registerUser(User user, String plainPassword) {
        user.setPasswordHash(createPasswordHash(plainPassword));
        userDao.create(user);
    }

    @Override
    public Collection<User> getAllUsers() {
        return userDao.findAll();
    }

    @Override
    public boolean authenticate(Long userId, String password) {
        User user = userDao.findById(userId);
        return verifyPassword(user.getPasswordHash(), password);
    }

    @Override
    public void addReference(Long userId, Long referenceId) {
        User user = userDao.findById(userId);
        Reference reference = referenceDao.findById(referenceId);
        user.addReference(reference);
        userDao.update(user);
    }

    @Override
    public void removeReference(Long userId, Long referenceId) {
        User user = userDao.findById(userId);
        Reference reference = referenceDao.findById(referenceId);
        user.removeReference(reference);
        userDao.update(user);
    }

    @Override
    public void addTag(Long userId, Long tagId) {
        User user = userDao.findById(userId);
        Tag tag = tagDao.findById(tagId);
        user.addTag(tag);
        userDao.update(user);
    }

    @Override
    public void removeTag(Long userId, Long tagId) {
        User user = userDao.findById(userId);
        Tag tag = tagDao.findById(tagId);
        cleanUpSharedTags(tag);
        user.removeTag(tag);
        userDao.update(user);
    }

    private void cleanUpSharedTags(Tag tag) {
        for (User user : tag.getUsers()) {
            unshareTag(user.getId(), tag.getId());
        }
    }

    @Override
    public void shareTag(Long userId, Long tagId) {
        User user = userDao.findById(userId);
        Tag tag = tagDao.findById(tagId);
        user.addTag(tag);
        userDao.update(user);
    }

    @Override
    public void unshareTag(Long userId, Long tagId) {
        User user = userDao.findById(userId);
        Tag tag = tagDao.findById(tagId);
        user.removeTag(tag);
        userDao.update(user);
    }

    private boolean verifyPassword(String password, String correctPasswordHash) {
        if (password == null) {
            return false;
        }
        if (correctPasswordHash == null) {
            throw new IllegalArgumentException("Password hash cannot be null.");
        }
        return passwordEncryptor.checkPassword(correctPasswordHash, password);
    }

    private String createPasswordHash(String password) {
        return passwordEncryptor.encryptPassword(password);
    }
}
