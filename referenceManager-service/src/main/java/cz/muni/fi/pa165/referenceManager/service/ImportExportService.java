package cz.muni.fi.pa165.referenceManager.service;

import cz.muni.fi.pa165.referenceManager.entity.Tag;
import cz.muni.fi.pa165.referenceManager.entity.User;
import cz.muni.fi.pa165.referenceManager.exceptions.ExportException;
import cz.muni.fi.pa165.referenceManager.exceptions.ImportException;

import java.io.File;

/**
 * Interface for importing/exporting reference from/to a BibTex format
 * to entity Reference.
 * @author David Šarman
 */
public interface ImportExportService {
    String EXPORT_FILENAME_START = "reference_export_tag_";
    /**
     * Import reference from bibtex formatted document.
     *
     * @param user User who will own the imported references.
     * @param file The bibtex file
     * @param tag  Tag with which all of the references will be tagged
     * @throws ImportException In case of missing file, reading or parsing exception
     */
    void importReferences(User user, File file, Tag tag) throws ImportException;

    /**
     * Export references with given tag to a bibtex formatted file
     *
     * @param tag Tag whose references will be exported
     * @return File with the exported references in bibtex format
     * @throws ExportException In case of unsuccessful export
     */
    File exportReferencesToBibtex(Tag tag) throws ExportException;

    /**
     * Export references with given tag to a CSV formatted file
     *
     * @param tag Tag whose references will be exported
     * @return File with the exported references in CSV format
     * @throws ExportException In case of unsuccessful export
     */
    File exportReferencesToCsv(Tag tag) throws ExportException;

    String getReferencesInCSV(Tag tag) throws ExportException;

    String getReferencesInBibtex(Tag tag) throws ExportException;

    void importReferencesFromBibtex( User user, String text, Tag tag) throws ImportException;

}
