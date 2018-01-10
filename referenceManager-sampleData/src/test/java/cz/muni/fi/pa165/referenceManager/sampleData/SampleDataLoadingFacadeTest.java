package cz.muni.fi.pa165.referenceManager.sampleData;

import cz.muni.fi.pa165.referenceManager.dao.NoteDao;
import cz.muni.fi.pa165.referenceManager.dao.ReferenceDao;
import cz.muni.fi.pa165.referenceManager.dao.TagDao;
import cz.muni.fi.pa165.referenceManager.dao.UserDao;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ContextConfiguration(classes = {ReferenceManagerSampleDataConfiguration.class})
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class SampleDataLoadingFacadeTest extends AbstractJUnit4SpringContextTests {

    private final static Logger log = LoggerFactory.getLogger(SampleDataLoadingFacadeTest.class);

    @Autowired
    public UserDao userDao;

    @Autowired
    public ReferenceDao referenceDao;

    @Autowired
    public TagDao tagDao;

    @Autowired
    public NoteDao noteDao;

    @Autowired
    public SampleDataLoadingFacade sampleDataLoadingFacade;

    @PersistenceContext
    private EntityManager em;

    @Test
    public void loadSampleDataTest() {
        log.debug("Starting the test.");

        Assert.assertTrue("Should contain sample note data", noteDao.findAll().size() > 0);

        Assert.assertTrue("Should contain sample reference data", referenceDao.findAll().size() > 0);

        Assert.assertTrue("Should contain sample tag data", tagDao.findAll().size() > 0);

        Assert.assertTrue("Should contain sample user data", userDao.findAll().size() > 0);
    }
}
