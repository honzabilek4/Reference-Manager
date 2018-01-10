package cz.muni.fi.pa165.referenceManager.facade;

import cz.muni.fi.pa165.referenceManager.dto.NoteCreateDTO;
import cz.muni.fi.pa165.referenceManager.dto.NoteDTO;
import cz.muni.fi.pa165.referenceManager.entity.Note;
import cz.muni.fi.pa165.referenceManager.service.MappingService;
import cz.muni.fi.pa165.referenceManager.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * Implementation of the NoteFacade interface.
 *
 * @author Lenka Smitalova
 */
@Service
@Transactional
public class NoteFacadeImpl implements NoteFacade {

    @Inject
    private NoteService noteService;

    @Autowired
    private MappingService mappingService;

    @Override
    public Long createNote(NoteDTO noteDTO) {
        Note note = mappingService.mapTo(noteDTO, Note.class);
        noteService.create(note);
        return note.getId();
    }

    @Override
    public Long createNote(NoteCreateDTO noteCreateDTO) {
        Note note = mappingService.mapTo(noteCreateDTO, Note.class);
        noteService.create(note);
        return note.getId();
    }

    @Override
    public void changeNoteText(NoteDTO noteDTO, String newText) {
        noteService.changeNoteText(noteDTO.getId(), newText);
    }

    @Override
    public void removeNote(Long noteId) {
        noteService.remove(noteId);
    }

    @Override
    public NoteDTO findById(Long id) {
        Note note = noteService.findById(id);
        return (note == null) ? null : mappingService.mapTo(note, NoteDTO.class);
    }

    @Override
    public List<NoteDTO> findAllNotes() {
        return mappingService.mapTo(noteService.findAllNotes(), NoteDTO.class);
    }
}
