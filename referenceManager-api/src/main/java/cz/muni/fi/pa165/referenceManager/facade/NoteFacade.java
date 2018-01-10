package cz.muni.fi.pa165.referenceManager.facade;

import cz.muni.fi.pa165.referenceManager.dto.NoteCreateDTO;
import cz.muni.fi.pa165.referenceManager.dto.NoteDTO;

import java.util.List;

/**
 * Interface representing the NoteFacade.
 *
 * @author Lenka Smitalova
 */
public interface NoteFacade {

    Long createNote(NoteCreateDTO noteCreateDTO);

    void changeNoteText(NoteDTO noteDTO, String newText);

    void removeNote(Long noteId);

    NoteDTO findById(Long id);

    List<NoteDTO> findAllNotes();
}
