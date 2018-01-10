package cz.muni.fi.pa165.referenceManager.facade;

import cz.muni.fi.pa165.referenceManager.dto.TagCreateDTO;
import cz.muni.fi.pa165.referenceManager.dto.TagDTO;
import cz.muni.fi.pa165.referenceManager.entity.Reference;
import cz.muni.fi.pa165.referenceManager.entity.Tag;
import cz.muni.fi.pa165.referenceManager.entity.User;
import cz.muni.fi.pa165.referenceManager.service.MappingService;
import cz.muni.fi.pa165.referenceManager.service.ReferenceService;
import cz.muni.fi.pa165.referenceManager.service.TagService;
import cz.muni.fi.pa165.referenceManager.config.ServiceConfiguration;
import cz.muni.fi.pa165.referenceManager.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;

import java.util.Collections;

/**
 * Set of tests testing the implementation of the TagFacade
 *
 * @author Lenka Smitalova
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class TagFacadeTest {

    @Mock
    private TagService tagService;

    @Mock
    private ReferenceService referenceService;

    @Mock
    private UserService userService;

    @Mock
    private MappingService mappingService;

    @InjectMocks
    private TagFacade tagFacade = new TagFacadeImpl();

    private TagDTO tagDTO;
    private TagCreateDTO tagCreateDTO;
    private Tag tag;
    private User user;

    @Before
    public void beforeTest() {
        MockitoAnnotations.initMocks(this);
        tag = new Tag(1L);
        tag.setName("Testing tag 1.");

        user = Mockito.mock(User.class);
        Mockito.when(user.getId()).thenReturn(2l);
        tag.setUsers(Collections.singleton(user));

        tagDTO = Mockito.mock(TagDTO.class);
        tagCreateDTO = Mockito.mock(TagCreateDTO.class);
        Mockito.when(mappingService.mapTo(tagCreateDTO, Tag.class)).thenReturn(tag);
        Mockito.when(tagDTO.getId()).thenReturn(tag.getId());

        Mockito.when(tagService.findById(tag.getId())).thenReturn(tag);
        Mockito.when(userService.findUserById(user.getId())).thenReturn(user);
    }

    @Test
    public void testCreateTag() {
        tagFacade.createTag(tagCreateDTO);
        Mockito.verify(tagService, Mockito.times(1))
            .create(tag);
    }

    @Test
    public void testUpdateTagName() {
        String newName = "Testing tag changed";
        tagFacade.updateTagName(tagDTO, newName);
        Mockito.verify(tagService, Mockito.times(1))
            .updateTagName(tagDTO.getId(), newName);
    }

    @Test
    public void testRemoveTag() {
        tagFacade.removeTag(tag.getId());
        Mockito.verify(tagService, Mockito.times(1))
            .remove(tag.getId());
    }

    @Test
    public void testFindById() {
        tagFacade.findById(tagDTO.getId());
        Mockito.verify(tagService, Mockito.times(1))
            .findById(tagDTO.getId());
    }

    @Test
    public void testFindAllTags() {
        tagFacade.findAllTags();
        Mockito.verify(tagService, Mockito.times(1))
            .findAllTags();
    }

    @Test
    public void addUser() {
        tagFacade.addUser(tag.getId(), user.getId());
        Mockito.verify(tagService, Mockito.times(1))
            .addUser(tag, user);
        Mockito.verify(tagService, Mockito.times(1))
            .findById(tag.getId());
        Mockito.verify(userService, Mockito.times(1))
            .findUserById(user.getId());
    }

    @Test
    public void removeUser() {
        tagFacade.removeUser(tag.getId(), user.getId());
        Mockito.verify(tagService, Mockito.times(1))
            .removeUser(tag, user);
        Mockito.verify(tagService, Mockito.times(1))
            .findById(tag.getId());
        Mockito.verify(userService, Mockito.times(1))
            .findUserById(user.getId());
    }
}
