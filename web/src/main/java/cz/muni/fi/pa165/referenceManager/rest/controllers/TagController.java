package cz.muni.fi.pa165.referenceManager.rest.controllers;

import cz.muni.fi.pa165.referenceManager.dto.TagCreateDTO;
import cz.muni.fi.pa165.referenceManager.dto.TagDTO;
import cz.muni.fi.pa165.referenceManager.dto.TagUpdateDTO;
import cz.muni.fi.pa165.referenceManager.facade.TagFacade;
import cz.muni.fi.pa165.referenceManager.rest.ApiUris;
import cz.muni.fi.pa165.referenceManager.rest.exceptions.ResourceAlreadyExistingException;
import cz.muni.fi.pa165.referenceManager.rest.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.Collection;

@RestController
@RequestMapping(ApiUris.ROOT_URI_TAGS)
public class TagController {

    private final static Logger logger = LoggerFactory.getLogger(TagController.class);

    @Inject
    private TagFacade tagFacade;

    /**
     * Returns all tags
     * curl -i -X GET http://localhost:8080/pa165/rest/tags
     * @return list of tags
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final Collection<TagDTO> getTags() {
        logger.debug("rest getTags()");
        return tagFacade.findAllTags();
    }

    /**
     * Return one tag with given id
     * curl -i -X GET http://localhost:8080/pa165/rest/tags/{id}
     * @param id identifier for tag
     * @return tag with given id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final TagDTO getTag(@PathVariable("id") Long id) throws Exception {
        logger.debug("rest getTag({})", id);

        try {
            return tagFacade.findById(id);
        } catch (Exception ex) {
            throw new ResourceNotFoundException(ex);
        }
    }


    /**
     * Delete one tag with given id
     * curl -i -X DELETE http://localhost:8080/pa165/rest/tags/{id}
     *
     * @param id identifier for tag
     * @throws ResourceNotFoundException
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final void deleteTag(@PathVariable("id") Long id) throws Exception {
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
     * curl -X POST -i -H "Content-Type: application/json" --data '{"name":"NAME"}'
     * http://localhost:8080/pa165/rest/tags/create
     *
     * @param tag TagCreateDTO with required fields for creation
     * @throws ResourceAlreadyExistingException
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public final Long createTag(@RequestBody TagCreateDTO tag) throws Exception {
        logger.debug("rest createTag(name: {})", tag.getName());
        try {
            return tagFacade.createTag(tag);
        } catch (Exception ex) {
            throw new ResourceAlreadyExistingException(ex);
        }
    }

    /**
     * Update the name for one tag by PUT method
     * curl -X PUT -i -H "Content-Type: application/json" --data '{"name":"NAME"}'
     * http://localhost:8080/pa165/rest/tags/{id}
     *
     * @param id identifier for tag
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public final TagDTO renameTag(@PathVariable("id") Long id, @RequestBody TagUpdateDTO tag) throws Exception {
        logger.debug("rest editTag()");

        try {
            tag.setId(id);
            tagFacade.updateTagName(tag, tag.getName());
            return tagFacade.findById(id);
        } catch (Exception ex) {
            throw new ResourceNotFoundException(ex);
        }
    }



}
