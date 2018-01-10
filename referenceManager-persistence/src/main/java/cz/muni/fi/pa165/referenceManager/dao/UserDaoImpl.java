package cz.muni.fi.pa165.referenceManager.dao;

import cz.muni.fi.pa165.referenceManager.entity.Tag;
import cz.muni.fi.pa165.referenceManager.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Set;

/**
 * @author David Šarman
 */
@Repository
public class UserDaoImpl implements UserDao{
    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(User u) {
        em.persist(u);
    }

    @Override
    public User update(User u) {
        return em.merge(u);
    }

    @Override
    public void remove(User u) {
        User managed = em.find(User.class, u.getId());
        List<Tag> tags = em.createQuery("select t from Tag t where :user member of t.users", Tag.class)
            .setParameter("user", managed).getResultList();
        for (Tag tag : tags) {
            tag.removeUser(managed);
        }
        em.remove(managed);
    }

    @Override
    public User findById(Long id) {
        return em.find(User.class, id);
    }

    @Override
    public User findUserByEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Cannot search for user without email");
        }

        try {
            return em.createQuery("select u from User u where email=:email", User.class)
                .setParameter("email", email)
                .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<User> findAll() {
        return em.createQuery("select u from User u", User.class).getResultList();
    }

}
