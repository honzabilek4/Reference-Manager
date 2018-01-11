package cz.muni.fi.pa165.referenceManager.facade;

import cz.muni.fi.pa165.referenceManager.dto.TagDTO;
import cz.muni.fi.pa165.referenceManager.entity.Reference;
import cz.muni.fi.pa165.referenceManager.entity.Tag;
import cz.muni.fi.pa165.referenceManager.service.MappingService;
import cz.muni.fi.pa165.referenceManager.service.ReferenceService;
import cz.muni.fi.pa165.referenceManager.service.TagService;
import cz.muni.fi.pa165.referenceManager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Implementation of the TagFacade interface.
 *
 * @author Lenka Smitalova
 */
@Service
@Transactional
public class TagFacadeImpl implements TagFacade {

    @Inject
    private TagService tagService;

    @Inject
    private ReferenceService referenceService;

    @Inject
    private UserService userService;

    @Autowired
    private MappingService mappingService;

    @Override
    public Long createTag(TagDTO tagDTO) {
        Tag tag = dtoToTag(tagDTO);
        tagService.create(tag);
        return tag.getId();
    }

    @Override
    public void updateTag(TagDTO tagDTO) {
        Tag tag = dtoToTag(tagDTO);
        for (Reference reference : tag.getReferences()) {
            if (!reference.getTags().contains(tag)) {
                reference.addTag(tag);
            }
        }
        tagService.updateTag(tag);
    }

    @Override
    public void removeTag(Long tagId) {
        tagService.remove(tagId);
    }

    @Override
    public TagDTO findById(Long id) {
        Tag tag = tagService.findById(id);
        if (tag == null) {
            throw new IllegalArgumentException("Tag with id " + id + "could not be found");
        }
        return tagToDTO(tag);
    }

    @Override
    public List<TagDTO> findAllTags() {
        Collection<Tag> tags = tagService.findAllTags();
        List<TagDTO> tagDTOS = new ArrayList<>();
        for (Tag tag : tags) {
            TagDTO tagDTO = tagToDTO(tag);
            tagDTOS.add(tagDTO);
        }
        return tagDTOS;
    }

    private TagDTO tagToDTO(Tag tag) {
        TagDTO tagDTO = mappingService.mapTo(tag, TagDTO.class);
        Set<Long> referenceIds = new HashSet<>();
        for (Reference reference : tag.getReferences()) {
            referenceIds.add(reference.getId());
        }
        tagDTO.setReferencesIds(referenceIds);
        return tagDTO;
    }

    @Override
    public void addUser(Long tagId, Long userId) {
        tagService.addUser(
            tagService.findById(tagId),
            userService.findUserById(userId));
    }

    private Tag dtoToTag(TagDTO tagDTO) {
        Tag tag = mappingService.mapTo(tagDTO, Tag.class);
        Set<Reference> references = new HashSet<>();
        for (Long referenceId : tagDTO.getReferencesIds()) {
            Reference reference = referenceService.findById(referenceId);
            if (reference == null) {
                throw new IllegalArgumentException("No reference with id " + referenceId + " was found");
            }
            references.add(reference);
        }
        tag.setReferences(references);
        return tag;
    }

    @Override
    public void removeUser(Long tagId, Long userId) {
        tagService.removeUser(
            tagService.findById(tagId),
            userService.findUserById(userId)
        );
    }

}
