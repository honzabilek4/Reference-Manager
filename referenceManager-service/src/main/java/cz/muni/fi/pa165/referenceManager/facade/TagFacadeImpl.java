package cz.muni.fi.pa165.referenceManager.facade;

import cz.muni.fi.pa165.referenceManager.dto.TagCreateDTO;
import cz.muni.fi.pa165.referenceManager.dto.TagDTO;
import cz.muni.fi.pa165.referenceManager.entity.Tag;
import cz.muni.fi.pa165.referenceManager.service.MappingService;
import cz.muni.fi.pa165.referenceManager.service.ReferenceService;
import cz.muni.fi.pa165.referenceManager.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

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

    @Autowired
    private MappingService mappingService;

    @Override
    public Long createTag(TagCreateDTO tagCreateDTO) {
        Tag tag = mappingService.mapTo(tagCreateDTO, Tag.class);
        tagService.create(tag);
        return tag.getId();
    }

    @Override
    public Long createTag(TagDTO tagDTO) {
        Tag tag = mappingService.mapTo(tagDTO, Tag.class);
        tagService.create(tag);
        return tag.getId();
    }

    @Override
    public void updateTagName(TagDTO tagDTO, String newName) {
        tagService.updateTagName(tagDTO.getId(), newName);
    }

    @Override
    public void removeTag(Long tagId) {
        tagService.remove(tagId);
    }

    @Override
    public TagDTO findById(Long id) {
        Tag tag = tagService.findById(id);
        return (tag == null) ? null : mappingService.mapTo(tag, TagDTO.class);
    }

    @Override
    public List<TagDTO> findAllTags() {
        return mappingService.mapTo(tagService.findAllTags(), TagDTO.class);
    }

    @Override
    public void addReference(Long tagId, Long referenceId) {
        tagService.addReference(
            tagService.findById(tagId),
            referenceService.findById(referenceId));
    }

    @Override
    public void removeReference(Long tagId, Long referenceId) {
        tagService.removeReference(
            tagService.findById(tagId),
            referenceService.findById(referenceId)
        );
    }

}
