package cz.muni.fi.pa165.referenceManager.facade;

import cz.muni.fi.pa165.referenceManager.dto.TagDTO;

import java.util.List;

/**
 * Interface representing the TagFacade.
 *
 * @author Lenka Smitalova
 */
public interface TagFacade {

    Long createTag(TagDTO tagDTO);

    void updateTag(TagDTO tagDTO);

    void removeTag(Long tagId);

    TagDTO findById(Long id);

    List<TagDTO> findAllTags();

    void addUser(Long tagId, Long userId);

    void removeUser(Long tagId, Long userId);

}
