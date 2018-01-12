package cz.muni.fi.pa165.referenceManager.rest.controllers;

import cz.muni.fi.pa165.referenceManager.exceptions.ExportException;
import cz.muni.fi.pa165.referenceManager.facade.ImportExportFacade;
import cz.muni.fi.pa165.referenceManager.rest.ApiUris;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import javax.inject.Inject;

@RestController
@RequestMapping(ApiUris.ROOT_URI_EXPORT)
public class ExportController {
    @Inject
    private ImportExportFacade importExportFacade;

    @RequestMapping(
        value = "/csv/{id}",
        method = RequestMethod.GET
    )
    public final String getCSVExport(@PathVariable("id") Long id) throws ExportException {
        return importExportFacade.getReferencesInCSV(id);
    }

    @RequestMapping(
        value = "/bibtex/{id}",
        method = RequestMethod.GET
    )
    public final String getBibtexExport(@PathVariable("id") Long id) throws ExportException {
        return importExportFacade.getReferencesInBibtex(id);
    }
}
