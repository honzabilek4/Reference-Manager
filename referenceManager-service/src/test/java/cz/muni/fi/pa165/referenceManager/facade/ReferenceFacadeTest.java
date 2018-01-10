package cz.muni.fi.pa165.referenceManager.facade;

import cz.muni.fi.pa165.referenceManager.dto.NoteDTO;
import cz.muni.fi.pa165.referenceManager.dto.ReferenceCreateDTO;
import cz.muni.fi.pa165.referenceManager.dto.ReferenceDTO;
import cz.muni.fi.pa165.referenceManager.entity.Note;
import cz.muni.fi.pa165.referenceManager.entity.Reference;
import cz.muni.fi.pa165.referenceManager.service.MappingService;
import cz.muni.fi.pa165.referenceManager.service.ReferenceService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.crypto.spec.RC2ParameterSpec;

public class ReferenceFacadeTest {
    @Mock
    private MappingService mappingService;

    @Mock
    private ReferenceService referenceService;

    @InjectMocks
    private ReferenceFacadeImpl referenceFacade;

    private ReferenceDTO referenceDTO;
    private NoteDTO noteDTO;
    private Reference reference;
    private Note note;
    private ReferenceCreateDTO referenceCreateDTO;

    @Before
    public void beforeTest() {
        MockitoAnnotations.initMocks(this);
        reference = new Reference(9l);
        reference.setTitle("Test reference.");
        referenceDTO = Mockito.mock(ReferenceDTO.class);
        referenceCreateDTO = Mockito.mock(ReferenceCreateDTO.class);
        noteDTO = Mockito.mock(NoteDTO.class);
        note = new Note(8l);

        Mockito.when(mappingService.mapTo(referenceCreateDTO, Reference.class)).thenReturn(reference);
        Mockito.when(mappingService.mapTo(referenceDTO, Reference.class)).thenReturn(reference);
        Mockito.when(referenceDTO.getId()).thenReturn(reference.getId());
        Mockito.when(mappingService.mapTo(noteDTO, Note.class)).thenReturn(note);
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
    public void testAddNote() {
        referenceFacade.addNote(referenceDTO.getId(), noteDTO);
        Mockito.verify(referenceService, Mockito.times(1)).addNote(reference, note);
    }

    @Test
    public void testRemoveNote() {
        referenceFacade.removeNote(referenceDTO.getId(), noteDTO);
        Mockito.verify(referenceService, Mockito.times(1)).removeNote(reference, note);
    }

}
