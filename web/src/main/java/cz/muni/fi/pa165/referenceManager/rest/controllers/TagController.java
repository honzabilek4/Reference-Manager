package cz.muni.fi.pa165.referenceManager.rest.controllers;

import cz.muni.fi.pa165.referenceManager.dto.TagDTO;
import cz.muni.fi.pa165.referenceManager.facade.TagFacade;
import cz.muni.fi.pa165.referenceManager.rest.ApiUris;
import cz.muni.fi.pa165.referenceManager.rest.exceptions.ResourceAlreadyExistingException;
import cz.muni.fi.pa165.referenceManager.rest.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.Collection;

/**
 * Documentation for REST is available at:
 * https://github.com/honzabilek4/Reference-Manager/wiki/REST-API
 *
 */
@RestController
@RequestMapping(ApiUris.ROOT_URI_TAGS)
public class TagController {

    private final static Logger logger = LoggerFactory.getLogger(TagController.class);

    @Inject
    private TagFacade tagFacade;

    /**
     * Returns all tags
     *
     * @return list of tags
     */
    @RequestMapping(
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public final Collection<TagDTO> getTags() {
        logger.debug("rest getTags()");
        return tagFacade.findAllTags();
    }

    /**
     * Return one tag with given id
     *
     * @param id identifier for tag
     * @return tag with given id
     */
    @RequestMapping(
        value = "/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public final TagDTO getTag(@PathVariable("id") Long id) {
        logger.debug("rest getTag({})", id);

        try {
            return tagFacade.findById(id);
        } catch (Exception ex) {
            throw new ResourceNotFoundException(ex);
        }
    }


    /**
     * Delete one tag with given id
     *
     * @param id identifier for tag
     * @throws ResourceNotFoundException
     */
    @RequestMapping(
        value = "/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public final void deleteTag(@PathVariable("id") Long id) {
        logger.debug("rest deleteTag({})", id);
        try {
            tagFacade.removeTag(id);
        } catch (IllegalArgumentException ex) {
            logger.error("tag " + id + " not found");
            throw new ResourceNotFoundException("tag " + id + " not found", ex);
        } catch (Throwable ex) {
            logger.error("cannot delete tag " + id + " :" + ex.getMessage());
            throw new ResourceNotFoundException("Unable to delete non existing item", ex);

        }
    }

    /**
     * Create a new tag by POST method
     *
     * @param tag TagCreateDTO with required fields for creation
     * @throws ResourceAlreadyExistingException
     */
    @RequestMapping(
        value = "/create",
        method = RequestMethod.POST,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public final Long createTag(@RequestBody TagDTO tag) {
        logger.debug("rest createTag(name: {})", tag.getName());
        try {
            return tagFacade.createTag(tag);
        } catch (Exception ex) {
            throw new ResourceAlreadyExistingException(ex);
        }
    }

    /**
     * Update the name for one tag by PUT method
     *
     * @param id identifier for tag
     */
    @RequestMapping(
        value = "/{id}",
        method = RequestMethod.PUT,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public final TagDTO updateTag(@PathVariable("id") Long id, @RequestBody TagDTO tag) {
        logger.debug("rest editTag()");

        try {
            tag.setId(id);
            tagFacade.updateTag(tag);
            return tagFacade.findById(id);
        } catch (Exception ex) {
            throw new ResourceNotFoundException(ex);
        }
    }

    /**
     * Share tag for given user by PUT method
     *
     * @param id identifier for tag
     * @param userId identifier for user
     */
    @RequestMapping(
        value = "/{id}/shareForUser/{userId}",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public final TagDTO shareTagForUser(
        @PathVariable("id") Long id,
        @PathVariable("userId") Long userId) {
        logger.debug("rest addTagToReference()");

        try {
            tagFacade.addUser(id, userId);
            return tagFacade.findById(id);
        } catch (Exception ex) {
            throw new ResourceNotFoundException(ex);
        }
    }

    /**
     * Unshare tag for given user by PUT method
     *
     * @param id identifier for tag
     * @param userId identifier for user
     */
    @RequestMapping(
        value = "/{id}/unshareForUser/{userId}",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public final TagDTO removeUserFromTag(
        @PathVariable("id") Long id,
        @PathVariable("userId") Long userId) {
        logger.debug("rest removeTagFromReference()");

        try {
            tagFacade.removeUser(id, userId);
            return tagFacade.findById(id);
        } catch (Exception ex) {
            throw new ResourceNotFoundException(ex);
        }
    }

}
