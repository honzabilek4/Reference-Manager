package cz.muni.fi.pa165.referenceManager.service;

import cz.muni.fi.pa165.referenceManager.dao.ReferenceDao;
import cz.muni.fi.pa165.referenceManager.entity.Note;
import cz.muni.fi.pa165.referenceManager.entity.Reference;
import cz.muni.fi.pa165.referenceManager.entity.Tag;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Jan Bílek
 */
@Service
public class ReferenceServiceImpl implements ReferenceService {

    @Inject
    ReferenceDao referenceDao;

    @Override
    public void createReference(Reference r) {
        referenceDao.create(r);
    }

    @Override
    public void updateReference(Reference r) {
        referenceDao.update(r);
    }

    @Override
    public void deleteReference(Long id) {
        Reference reference = referenceDao.findById(id);
        referenceDao.remove(reference);
    }

    @Override
    public List<Reference> getAllReferences() {
        return referenceDao.findAll();
    }

    @Override
    public Reference findById(Long id) {
        return referenceDao.findById(id);
    }

    @Override
    public void addTag(Reference reference, Tag tag) {
        reference.addTag(tag);
    }

    @Override
    public void removeTag(Reference reference, Tag tag) {
        reference.removeTag(tag);
    }
}
