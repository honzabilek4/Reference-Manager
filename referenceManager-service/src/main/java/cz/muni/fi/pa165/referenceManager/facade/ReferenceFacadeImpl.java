package cz.muni.fi.pa165.referenceManager.facade;


import cz.muni.fi.pa165.referenceManager.dto.NoteDTO;
import cz.muni.fi.pa165.referenceManager.dto.ReferenceCreateDTO;
import cz.muni.fi.pa165.referenceManager.dto.ReferenceUpdateDTO;
import cz.muni.fi.pa165.referenceManager.facade.ReferenceFacade;
import cz.muni.fi.pa165.referenceManager.dto.ReferenceDTO;
import cz.muni.fi.pa165.referenceManager.service.MappingService;
import cz.muni.fi.pa165.referenceManager.service.ReferenceService;
import cz.muni.fi.pa165.referenceManager.entity.Note;
import cz.muni.fi.pa165.referenceManager.entity.Reference;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class ReferenceFacadeImpl implements ReferenceFacade {

    @Inject
    MappingService mappingService;
    @Inject
    ReferenceService referenceService;


    @Override
    public void createReference(ReferenceDTO referenceDTO) {
        Reference reference = mappingService.mapTo(referenceDTO, Reference.class);
        referenceService.createReference(reference);
    }

    @Override
    public Long createReference(ReferenceCreateDTO referenceCreateDTO) {
        Reference reference = mappingService.mapTo(referenceCreateDTO, Reference.class);
        referenceService.createReference(reference);
        return reference.getId();
    }

    @Override
    public void updateReference(ReferenceDTO referenceDTO) {
        Reference reference = mappingService.mapTo(referenceDTO, Reference.class);
        referenceService.updateReference(reference);
    }

    @Override
    public void updateReference(ReferenceUpdateDTO referenceUpdateDTO) {
        Reference reference = mappingService.mapTo(referenceUpdateDTO, Reference.class);
        referenceService.updateReference(reference);
    }

    @Override
    public void deleteReference(Long id) {
        referenceService.deleteReference(id);
    }

    @Override
    public List<ReferenceDTO> getAllReferences() {
        return mappingService.mapTo(referenceService.getAllReferences(),ReferenceDTO.class);
    }

    @Override
    public ReferenceDTO getReferenceById(Long id) {
        return mappingService.mapTo(referenceService.findById(id),ReferenceDTO.class);
    }

    @Override
    public void addNote(Long referenceId, NoteDTO noteDTO) {
        Reference reference = referenceService.findById(referenceId);
        Note note = mappingService.mapTo(noteDTO, Note.class);
        referenceService.addNote(reference,note);
    }

    @Override
    public void removeNote(Long referenceId, NoteDTO noteDTO) {
        Reference reference = referenceService.findById(referenceId);
        Note note = mappingService.mapTo(noteDTO, Note.class);
        referenceService.removeNote(reference,note);
    }
}
