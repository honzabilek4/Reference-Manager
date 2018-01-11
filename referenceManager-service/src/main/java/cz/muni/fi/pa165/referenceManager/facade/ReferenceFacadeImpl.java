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
import cz.muni.fi.pa165.referenceManager.service.TagService;
import cz.muni.fi.pa165.referenceManager.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Transactional
@Service
public class ReferenceFacadeImpl implements ReferenceFacade {

    @Inject
    private MappingService mappingService;

    @Inject
    private ReferenceService referenceService;

    @Inject
    private UserService userService;

    @Inject
    private TagService tagService;

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
        Set<Tag> tags = new HashSet<>();
        for (Long tagId : referenceDTO.getTagIds()) {
            Tag tag = tagService.findById(tagId);
            if (tag == null) {
                throw new IllegalArgumentException("No tag with id " + tagId + " was found, cannot update the reference to include it");
            }
            tag.addReference(reference);
            tags.add(tag);
        }
        reference.setTags(tags);
        referenceService.updateReference(reference);
    }

    @Override
    public void deleteReference(Long id) {
        referenceService.deleteReference(id);
    }

    @Override
    public List<ReferenceDTO> getAllReferences() {
        List<Reference> references = referenceService.getAllReferences();
        List<ReferenceDTO> referenceDTOS = new ArrayList<>();
        for (Reference reference : references) {
            ReferenceDTO referenceDTO = mappingService.mapTo(reference, ReferenceDTO.class);
            List<Long> tagIds = new ArrayList<>();
            for (Tag tag : reference.getTags()) {
                tagIds.add(tag.getId());
            }
            referenceDTO.setTagIds(tagIds);
            referenceDTOS.add(referenceDTO);
        }
        return referenceDTOS;
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
