package cz.muni.fi.pa165.referenceManager.dao;

import cz.muni.fi.pa165.referenceManager.entity.Reference;
import cz.muni.fi.pa165.referenceManager.entity.Tag;
import cz.muni.fi.pa165.referenceManager.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Jan Bílek
 * @since 2017-10-28
 **/
@Repository
public class ReferenceDaoImpl implements ReferenceDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Reference r) {
        em.persist(r);
    }

    @Override
    public Reference update(Reference r) {
        return em.merge(r);
    }

    @Override
    public void remove(Reference r) {
        Reference reference = em.find(Reference.class, r.getId());
        List<Tag> tags = em.createQuery("select t from Tag t where :reference member of t.references", Tag.class)
        .setParameter("reference", reference).getResultList();
        for (Tag tag : tags) {
            tag.removeReference(reference);
            em.merge(tag);
        }
        em.remove(reference);
    }

    @Override
    public Reference findById(Long id) {
        return em.find(Reference.class, id);
    }

    @Override
    public List<Reference> findAll() {
        return em.createQuery("select r from Reference r", Reference.class).getResultList();
    }
}
