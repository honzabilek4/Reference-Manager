package cz.muni.fi.pa165.referenceManager.rest.controllers;

import cz.muni.fi.pa165.referenceManager.dto.ImportDTO;
import cz.muni.fi.pa165.referenceManager.exceptions.ImportException;
import cz.muni.fi.pa165.referenceManager.facade.ImportExportFacade;
import cz.muni.fi.pa165.referenceManager.rest.ApiUris;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


import javax.inject.Inject;

@RestController
@RequestMapping(ApiUris.ROOT_URI_IMPORT)
public class ImportController {
    @Inject
    private ImportExportFacade importExportFacade;

    @RequestMapping(
        method = RequestMethod.POST
    )
    public final void importReferences( @RequestBody ImportDTO importDTO) throws ImportException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        importExportFacade.importReferencesFromBibtex(userEmail,importDTO.getContent(),importDTO.getTag());
    }

}
