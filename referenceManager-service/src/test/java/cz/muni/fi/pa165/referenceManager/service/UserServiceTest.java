package cz.muni.fi.pa165.referenceManager.service;

import cz.muni.fi.pa165.referenceManager.dao.TagDao;
import cz.muni.fi.pa165.referenceManager.dao.UserDao;
import cz.muni.fi.pa165.referenceManager.entity.Tag;
import cz.muni.fi.pa165.referenceManager.entity.User;
import cz.muni.fi.pa165.referenceManager.config.ServiceConfiguration;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;

import java.util.Arrays;
import java.util.List;

@ContextConfiguration(classes = ServiceConfiguration.class)
public class UserServiceTest {

    @Mock
    private UserDao userDao;

    @Mock
    private TagDao tagDao;

    @InjectMocks
    private UserService userService = new UserServiceImpl();

    private User user1;
    private Tag tag1;

    @Before
    public void beforeTest() {
        MockitoAnnotations.initMocks(this);

        user1 = Mockito.mock(User.class);
        tag1 = Mockito.mock(Tag.class);

        List<User> users = Arrays.asList(user1);
        List<Tag> tags = Arrays.asList(tag1);

        Mockito.when(userDao.findById(1L)).thenReturn(user1);
        Mockito.when(tagDao.findById(1L)).thenReturn(tag1);
        Mockito.when(userDao.findAll()).thenReturn(users);
        Mockito.when(tagDao.findAll()).thenReturn(tags);
    }

    @Test
    public void testCreateUser() {
        userService.create(user1);
        Mockito.verify(userDao, Mockito.times(1)).create(user1);
    }

    @Test
    public void testFindUserById() {
        User result = userService.findUserById(1L);
        Mockito.verify(userDao, Mockito.times(1)).findById(1L);
        Assert.assertEquals(user1, result);
    }

    @Test
    public void testAddTag() {
        userService.addTag(1L, 1L);
        Mockito.verify(user1, Mockito.times(1)).addTag(tag1);
        Mockito.verify(userDao, Mockito.times(1)).update(user1);
    }

    @Test
    public void testRemoveTag() {
        userService.removeTag(1L, 1L);
        Mockito.verify(user1, Mockito.times(1)).removeTag(tag1);
        Mockito.verify(userDao, Mockito.times(1)).update(user1);
    }

    @Test
    public void testShareTag() {
        userService.shareTag(1L, 1L);
        Mockito.verify(user1, Mockito.times(1)).addTag(tag1);
        Mockito.verify(userDao, Mockito.times(1)).update(user1);
    }

    @Test
    public void testUnshareTag() {
        userService.unshareTag(1L, 1L);
        Mockito.verify(user1, Mockito.times(1)).removeTag(tag1);
        Mockito.verify(userDao, Mockito.times(1)).update(user1);
    }

}
