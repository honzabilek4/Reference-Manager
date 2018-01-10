package cz.muni.fi.pa165.referenceManager.dao;

import cz.muni.fi.pa165.referenceManager.config.PersistenceApplicationContext;
import cz.muni.fi.pa165.referenceManager.entity.Note;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PersistenceApplicationContext.class)
@Transactional
public class NoteDaoTest {

    @Inject
    private NoteDao noteDao;

    @PersistenceContext
    private EntityManager em;

    @Test(expected=IllegalArgumentException.class)
    public void testCreateNullNote() {
        noteDao.create(null);
    }

    @Test
    public void testCreateNoteSuccess() {
        Note note = new Note();
        note.setText("Test note");
        noteDao.create(note);
        Assert.assertNotNull(note.getId());
        Note foundNote = em.find(Note.class,note.getId());
        Assert.assertTrue(note.equals(foundNote));
    }

    @Test
    public void testUpdateNoteSuccess() {
        Note note = new Note();
        note.setText("Original text");
        em.persist(note);
        note.setText("New text");
        noteDao.update(note);
        Note foundNote = em.find(Note.class,note.getId());
        Assert.assertEquals("New text", foundNote.getText());
    }

    @Test
    public void testDeleteNoteSuccess() {
        Note note = new Note();
        note.setText("Test note");
        em.persist(note);
        Note foundNote = em.find(Note.class,note.getId());
        Assert.assertTrue(foundNote.equals(note));
        noteDao.remove(note);
        foundNote = em.find(Note.class,note.getId());
        Assert.assertEquals(null, foundNote);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testDeleteNoteFail(){
        Note note = new Note();
        noteDao.remove(note);
    }

    @Test
    public void testFindAllNotes(){
        Note note = new Note();
        Note note2 = new Note();
        note.setText("First note text.");
        note2.setText("Second note text");
        em.persist(note);
        em.persist(note2);

        HashSet<Note> foundNotes = new HashSet<>(noteDao.findAll());
        HashSet<Note> expectedNotes = new HashSet<>(em.createQuery("select n from Note n", Note.class).getResultList());
        Assert.assertTrue(expectedNotes.equals(foundNotes));
    }

    @Test
    public void testFindNoteById(){
        Note note = new Note();
        note.setText("Note text.");
        em.persist(note);
        Note foundNote = noteDao.findById(note.getId());
        Assert.assertTrue(note.equals(foundNote));
    }
}
