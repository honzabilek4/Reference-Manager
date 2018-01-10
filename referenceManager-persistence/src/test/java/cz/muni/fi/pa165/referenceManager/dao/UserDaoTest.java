package cz.muni.fi.pa165.referenceManager.dao;

import cz.muni.fi.pa165.referenceManager.config.PersistenceApplicationContext;
import cz.muni.fi.pa165.referenceManager.entity.Tag;
import cz.muni.fi.pa165.referenceManager.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.validation.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * @author Andrej Staruch
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PersistenceApplicationContext.class)
@Transactional
public class UserDaoTest {

    @Inject
    private UserDao userDao;

    @PersistenceContext
    private EntityManager em;

    private User testUser() {
        User user = new User();
        user.setEmail("user@example.com");
        user.setName("John Doe");
        user.setPasswordHash("password");
        return user;
    }

    @Test(expected = ValidationException.class)
    public void testEmptyUserError() {
        User user = new User();
        userDao.create(user);
    }

    @Test(expected = ValidationException.class)
    public void testEmptyEmailError() {
        User user = new User();
        user.setPasswordHash("password");
        userDao.create(user);
    }

    @Test(expected = ConstraintViolationException.class)
    public void testEmptyPasswordError() {
        User user = new User();
        user.setEmail("user@example.com");
        userDao.create(user);
    }

    @Test
    public void testUserValidationWrongEmail() {
        Validator validator = Validation.buildDefaultValidatorFactory().
            getValidator();

        User user = new User();
        user.setEmail("user#example.com");
        user.setName("user");
        user.setPasswordHash("password");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals("There should be one constraint violation for the email " +
            "field", 1, violations.size());
    }

    @Test
    public void testCreateUser() {
        User user = testUser();
        userDao.create(user);

        assertNotNull("User should have ID after saving", user.getId());
        User found = em.find(User.class, user.getId());
        assertTrue("Saved and found user should be equal", user.equals(found));
    }

    @Test(expected = PersistenceException.class)
    public void testUsersWithSameEmailError() {
        User user1 = testUser();
        userDao.create(user1);
        User user2 = testUser();
        userDao.create(user2);
    }

    @Test
    public void testUpdateUser() {
        User user = testUser();
        em.persist(user);
        em.detach(user);

        user.setEmail("new_email@example.com");
        user.setPasswordHash("new_password");

        userDao.update(user);

        User found = em.find(User.class, user.getId());
        assertTrue("Original and found user should be equal", user.equals(found));
    }

    @Test(expected = ConstraintViolationException.class)
    public void testUpdateEmailToExisting() {
        User user1 = testUser();
        userDao.create(user1);
        User user2 = new User();
        user2.setEmail("user2@example.com");
        user2.setPasswordHash("password");
        em.persist(user2);
        em.detach(user2);
        user2.setEmail(user1.getEmail());
        em.merge(user2);
        List<User> users = em.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    @Test
    public void testRemoveUser() {
        User user = testUser();
        em.persist(user);
        em.detach(user);

        userDao.remove(user);

        List<User> users = em.createQuery("SELECT u FROM User u", User.class).getResultList();

        assertTrue("There should be no persisted users after removal", users.isEmpty());
    }

    @Test
    public void testRemoveUserWithTags() {
        User user = testUser();
        Tag tag = new Tag();
        tag.setName("Test tag");
        em.persist(tag);
        em.persist(user);
        user.addTag(tag);
        tag.addUser(user);
        em.persist(tag);
        em.persist(user);

        em.detach(user);

        userDao.remove(user);

        List<User> users = em.createQuery("SELECT u FROM User u", User.class).getResultList();

        assertTrue("There should be no persisted users after removal", users.isEmpty());
    }


    @Test(expected = IllegalArgumentException.class)
    public void testRemoveNonExistingUser() {
        User user = testUser();
        userDao.remove(user);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveMultipleTimesUser() {
        User user = testUser();
        em.persist(user);
        em.remove(user);
        userDao.remove(user);
    }

    @Test
    public void testFindById() {
        User user = testUser();
        userDao.create(user);

        User found = userDao.findById(user.getId());
        assertTrue("Created and found user by his ID should be the same", user.equals(found));
    }

    @Test
    public void testFindByIdError() {
        User user = testUser();
        userDao.create(user);

        User found = userDao.findById((long) -1);
        assertFalse("Created and found user by non-existing ID shouldn't be the same",
            user.equals(found));
    }

    @Test
    public void testFindByEmail() {
        User user = testUser();
        userDao.create(user);

        User found = userDao.findUserByEmail(user.getEmail());
        assertTrue("Created and found user by his email should be the same", user.equals(found));
    }

    @Test
    public void testFindByEmailError() {
        User user = testUser();
        userDao.create(user);

        User found = userDao.findUserByEmail("user2@example.com");
        assertFalse("Created and found user by non-existing email shouldn't be the same", user.equals(found));

    }

    @Test
    public void testFindAllUsers() {
        User user1 = testUser();
        User user2 = testUser();
        user2.setEmail("user2@example.com");
        user2.setName("John Deer");
        em.persist(user1);
        em.persist(user2);

        Set<User> users = new HashSet<>(userDao.findAll());
        Set<User> expected = new HashSet<>(
            em.createQuery("SELECT u FROM User u", User.class).getResultList());

        assertTrue("Users returned by userDao should match persisted users",
            users.equals(expected));

    }



}
