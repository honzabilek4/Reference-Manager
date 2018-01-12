package cz.muni.fi.pa165.referenceManager.facade;

import cz.muni.fi.pa165.referenceManager.dto.TagDTO;
import cz.muni.fi.pa165.referenceManager.exceptions.ExportException;
import cz.muni.fi.pa165.referenceManager.exceptions.ImportException;

import java.io.File;

/**
 * @author David Šarman
 */
public interface ImportExportFacade {
    void importReferences(Long userId, File file, TagDTO tagDTO) throws ImportException;
    File exportReferencesToBibtex(Long tagId) throws ExportException;
    File exportReferencesToCSV(Long tagId) throws ExportException;
    String getReferencesInCSV(Long tagId) throws ExportException;
    String getReferencesInBibtex(Long tagId) throws ExportException;
}
