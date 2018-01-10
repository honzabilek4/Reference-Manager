package cz.muni.fi.pa165.referenceManager.dao;

import cz.muni.fi.pa165.referenceManager.entity.Reference;
import org.hibernate.Session;
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
    EntityManager em;

    @Override
    public void create(Reference r) {
        em.persist(r);
    }

    @Override
    public Reference update(Reference r) {
        Session session = (Session) em.getDelegate();
        session.update(r);
        return r;
    }

    @Override
    public void remove(Reference r) {
        Reference reference = em.find(Reference.class,r.getId());
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
