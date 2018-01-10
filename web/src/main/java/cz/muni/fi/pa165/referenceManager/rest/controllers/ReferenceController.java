package cz.muni.fi.pa165.referenceManager.rest.controllers;

import cz.muni.fi.pa165.referenceManager.dto.*;
import cz.muni.fi.pa165.referenceManager.entity.Reference;
import cz.muni.fi.pa165.referenceManager.facade.NoteFacade;
import cz.muni.fi.pa165.referenceManager.facade.ReferenceFacade;
import cz.muni.fi.pa165.referenceManager.rest.ApiUris;
import cz.muni.fi.pa165.referenceManager.rest.exceptions.ResourceAlreadyExistingException;
import cz.muni.fi.pa165.referenceManager.rest.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.Collection;

/**
 * @author Andrej Staruch
 */
@RestController
@RequestMapping(ApiUris.ROOT_URI_REFERENCES)
public class ReferenceController {

    private final static Logger logger = LoggerFactory.getLogger(ReferenceController.class);

    @Inject
    private ReferenceFacade referenceFacade;

    /**
     * Returns all references
     * curl -i -X GET http://localhost:8080/pa165/rest/references
     * @return list of references
     */
    @RequestMapping(
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public final Collection<ReferenceDTO> getReferences() {
        logger.debug("rest getReferences()");
        return referenceFacade.getAllReferences();
    }

    /**
     * Return one reference with given id
     * curl -i -X GET http://localhost:8080/pa165/rest/references/{id}
     * @param id identifier for reference
     * @return reference with given id
     */
    @RequestMapping(
        value = "/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public final ReferenceDTO getReference(@PathVariable("id") Long id) {
        logger.debug("rest getReference({})", id);
        try {
            return referenceFacade.getReferenceById(id);
        } catch (Exception ex) {
            throw new ResourceNotFoundException(ex);
        }
    }

    /**
     * Return one reference with given id
     * curl -i -X GET http://localhost:8080/pa165/rest/references/{id}
     * @param id identifier for reference
     * @return reference with given id
     */
    @RequestMapping(
        value = "/{id}/notes",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public final Collection<NoteDTO> getReferenceNotes(@PathVariable("id") Long id) {
        logger.debug("rest getReference({})", id);
        try {
            return referenceFacade.getAllReferenceNotes(id);
        } catch (Exception ex) {
            throw new ResourceNotFoundException(ex);
        }
    }


    /**
     * Delete one reference with given id
     * curl -i -X DELETE http://localhost:8080/pa165/rest/references/{id}
     *
     * @param id identifier for reference
     * @throws ResourceNotFoundException
     */
    @RequestMapping(
        value = "/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public final void deleteReference(@PathVariable("id") Long id) {
        logger.debug("rest deleteReference({})", id);
        try {
            referenceFacade.deleteReference(id);
        } catch (IllegalArgumentException ex) {
            logger.error("reference " + id + " not found");
            throw new ResourceNotFoundException("reference " + id + " not found", ex);
        } catch (Throwable ex) {
            logger.error("cannot delete reference " + id + " :" + ex.getMessage());
            throw new ResourceNotFoundException("Unable to delete non existing item", ex);
        }
    }

    /**
     * Create a new reference by POST method
     * curl -X POST -i -H "Content-Type: application/json" --data '{"title":"TEXT", "authors":
     * ["author1", "author2", "author3"], "venue": "VENUE", "pagesStart": NUM, "pagesEnd": NUM}'
     * http://localhost:8080/pa165/rest/references/create
     *
     * @param reference ReferenceCreateDTO with required fields for creation
     * @throws ResourceAlreadyExistingException
     */
    @RequestMapping(
        value = "/create",
        method = RequestMethod.POST,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public final Long createReference(@RequestBody ReferenceCreateDTO reference) {
        logger.debug("rest createReference()");
        try {
            return referenceFacade.createReference(reference);
        } catch (Exception ex) {
            throw new ResourceAlreadyExistingException(ex);
        }
    }

    /**
     * Update the reference by PUT method
     *
     * curl -X PUT -i -H "Content-Type: application/json" --data '{"title":"TEXT", "authors":
     * ["author1", "author2", "author3"], "venue": "VENUE", "pagesStart": NUM, "pagesEnd": NUM}'
     * http://localhost:8080/pa165/rest/references/{id}
     *
     * @param id identifier for reference
     */
    @RequestMapping(
        value = "/{id}",
        method = RequestMethod.PUT,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public final ReferenceDTO editReference(@PathVariable("id") Long id, @RequestBody ReferenceDTO reference) {
        logger.debug("rest editReference()");

        try {
            reference.setId(id);
            referenceFacade.updateReference(reference);
            return referenceFacade.getReferenceById(id);
        } catch (Exception ex) {
            throw new ResourceNotFoundException(ex);
        }
    }

    /**
     * Adds tag to the reference by PUT method
     *
     * @param id identifier for reference
     */
    @RequestMapping(
        value = "/{id}/addTag",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public final ReferenceDTO addTagToReference(
        @PathVariable("id") Long id,
        @RequestBody TagDTO tagDTO) {
        logger.debug("rest addTagToReference()");

        try {
            referenceFacade.addTag(id, tagDTO);
            return referenceFacade.getReferenceById(id);
        } catch (Exception ex) {
            throw new ResourceNotFoundException(ex);
        }
    }

    /**
     * Removes tag to the reference by PUT method
     *
     * @param id identifier for reference
     */
    @RequestMapping(
        value = "/{id}/removeTag",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public final ReferenceDTO removeTagFromReference(
        @PathVariable("id") Long id,
        @RequestBody TagDTO tagDTO) {
        logger.debug("rest removeTagFromReference()");

        try {
            referenceFacade.removeTag(id, tagDTO);
            return referenceFacade.getReferenceById(id);
        } catch (Exception ex) {
            throw new ResourceNotFoundException(ex);
        }
    }

}
