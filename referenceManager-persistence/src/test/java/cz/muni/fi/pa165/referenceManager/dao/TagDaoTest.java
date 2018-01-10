package cz.muni.fi.pa165.referenceManager.dao;

import cz.muni.fi.pa165.referenceManager.config.PersistenceApplicationContext;
import cz.muni.fi.pa165.referenceManager.entity.Reference;
import cz.muni.fi.pa165.referenceManager.entity.Tag;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Lenka Šmitalová
 */
@ContextConfiguration(classes = PersistenceApplicationContext.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class TagDaoTest {

    @Inject
    private TagDao tagDao;

    @Inject
    private ReferenceDao refDao;

    @PersistenceContext
    private EntityManager em;

    private Tag t1;
    private Tag t2;

    private Reference r1;
    private Reference r2;

    @Before
    public void createTags() {
        r1 = new Reference();
        r1.setTitle("Reference 1");
        r1.setPagesStart(21);
        r1.setPagesEnd(30);
        r1.setAuthors(Arrays.asList("author1", "author2"));
        r1.setVenue("Reference 1 venue");

        r2 = new Reference();
        r2.setTitle("Reference 2");
        r2.setAuthors(Collections.singletonList("author3"));

        refDao.create(r1);
        refDao.create(r2);

        t1 = new Tag();
        t1.setName("Test tag 1");
        Set<Reference> refs = new HashSet<>();
        refs.add(r1);
        t1.setReferences(refs);

        t2 = new Tag();
        t2.setName("Test tag 2");
        t2.setReferences(refs);

        tagDao.create(t1);
        tagDao.create(t2);
    }

    @Test
    public void testCreateTag(){
        Assert.assertEquals("2 tags should be persisted",
            2, getAllPersistedTags().size());
        Tag t = new Tag();
        t.setName("Test tag");
        tagDao.create(t);
        Assert.assertNotNull("Tag id should be set", t.getId());
        Assert.assertEquals("Found tag should equal the saved one.",
            t, em.find(Tag.class, t.getId()));
    }

    @Test(expected = ConstraintViolationException.class)
    public void testFailCreateTagWithNullName() {
        Tag tag = new Tag();
        tagDao.create(tag);
    }

    @Test
    public void testUpdateTag() {
        Set<Reference> refs = new HashSet<>();
        refs.add(r1);
        refs.add(r2);
        t2.setReferences(refs);

        tagDao.update(t2);

        Assert.assertEquals("Tag contains two references",
            2,
            em.createQuery("select t.references from Tag t where t.id=:t2_id")
                .setParameter("t2_id", t2.getId()).getResultList().size());
    }

    @Test
    public void testRemoveTag() {
        tagDao.remove(t1);
        Assert.assertEquals("Tags table should contain only one row",
            1,
            getAllPersistedTags().size());
        Assert.assertNull("Correct tag was removed",
            em.find(Tag.class, t1.getId()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemovingAlreadyRemovedTag() {
        em.remove(t1);
        tagDao.remove(t1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveNonPersistedTag() {
        Tag t = new Tag();
        t.setName("New test tag");
        tagDao.remove(t);
    }

    @Test
    public void testFindTagById() {
        Assert.assertEquals("Found and saved tag should be equal",
            t2, em.find(Tag.class, t2.getId()));
    }

    @Test
    public void testFindTagByNonExistingId() {
        Assert.assertNull("FindById by non-existing id should return null",
            tagDao.findById(9031L));
    }

    @Test
    public void testFindAllTags() {
        Assert.assertEquals("Tags returned by TagDao should match the persisted ones",
            getAllPersistedTags(),
            tagDao.findAll());
    }

    private List<Tag> getAllPersistedTags() {
        return em.createQuery("select t from Tag t", Tag.class).getResultList();
    }
 }
