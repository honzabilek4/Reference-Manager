package cz.muni.fi.pa165.referenceManager.facade;

import cz.muni.fi.pa165.referenceManager.dto.NoteDTO;
import cz.muni.fi.pa165.referenceManager.dto.ReferenceCreateDTO;
import cz.muni.fi.pa165.referenceManager.dto.ReferenceDTO;
import cz.muni.fi.pa165.referenceManager.dto.ReferenceUpdateDTO;

import java.util.List;

/**
 * @author Jan Bílek
 */

public interface ReferenceFacade {
    void createReference(ReferenceDTO r);

    Long createReference(ReferenceCreateDTO referenceCreateDTO);

    void updateReference(ReferenceDTO r);

    void updateReference(ReferenceUpdateDTO referenceUpdateDTO);

    void deleteReference(Long id);

    List<ReferenceDTO> getAllReferences();

    ReferenceDTO getReferenceById(Long id);

    void addNote(Long referenceId, NoteDTO note);

    void removeNote(Long referenceId, NoteDTO note);
}
