package cz.muni.fi.pa165.referenceManager.dao;

import cz.muni.fi.pa165.referenceManager.entity.Note;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Lenka Šmitalová
 */
@Repository
public class NoteDaoImpl implements NoteDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Note note) {
        em.persist(note);
    }

    @Override
    public Note update(Note note) {
        Session session = (Session) em.getDelegate();
        session.update(note);
        return note;
    }

    @Override
    public void remove(Note note) {
        Note managed = em.find(Note.class, note.getId());
        em.remove(managed);
    }

    @Override
    public Note findById(Long id) {
        return em.find(Note.class, id);
    }

    @Override
    public List<Note> findAll() {
        return em.createQuery("select n from Note n", Note.class).getResultList();
    }
}
