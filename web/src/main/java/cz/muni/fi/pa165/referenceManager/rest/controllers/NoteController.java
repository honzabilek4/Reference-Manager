package cz.muni.fi.pa165.referenceManager.rest.controllers;

import cz.muni.fi.pa165.referenceManager.dto.NoteCreateDTO;
import cz.muni.fi.pa165.referenceManager.dto.NoteDTO;
import cz.muni.fi.pa165.referenceManager.dto.NoteUpdateDTO;
import cz.muni.fi.pa165.referenceManager.facade.NoteFacade;
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
@RequestMapping(ApiUris.ROOT_URI_NOTES)
public class NoteController {

    private final static Logger logger = LoggerFactory.getLogger(NoteController.class);

    @Inject
    private NoteFacade noteFacade;

    /**
     * Returns all notes
     * curl -i -X GET http://localhost:8080/pa165/rest/notes
     * @return list of notes
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final Collection<NoteDTO> getNotes() {
        logger.debug("rest getTags()");
        return noteFacade.findAllNotes();
    }

    /**
     * Return one note with given id
     * curl -i -X GET http://localhost:8080/pa165/rest/notes/{id}
     * @param id identifier for note
     * @return note with given id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final NoteDTO getNote(@PathVariable("id") Long id) throws Exception {
        logger.debug("rest getNote({})", id);
        try {
            return noteFacade.findById(id);
        } catch (Exception ex) {
            throw new ResourceNotFoundException();
        }
    }


    /**
     * Delete one note with given id
     * curl -i -X DELETE http://localhost:8080/pa165/rest/notes/{id}
     *
     * @param id identifier for note
     * @throws ResourceNotFoundException
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final void deleteNote(@PathVariable("id") Long id) throws Exception {
        logger.debug("rest deleteNote({})", id);
        try {
            noteFacade.removeNote(id);
        } catch (IllegalArgumentException ex) {
            logger.error("note " + id + " not found");
            throw new ResourceNotFoundException("note " + id + " not found");
        } catch (Throwable ex) {
            logger.error("cannot delete note " + id + " :" + ex.getMessage());
            throw new ResourceNotFoundException("Unable to delete non existing item");
        }
    }

    /**
     * Create a new note by POST method
     * curl -X POST -i -H "Content-Type: application/json" --data '{"text":"TEXT"}'
     * http://localhost:8080/pa165/rest/notes/create
     *
     * @param note NoteCreateDTO with required fields for creation
     * @throws ResourceAlreadyExistingException
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public final Long createNote(@RequestBody NoteCreateDTO note) throws Exception {
        logger.debug("rest createNote(name: {})", note.getText());
        try {
            return noteFacade.createNote(note);
        } catch (Exception ex) {
            throw new ResourceAlreadyExistingException();
        }
    }

    /**
     * Update the text for one note by PUT method
     * curl -X PUT -i -H "Content-Type: application/json" --data '{"text":"TEXT"}'
     * http://localhost:8080/pa165/rest/notes/{id}
     *
     * @param id identifier for note
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public final NoteDTO editNote(@PathVariable("id") Long id, @RequestBody NoteUpdateDTO note) throws Exception {
        logger.debug("rest editNote()");

        try {
            note.setId(id);
            noteFacade.changeNoteText(note, note.getText());
            return noteFacade.findById(id);
        } catch (Exception ex) {
            throw new ResourceNotFoundException();
        }
    }



}
