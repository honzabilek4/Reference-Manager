package cz.muni.fi.pa165.referenceManager.service;

import cz.muni.fi.pa165.referenceManager.entity.Reference;
import cz.muni.fi.pa165.referenceManager.entity.Tag;
import cz.muni.fi.pa165.referenceManager.entity.User;
import cz.muni.fi.pa165.referenceManager.exceptions.ExportException;
import cz.muni.fi.pa165.referenceManager.exceptions.ImportException;
import cz.muni.fi.pa165.referenceManager.utils.CSVWriter;
import org.jbibtex.BibTeXDatabase;
import org.jbibtex.BibTeXEntry;
import org.jbibtex.BibTeXFormatter;
import org.jbibtex.BibTeXParser;
import org.jbibtex.Key;
import org.jbibtex.ParseException;
import org.jbibtex.StringValue;
import org.jbibtex.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author David Šarman
 */
@Service
public class ImportExportServiceImpl implements ImportExportService {
    private final static Logger log = LoggerFactory.getLogger(ImportExportServiceImpl.class);

    // The proper separator for multiple authors in bibtex files is "and" according to
    // https://tex.stackexchange.com/questions/36396/how-to-properly-write-multiple-authors-in-bibtex-file
    private final static String MULTIPLE_AUTHORS_SEPARATOR = " and ";
    private final static String PAGES_SEPARATOR = "--";
    private final static StringValue.Style VALUE_STYLE = StringValue.Style.QUOTED;

    @Inject
    private ReferenceService referenceService;

    @Inject
    private UserService userService;

    @Inject
    private TagService tagService;

    public BibTeXDatabase getBibDatabaseFromFile(File file) throws ImportException {
        BibTeXDatabase database = null;
        try (Reader fileReader = new FileReader(file)) {
            BibTeXParser parser = new BibTeXParser();
            database = parser.parseFully(fileReader);
        } catch (FileNotFoundException e) {
            String errorMsg = "Supplied file " + file + " could not be found with exception " + e;
            log.error(errorMsg);
            throw new ImportException(errorMsg, e);
        } catch (IOException e) {
            String errorMsg = "Exception occurred while reading file " + file;
            log.error(errorMsg);
            throw new ImportException(errorMsg, e);
        } catch (ParseException e) {
            String errorMsg = "Exception occurred while parsing file " + file;
            log.error(errorMsg);
            throw new ImportException(errorMsg, e);
        }
        if (database == null) {
            String errorMsg = "Error occurred while parsing, the parsed database was null";
            log.error(errorMsg);
            throw new ImportException(errorMsg);
        }
        return database;
    }

    private Reference parseBibTeXEntry(BibTeXEntry entry) {
        log.debug("Processing entry {}", entry);

        Reference reference = new Reference();

        Value title = entry.getField(BibTeXEntry.KEY_TITLE);
        Value author = entry.getField(BibTeXEntry.KEY_AUTHOR);
        Value pages = entry.getField(BibTeXEntry.KEY_PAGES);
        Value venue = entry.getField(BibTeXEntry.KEY_ADDRESS);

        if (title != null) {
            reference.setTitle(title.toUserString());
        }
        if (author != null) {
            String authorString = author.toUserString();
            List<String> authors = new ArrayList<>();
            if (authorString.contains(MULTIPLE_AUTHORS_SEPARATOR)) {
                authors.addAll(Arrays.asList(authorString.split(MULTIPLE_AUTHORS_SEPARATOR)));
            } else {
                authors.add(authorString);
            }
            reference.setAuthors(authors);
        }
        if (pages != null) {
            String pagesString = pages.toUserString();
            if (pagesString.contains(PAGES_SEPARATOR)) {
                String[] splitPages = pagesString.split(PAGES_SEPARATOR);
                if (splitPages.length == 2) {
                    try {
                        reference.setPagesStart(Integer.parseInt(splitPages[0]));
                        reference.setPagesEnd(Integer.parseInt(splitPages[1]));
                    } catch (NumberFormatException e) {
                        log.error("The pages string {} could not be parsed into individual pages", pagesString);
                    }
                }
            }
        }
        if (venue != null) {
            reference.setVenue(venue.toUserString());
        }
        return reference;
    }

    @Override
    public void importReferences(User user, File file, Tag tag) throws ImportException {
        if (user == null || file == null || tag == null) {
            throw new IllegalArgumentException("The supplied arguments must not be null.");
        }

        BibTeXDatabase database = getBibDatabaseFromFile(file);

        Map<Key, BibTeXEntry> entryMap = database.getEntries();
        for (BibTeXEntry entry : entryMap.values()) {
            Reference reference = parseBibTeXEntry(entry);
            referenceService.createReference(reference);
            userService.addReference(user.getId(), reference.getId());
            tagService.addReference(tag, reference);
        }
    }

