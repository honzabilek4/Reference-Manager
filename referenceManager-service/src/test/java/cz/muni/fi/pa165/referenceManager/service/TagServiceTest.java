package cz.muni.fi.pa165.referenceManager.service;

import cz.muni.fi.pa165.referenceManager.dao.ReferenceDao;
import cz.muni.fi.pa165.referenceManager.dao.TagDao;
import cz.muni.fi.pa165.referenceManager.entity.Reference;
import cz.muni.fi.pa165.referenceManager.entity.Tag;
import cz.muni.fi.pa165.referenceManager.config.ServiceConfiguration;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import javax.validation.constraints.Null;
import java.util.*;

/**
 * Set of tests testing the implementation of the TagService
 *
 * @author Lenka Smitalova
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class TagServiceTest {

    @Mock
    private TagDao tagDao;

    @Mock
    private UserService userService;

    @Autowired
    @InjectMocks
    private TagServiceImpl tagService = new TagServiceImpl();

    private Tag tag1;
    private Tag tag2;

    private Reference reference1;
    private Reference reference2;

    @Before
    public void beforeTest() {
        MockitoAnnotations.initMocks(this);

        tag1 = new Tag(1l);
        tag2 = new Tag(2l);

        tag1.setName("This is tag1.");
        tag2.setName("This is tag2.");

        setReferences();

        Mockito.when(tagDao.findById(1l)).thenReturn(tag1);
        Mockito.when(tagDao.findById(2l)).thenReturn(tag2);

        Mockito.when(tagDao.findAll()).thenReturn(Arrays.asList(tag1, tag2));
    }

    private void setReferences(){
        reference1 = new Reference(3l);
        reference2 = new Reference(4l);

        reference1.setTitle("Reference1");
        reference1.setAuthors(Arrays.asList("author1"));

        reference2.setTitle("Reference2");
        reference2.setAuthors(Arrays.asList("author2"));

        Set<Reference> references1 = new HashSet<>();
        references1.add(reference1);
        tag1.setReferences(references1);

        Set<Reference> references = new HashSet<>();
        references.add(reference1);
        references.add(reference2);
        tag2.setReferences(references);
    }

    @Test
    public void testCreate() {
        Mockito.verify(tagDao, Mockito.times(0)).create(tag1);
        tagService.create(tag1);
        Mockito.verify(tagDao, Mockito.times(1)).create(tag1);
    }

    @Test
    public void testFindAll() {
        Collection<Tag> tags = tagService.findAllTags();
        Assert.assertEquals(2, tags.size());
        Assert.assertTrue(tags.contains(tag1));
        Assert.assertTrue(tags.contains(tag2));
    }

    @Test
    public void testFindById() {
        Tag tag = tagService.findById(tag2.getId());
        Assert.assertEquals("Found tag should be the same as expected one.", tag2, tag);
    }

    @Test
    public void testRemove(){
        tagService.remove(tag2.getId());
        Mockito.verify(tagDao, Mockito.times(1)).remove(tag2);
    }

    @Test
    public void testUpdateTagName() {
        String newName = "New tag name";
        tagService.updateTagName(tag1.getId(), newName);

        Assert.assertEquals(tag1.getName(), newName);
    }

    @Test
    public void testAddReference() {
        tagService.addReference(tag1, reference2);

        Mockito.verify(tagDao, Mockito.times(1))
            .update(tag1);

        Assert.assertEquals(
            "Tag should contain 2 references",
            2, tag1.getReferences().size());
        Assert.assertTrue(
            "Tag should contain reference1.",
            tag1.getReferences().contains(reference1));
        Assert.assertTrue(
            "Tag should contain added reference.",
            tag1.getReferences().contains(reference2));
    }

    @Test
    public void testRemoveReference() {
        tagService.removeReference(tag2, reference1);

        Mockito.verify(tagDao, Mockito.times(1))
            .update(tag2);

        Assert.assertEquals(
            "Tag should contain only 1 reference",
            1, tag2.getReferences().size());
        Assert.assertFalse(
            "Tag should not contain removed reference.",
            tag2.getReferences().contains(reference1));
        Assert.assertTrue(
            "Tag should contain reference2.",
            tag2.getReferences().contains(reference2));
    }
}
