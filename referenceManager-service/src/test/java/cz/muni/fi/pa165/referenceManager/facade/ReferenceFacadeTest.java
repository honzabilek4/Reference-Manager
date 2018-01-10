package cz.muni.fi.pa165.referenceManager.facade;

import cz.muni.fi.pa165.referenceManager.dto.*;
import cz.muni.fi.pa165.referenceManager.entity.Note;
import cz.muni.fi.pa165.referenceManager.entity.Reference;
import cz.muni.fi.pa165.referenceManager.entity.Tag;
import cz.muni.fi.pa165.referenceManager.entity.User;
import cz.muni.fi.pa165.referenceManager.service.MappingService;
import cz.muni.fi.pa165.referenceManager.service.ReferenceService;
import cz.muni.fi.pa165.referenceManager.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class ReferenceFacadeTest {
    @Mock
    private MappingService mappingService;

    @Mock
    private ReferenceService referenceService;

    @Mock
    private UserService userService;

    @InjectMocks
    private ReferenceFacadeImpl referenceFacade;

    private ReferenceDTO referenceDTO;
    private TagDTO tagDTO;
    private Reference reference;
    private Tag tag;
    private UserDTO ownerDTO;
    private User owner;
    private ReferenceCreateDTO referenceCreateDTO;

    @Before
    public void beforeTest() {
        MockitoAnnotations.initMocks(this);
        ownerDTO = Mockito.mock(UserDTO.class);
        owner = new User(11l);
        reference = new Reference(9l);
        reference.setTitle("Test reference.");
        referenceDTO = Mockito.mock(ReferenceDTO.class);
        referenceCreateDTO = Mockito.mock(ReferenceCreateDTO.class);
        tagDTO = Mockito.mock(TagDTO.class);
        tag = new Tag(8l);

        Mockito.when(mappingService.mapTo(referenceCreateDTO, Reference.class)).thenReturn(reference);
        Mockito.when(mappingService.mapTo(referenceDTO, Reference.class)).thenReturn(reference);
        Mockito.when(referenceDTO.getId()).thenReturn(reference.getId());
        Mockito.when(mappingService.mapTo(tagDTO, Tag.class)).thenReturn(tag);
        Mockito.when(referenceService.findById(reference.getId())).thenReturn(reference);
    }

    @Test
    public void testCreateReference() {
        referenceFacade.createReference(referenceCreateDTO);
        Mockito.verify(referenceService, Mockito.times(1)).createReference(reference);
    }

    @Test
    public void testUpdateReference() {
        referenceFacade.updateReference(referenceDTO);
        Mockito.verify(referenceService, Mockito.times(1)).updateReference(reference);
    }

    @Test
    public void testDeleteReference() {
        referenceFacade.deleteReference(reference.getId());
        Mockito.verify(referenceService, Mockito.times(1)).deleteReference(reference.getId());
    }

    @Test
    public void testGetReferenceById() {
        referenceFacade.getReferenceById(referenceDTO.getId());
        Mockito.verify(referenceService, Mockito.times(1)).findById(reference.getId());
    }

    @Test
    public void testGetAllReferences() {
        referenceFacade.getAllReferences();
        Mockito.verify(referenceService, Mockito.times(1)).getAllReferences();
    }

    @Test
    public void testAddTag() {
        referenceFacade.addTag(referenceDTO.getId(), tagDTO);
        Mockito.verify(referenceService, Mockito.times(1)).addTag(reference, tag);
    }

    @Test
    public void testRemoveTag() {
        referenceFacade.removeTag(referenceDTO.getId(), tagDTO);
        Mockito.verify(referenceService, Mockito.times(1)).removeTag(reference, tag);
    }

}
