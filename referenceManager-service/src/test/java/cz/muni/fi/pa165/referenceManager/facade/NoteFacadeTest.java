package cz.muni.fi.pa165.referenceManager.facade;

import cz.muni.fi.pa165.referenceManager.dto.NoteCreateDTO;
import cz.muni.fi.pa165.referenceManager.dto.NoteDTO;
import cz.muni.fi.pa165.referenceManager.entity.Note;
import cz.muni.fi.pa165.referenceManager.service.MappingService;
import cz.muni.fi.pa165.referenceManager.service.NoteService;
import cz.muni.fi.pa165.referenceManager.config.ServiceConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;

/**
 * Set of tests testing the implementation of the NoteFacade
 *
 * @author Lenka Smitalova
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class NoteFacadeTest {
    @Mock
    private NoteService noteService;

    @Mock
    private MappingService mappingService;

    @InjectMocks
    private NoteFacade noteFacade = new NoteFacadeImpl();

    private NoteCreateDTO noteCreateDTO;
    private NoteDTO noteDTO;
    private Note note;

    @Before
    public void beforeTest() {
        MockitoAnnotations.initMocks(this);
        note = new Note(1l);
        note.setText("Testing note 1.");

        noteDTO = Mockito.mock(NoteDTO.class);
        noteCreateDTO = Mockito.mock(NoteCreateDTO.class);
        Mockito.when(mappingService.mapTo(noteCreateDTO, Note.class)).thenReturn(note);
        Mockito.when(noteDTO.getId()).thenReturn(note.getId());
        Mockito.when(noteDTO.getText()).thenReturn(note.getText());
    }

    @Test
    public void testCreateNote() {
        noteFacade.createNote(noteCreateDTO);
        Mockito.verify(noteService, Mockito.times(1)).create(note);
    }

    @Test
    public void testChangeNoteText() {
        String newText = "Testing note changed.";
        noteFacade.changeNoteText(noteDTO, newText);
        Mockito.verify(noteService, Mockito.times(1))
            .changeNoteText(noteDTO.getId(), newText);
    }

    @Test
    public void testRemoveNote() {
        noteFacade.removeNote(note.getId());
        Mockito.verify(noteService, Mockito.times(1))
            .remove(note.getId());
    }

    @Test
    public void testFindById() {
        noteFacade.findById(noteDTO.getId());
        Mockito.verify(noteService, Mockito.times(1))
            .findById(noteDTO.getId());
    }

    @Test
    public void testFindAllNotes() {
        noteFacade.findAllNotes();
        Mockito.verify(noteService, Mockito.times(1))
            .findAllNotes();
    }
}
