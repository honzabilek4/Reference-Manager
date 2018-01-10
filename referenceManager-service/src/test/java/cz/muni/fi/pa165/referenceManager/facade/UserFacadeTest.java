package cz.muni.fi.pa165.referenceManager.facade;

import cz.muni.fi.pa165.referenceManager.config.ServiceConfiguration;
import cz.muni.fi.pa165.referenceManager.dto.UserCreateDTO;
import cz.muni.fi.pa165.referenceManager.dto.UserDTO;
import cz.muni.fi.pa165.referenceManager.dto.UserLoginDTO;
import cz.muni.fi.pa165.referenceManager.entity.Reference;
import cz.muni.fi.pa165.referenceManager.entity.Tag;
import cz.muni.fi.pa165.referenceManager.entity.User;
import cz.muni.fi.pa165.referenceManager.service.MappingService;
import cz.muni.fi.pa165.referenceManager.service.ReferenceService;
import cz.muni.fi.pa165.referenceManager.service.TagService;
import cz.muni.fi.pa165.referenceManager.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Andrej Staruch
 */

@ContextConfiguration(classes = ServiceConfiguration.class)
public class UserFacadeTest {

    @Mock
    private UserService userService;

    @Mock
    private TagService tagService;

    @Mock
    private ReferenceService referenceService;

    @Mock
    private MappingService mappingService;

    @InjectMocks
    private UserFacade userFacade = new UserFacadeImpl();

    private UserDTO userDTO;
    private UserCreateDTO userCreateDTO;
    private User user;
    private UserLoginDTO userLoginDTO;
    private Reference reference;
    private Tag tag;

    private Collection<UserDTO> getSampleUsers() {
        Collection<UserDTO> out = new ArrayList<>();
        out.add(Mockito.mock(UserDTO.class));
        out.add(Mockito.mock(UserDTO.class));
        return out;
    }

    @Before
    public void beforeTest() {
        MockitoAnnotations.initMocks(this);

        user = Mockito.mock(User.class);
        userDTO = Mockito.mock(UserDTO.class);
        userLoginDTO = Mockito.mock(UserLoginDTO.class);
        userCreateDTO = Mockito.mock(UserCreateDTO.class);
        reference = Mockito.mock(Reference.class);
        tag = Mockito.mock(Tag.class);

        Mockito.when(userService.findUserById(1L)).thenReturn(user);

        Mockito.when(userService.authenticate(Mockito.anyLong(), Mockito.anyString())).
            thenReturn(true);
        Mockito.when(mappingService.mapTo(userCreateDTO, User.class)).thenReturn(user);
        Mockito.when(userFacade.getAllUsers()).thenReturn(getSampleUsers());
        Mockito.when(referenceService.findById(Mockito.anyLong())).thenReturn(reference);
        Mockito.when(tagService.findById(Mockito.anyLong())).thenReturn(tag);
    }

    @Test
    public void testRegisterUser() {
        userFacade.registerUser(userCreateDTO, "password");
        Mockito.verify(userService, Mockito.times(1))
            .registerUser(user, "password");
    }

    @Test
    public void testFindUserById() {
        userFacade.findUserById(user.getId());
        Mockito.verify(userService, Mockito.times(1)).
            findUserById(user.getId());
    }

    @Test
    public void testFindUserByEmail() {
        userFacade.findUserByEmail(user.getEmail());
        Mockito.verify(userService, Mockito.times(1)).
            findUserByEmail(user.getEmail());
    }


    @Test
    public void testGetAllUsers() {
        Collection<UserDTO> users = userFacade.getAllUsers();
        Assert.assertNotNull(users);
        Assert.assertEquals(2, users.size());
        Mockito.verify(userService, Mockito.times(2)).
            getAllUsers();
    }

    @Test
    public void testAuthenticate() {
        Mockito.when(userLoginDTO.getEmail()).thenReturn("user@example.com");
        Mockito.when(userLoginDTO.getPassword()).thenReturn("password");
        Mockito.when(user.getId()).thenReturn(1L);
        Mockito.when(userService.findUserByEmail(Mockito.anyString())).thenReturn(user);
        Mockito.when(userService.authenticate(user.getId(), userLoginDTO.getPassword())).
            thenReturn(true);
        boolean authenticated = userFacade.authenticate(userLoginDTO);
        Assert.assertTrue(authenticated);
        Mockito.verify(userService, Mockito.times(1)).
            authenticate(user.getId(), userLoginDTO.getPassword());
    }

    @Test
    public void testAddReference() {
        userFacade.addReference(user.getId(), reference.getId());
        Mockito.verify(userService, Mockito.times(1)).
            addReference(user.getId(), reference.getId());
    }

    @Test
    public void testRemoveReference() {
        userFacade.removeReference(user.getId(), reference.getId());
        Mockito.verify(userService, Mockito.times(1)).
            removeReference(user.getId(), reference.getId());
    }

    @Test
    public void testAddTag() {
        userFacade.addTag(user.getId(), tag.getId());
        Mockito.verify(userService, Mockito.times(1)).
            addTag(user.getId(), tag.getId());
    }

    @Test
    public void testRemoveTag() {
        userFacade.removeTag(user.getId(), tag.getId());
        Mockito.verify(userService, Mockito.times(1)).
            removeTag(user.getId(), tag.getId());
    }

    @Test
    public void testSahreTag() {
        userFacade.shareTag(user.getId(), tag.getId());
        Mockito.verify(userService, Mockito.times(1)).
            shareTag(user.getId(), tag.getId());
    }

    @Test
    public void testUnshareTag() {
        userFacade.unshareTag(user.getId(), tag.getId());
        Mockito.verify(userService, Mockito.times(1)).
            unshareTag(user.getId(), tag.getId());
    }




}
