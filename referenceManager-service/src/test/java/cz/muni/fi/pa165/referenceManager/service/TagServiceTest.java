package cz.muni.fi.pa165.referenceManager.service;

import cz.muni.fi.pa165.referenceManager.dao.TagDao;
import cz.muni.fi.pa165.referenceManager.entity.Reference;
import cz.muni.fi.pa165.referenceManager.entity.Tag;
import cz.muni.fi.pa165.referenceManager.config.ServiceConfiguration;
import cz.muni.fi.pa165.referenceManager.entity.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.*;

/**
 * Set of tests testing the implementation of the TagService
 *
 * @author Lenka Smitalova
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class TagServiceTest {

    @Mock
    private TagDao tagDao;

    @Mock
    private UserService userService;

    @Autowired
    @InjectMocks
    private TagServiceImpl tagService = new TagServiceImpl();

    private Tag tag1;
    private Tag tag2;

    private User user1;
    private User user2;

    @Before
    public void beforeTest() {
        MockitoAnnotations.initMocks(this);

        tag1 = new Tag(1l);
        tag2 = new Tag(2l);

        tag1.setName("This is tag1.");
        tag2.setName("This is tag2.");

        setUsers();

        Mockito.when(tagDao.findById(1l)).thenReturn(tag1);
        Mockito.when(tagDao.findById(2l)).thenReturn(tag2);

        Mockito.when(tagDao.findAll()).thenReturn(Arrays.asList(tag1, tag2));
    }

    private void setUsers(){
        user1 = new User(3l);
        user2 = new User(4l);

        user1.setEmail("user1@mail.cz");
        user1.setPasswordHash("user1password");
        user1.setName("user1");

        user2.setEmail("user2@mail.cz");
        user2.setPasswordHash("user1password");
        user2.setName("user2");

        Set<User> users1 = new HashSet<>();
        users1.add(user1);
        tag1.setUsers(users1);

        Set<User> users = new HashSet<>();
        users.add(user1);
        users.add(user2);
        tag2.setUsers(users);
    }

    @Test
    public void testCreate() {
        Mockito.verify(tagDao, Mockito.times(0)).create(tag1);
        tagService.create(tag1);
        Mockito.verify(tagDao, Mockito.times(1)).create(tag1);
    }

    @Test
    public void testFindAll() {
        Collection<Tag> tags = tagService.findAllTags();
        Assert.assertEquals(2, tags.size());
        Assert.assertTrue(tags.contains(tag1));
        Assert.assertTrue(tags.contains(tag2));
    }

    @Test
    public void testFindById() {
        Tag tag = tagService.findById(tag2.getId());
        Assert.assertEquals("Found tag should be the same as expected one.", tag2, tag);
    }

    @Test
    public void testRemove(){
        tagService.remove(tag2.getId());
        Mockito.verify(tagDao, Mockito.times(1)).remove(tag2);
    }

    @Test
    public void testUpdateTagName() {
        String newName = "New tag name";
        tag1.setName(newName);
        tagService.updateTag(tag1);

        Assert.assertEquals(tag1.getName(), newName);
    }

    @Test
    public void testAddUser() {
        tagService.addUser(tag1, user2);

        Assert.assertEquals(
            "Tag should contain 2 references",
            2, tag1.getUsers().size());
        Assert.assertTrue(
            "Tag should contain user1.",
            tag1.getUsers().contains(user1));
        Assert.assertTrue(
            "Tag should contain added reference.",
            tag1.getUsers().contains(user2));
    }

    @Test
    public void testRemoveUser() {
        tagService.removeUser(tag2, user1);

        Assert.assertEquals(
            "Tag should contain only 1 reference",
            1, tag2.getUsers().size());
        Assert.assertFalse(
            "Tag should not contain removed reference.",
            tag2.getUsers().contains(user1));
        Assert.assertTrue(
            "Tag should contain user2.",
            tag2.getUsers().contains(user2));
    }
}