    private BibTeXDatabase createDatabase(Set<Reference> references) {
        BibTeXDatabase database = new BibTeXDatabase();
        for (Reference reference : references) {
            String entryKeyString = reference.getTitle().replace(" ", "_").toLowerCase();
            entryKeyString = Normalizer.normalize(entryKeyString, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
            Key entryKey = new Key(entryKeyString);
            BibTeXEntry entry = new BibTeXEntry(BibTeXEntry.TYPE_MISC, entryKey);
            Value value = createStringValue(reference.getTitle());
            entry.addField(BibTeXEntry.KEY_TITLE, value);

            if (reference.getAuthors() != null && !reference.getAuthors().isEmpty()) {
                String joinedAuthors = String.join(MULTIPLE_AUTHORS_SEPARATOR, reference.getAuthors());
                value = createStringValue(joinedAuthors);
                entry.addField(BibTeXEntry.KEY_AUTHOR, value);
            }

            if (reference.getPagesStart() != null && reference.getPagesEnd() != null) {
                value = createStringValue(reference.getPagesStart() + PAGES_SEPARATOR + reference.getPagesEnd());
                entry.addField(BibTeXEntry.KEY_PAGES, value);
            }

            if (reference.getVenue() != null) {
                value = createStringValue(reference.getVenue());
                entry.addField(BibTeXEntry.KEY_ADDRESS, value);
            }

            database.addObject(entry);
        }

        return database;
    }

    private File createExportFile(String filename) throws ExportException {
        URL resourcesUrl = getClass().getClassLoader().getResource(".");
        if (resourcesUrl == null) {
            String errorMsg = "Resource folder returned by classloader is null";
            throw new ExportException(errorMsg);
        }

        File file = new File(resourcesUrl.getFile() + filename);
        boolean created = false;
        try {
            created = file.createNewFile();
        } catch (IOException e) {
            String errorMsg = "Exception occurred while creating the export file " + file;
            log.error(errorMsg);
            throw new ExportException(errorMsg, e);
        }

        if (!created) {
            String errorMsg = "Export file " + file + " could not be created because it alredy exists";
            log.error(errorMsg);
            throw new ExportException(errorMsg);
        }
        return file;
    }

    @Override
    public File exportReferencesToBibtex(Tag tag) throws ExportException {
        Set<Reference> references = tag.getReferences();

        BibTeXDatabase database = createDatabase(references);
        File exportFile = createExportFile(EXPORT_FILENAME_START + tag.getId() + ".bib");
        BibTeXFormatter formatter = new BibTeXFormatter();

        try (Writer fileWriter = new FileWriter(exportFile)) {
            formatter.format(database, fileWriter);
        } catch (IOException e) {
            String errorMsg = "Exception " + e + " occurred while writing to export file";
            log.error(errorMsg);
            throw new ExportException(errorMsg, e);
        }

        return exportFile;
    }

    @Override
    public File exportReferencesToCsv(Tag tag) throws ExportException {
        Set<Reference> references = tag.getReferences();

        List<List<String>> csvData = getCSVData(references);
        File exportFile = createExportFile(EXPORT_FILENAME_START + tag.getId() + ".csv");

        return CSVWriter.writeFile(csvData, exportFile);
    }

    private List<List<String>> getCSVData(Set<Reference> references) {
        List<List<String>> result = new ArrayList<>();

        List<String> header = new ArrayList<>();
        header.add("title");
        header.add("authors");
        header.add("venue");
        header.add("pages");
        result.add(header);

        for (Reference reference : references) {
            List<String> line = new ArrayList<>();
            line.add((reference.getTitle() == null) ? " " : reference.getTitle());

            List<String> authors = reference.getAuthors();
            String authorsString = " ";
            if (authors != null) {
                authorsString = String.join(MULTIPLE_AUTHORS_SEPARATOR, authors);
            }
            line.add(authorsString);

            line.add((reference.getVenue() == null) ? " " : reference.getVenue());

            Integer pagesStart = reference.getPagesStart();
            Integer pagesEnd = reference.getPagesEnd();
            String pagesString = " ";
            if (pagesStart != null && pagesEnd != null) {
                pagesString = pagesStart + "-" + pagesEnd;
            }
            line.add(pagesString);
            result.add(line);
        }
        return result;
    }

    private StringValue createStringValue(String value) {
        return new StringValue(value, VALUE_STYLE);
    }
}
