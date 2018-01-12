package cz.muni.fi.pa165.referenceManager.facade;

import cz.muni.fi.pa165.referenceManager.dto.TagDTO;
import cz.muni.fi.pa165.referenceManager.entity.Tag;
import cz.muni.fi.pa165.referenceManager.entity.User;
import cz.muni.fi.pa165.referenceManager.exceptions.ExportException;
import cz.muni.fi.pa165.referenceManager.exceptions.ImportException;
import cz.muni.fi.pa165.referenceManager.service.ImportExportService;
import cz.muni.fi.pa165.referenceManager.service.TagService;
import cz.muni.fi.pa165.referenceManager.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.io.File;

/**
 * @author David Šarman
 */
@Service
@Transactional
public class ImportExportFacadeImpl implements ImportExportFacade {
    private final static Logger log = LoggerFactory.getLogger(ImportExportFacade.class);

    @Inject
    private ImportExportService importExportService;

    @Inject
    private UserService userService;

    @Inject
    private TagService tagService;

    @Inject
    private TagFacade tagFacade;

    @Override
    public void importReferences(Long userId, File file, TagDTO tagDTO) throws ImportException {
        if (userId == null || file == null || tagDTO == null || tagDTO.getId() == null) {
            throw new IllegalArgumentException("None of the arguments can be null");
        }

        User user = userService.findUserById(userId);
        if (user == null) {
            String errorMsg = "User with id " + userId + " could not be found";
            log.error(errorMsg);
            throw new ImportException(errorMsg);
        }

        Tag tag =  tagService.findById(tagDTO.getId());
        Long tagId = null;
        if (tag == null) {
            tagId = tagFacade.createTag(tagDTO);
            if (tagId == null) {
                String errorMsg = "Tag " + tagDTO + " could not be created";
                log.error(errorMsg);
                throw new ImportException(errorMsg);
            }
            tag = tagService.findById(tagId);
        }
        if (tag == null) {
            String errorMsg = "Tag with id " + tagId + " could not be found";
            log.error(errorMsg);
            throw new ImportException(errorMsg);
        }

        importExportService.importReferences(user, file, tag);
    }

    @Override
    public File exportReferencesToBibtex(Long tagId) throws ExportException {
        Tag tag = findTag(tagId);
        return importExportService.exportReferencesToBibtex(tag);
    }

    @Override
    public File exportReferencesToCSV(Long tagId) throws ExportException {
        Tag tag = findTag(tagId);
        return importExportService.exportReferencesToCsv(tag);
    }

    @Override
    public String getReferencesInCSV(Long tagId) throws ExportException {
        Tag tag = findTag(tagId);
        return importExportService.getReferencesInCSV(tag);
    }

    @Override
    public String getReferencesInBibtex(Long tagId) throws ExportException {
        Tag tag = findTag(tagId);
        return importExportService.getReferencesInBibtex(tag);
    }

    private Tag findTag(Long tagId) throws ExportException {
        Tag tag = tagService.findById(tagId);
        if (tag == null) {
            String errorMsg = "Tag with id " + tagId + " could not be found";
            throw new ExportException(errorMsg);
        }
        return tag;
    }
}
