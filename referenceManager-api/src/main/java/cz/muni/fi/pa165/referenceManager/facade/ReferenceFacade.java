package cz.muni.fi.pa165.referenceManager.facade;

import cz.muni.fi.pa165.referenceManager.dto.NoteDTO;
import cz.muni.fi.pa165.referenceManager.dto.ReferenceCreateDTO;
import cz.muni.fi.pa165.referenceManager.dto.ReferenceDTO;
import cz.muni.fi.pa165.referenceManager.dto.TagDTO;

import java.util.List;

/**
 * @author Jan Bílek
 */

public interface ReferenceFacade {

    Long createReference(ReferenceCreateDTO referenceCreateDTO);

    void updateReference(ReferenceDTO r);

    void deleteReference(Long id);

    List<ReferenceDTO> getAllReferences();

    ReferenceDTO getReferenceById(Long id);

    List<NoteDTO> getAllReferenceNotes(Long referenceId);

    void addTag(Long referenceId, TagDTO tagDTO);

    void removeTag(Long referenceId, TagDTO tagDTO);
}
