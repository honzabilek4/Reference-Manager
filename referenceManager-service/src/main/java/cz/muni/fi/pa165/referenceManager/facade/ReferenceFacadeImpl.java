package cz.muni.fi.pa165.referenceManager.facade;


import cz.muni.fi.pa165.referenceManager.dto.NoteDTO;
import cz.muni.fi.pa165.referenceManager.dto.ReferenceCreateDTO;
import cz.muni.fi.pa165.referenceManager.dto.ReferenceDTO;
import cz.muni.fi.pa165.referenceManager.dto.TagDTO;
import cz.muni.fi.pa165.referenceManager.entity.Tag;
import cz.muni.fi.pa165.referenceManager.entity.User;
import cz.muni.fi.pa165.referenceManager.service.MappingService;
import cz.muni.fi.pa165.referenceManager.service.ReferenceService;
import cz.muni.fi.pa165.referenceManager.entity.Reference;
import cz.muni.fi.pa165.referenceManager.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

@Transactional
@Service
public class ReferenceFacadeImpl implements ReferenceFacade {

    @Inject
    private MappingService mappingService;

    @Inject
    private ReferenceService referenceService;

    @Inject
    private UserService userService;

    @Override
    public Long createReference(ReferenceCreateDTO referenceCreateDTO) {
        Reference reference = mappingService.mapTo(referenceCreateDTO, Reference.class);
        User user = userService.findUserById(referenceCreateDTO.getUserId());
        reference.setOwner(user);
        referenceService.createReference(reference);
        return reference.getId();
    }

    @Override
    public void updateReference(ReferenceDTO referenceDTO) {
        Reference reference = mappingService.mapTo(referenceDTO, Reference.class);
        //reference.setOwner(user);
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
    public void addTag(Long referenceId, TagDTO tagDTO) {
        Reference reference = referenceService.findById(referenceId);
        Tag tag = mappingService.mapTo(tagDTO, Tag.class);
        referenceService.addTag(reference,tag);
    }

    @Override
    public void removeTag(Long referenceId, TagDTO tagDTO) {
        Reference reference = referenceService.findById(referenceId);
        Tag tag = mappingService.mapTo(tagDTO, Tag.class);
        referenceService.removeTag(reference,tag);
    }

    @Override
    public List<NoteDTO> getAllReferenceNotes(Long referenceId) {
        Reference reference = referenceService.findById(referenceId);
        return mappingService.mapTo(reference.getNotes(),NoteDTO.class);
    }
}
