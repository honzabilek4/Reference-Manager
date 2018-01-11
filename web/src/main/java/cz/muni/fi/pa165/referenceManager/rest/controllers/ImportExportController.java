package cz.muni.fi.pa165.referenceManager.rest.controllers;

import cz.muni.fi.pa165.referenceManager.exceptions.ExportException;
import cz.muni.fi.pa165.referenceManager.facade.ImportExportFacade;
import cz.muni.fi.pa165.referenceManager.rest.ApiUris;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@RequestMapping(ApiUris.ROOT_URI_IMPORT_EXPORT)
public class ImportExportController {
    @Inject
    private ImportExportFacade importExportFacade;

    @RequestMapping(
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    public final void getExportForFile() throws ExportException {
        importExportFacade.exportReferencesToCSV(1L);
    }
}
