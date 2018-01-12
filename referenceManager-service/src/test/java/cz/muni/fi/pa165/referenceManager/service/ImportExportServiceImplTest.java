package cz.muni.fi.pa165.referenceManager.service;


import cz.muni.fi.pa165.referenceManager.entity.Reference;
import cz.muni.fi.pa165.referenceManager.entity.Tag;
import cz.muni.fi.pa165.referenceManager.entity.User;
import cz.muni.fi.pa165.referenceManager.exceptions.ExportException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ImportExportServiceImplTest {
    @Mock
    private ReferenceService referenceService;

    @Mock
    private UserService userService;

    @Mock
    private TagService tagService;

    @InjectMocks
    private ImportExportService service = new ImportExportServiceImpl();

    @Test(expected = IllegalArgumentException.class)
    public void importNullReferences() throws Exception {
        service.importReferences(null, null, null);
    }

    private void tryDelete(String filename) {
        URL url = getClass().getClassLoader().getResource(filename);
        if (url != null) {
            File file = new File(url.getFile());
            file.delete();
        }
    }

    @Before
    public void setUp() {
        tryDelete(service.EXPORT_FILENAME_START + 1 + ".bib");
        tryDelete(service.EXPORT_FILENAME_START + 1 + ".csv");
    }

    private List<Reference> getTestData() {
        Reference ref1 = new Reference();
        ref1.setTitle("{On} the electrodynamics of moving bodies");
        List<String> authors1 = new ArrayList<>();
        authors1.add("Albert Einstein");
        ref1.setAuthors(authors1);
        ref1.setPagesStart(891);
        ref1.setPagesEnd(921);

        Reference ref2 = new Reference();
        ref2.setTitle("The \\LaTeX\\ Companion");
        List<String> authors2 = new ArrayList<>();
        authors2.add("Michel Goossens");
        authors2.add("Frank Mittelbach");
        authors2.add("Alexander Samarin");
        ref2.setAuthors(authors2);
        ref2.setVenue("Reading, Massachusetts");

        Reference ref3 = new Reference();
        ref3.setTitle("Knuth: Computers and Typesetting");
        List<String> authors3 = new ArrayList<>();
        authors3.add("Donald Knuth");
        ref3.setAuthors(authors3);

        List<Reference> expectedReferences = new ArrayList<>();
        expectedReferences.add(ref1);
        expectedReferences.add(ref2);
        expectedReferences.add(ref3);

        return expectedReferences;
    }

    private File getTestImportFile() {
        ClassLoader classLoader = getClass().getClassLoader();
        return new File(classLoader.getResource("importBibTeXExample.bibtex").getFile());
    }

    private File getTestExportBibTeXFile() {
        ClassLoader classLoader = getClass().getClassLoader();
        return new File(classLoader.getResource("expectedBibTeXExport.bibtex").getFile());
    }

    private File getTestExportCSVFile() {
        ClassLoader classLoader = getClass().getClassLoader();
        return new File(classLoader.getResource("expectedCSVExport.csv").getFile());
    }

    @Test
    public void importReferences() throws Exception {
        File file = getTestImportFile();
        User user = new User();
        Tag tag = new Tag();

        service.importReferences(user, file, tag);

        for (Reference expectedReference : getTestData()) {
            verify(referenceService, times(1)).createReference(expectedReference);
            verify(referenceService, times(1)).addTag(expectedReference, tag);
        }
        verify(userService, times(3)).addReference(null, null);
    }

    @Test
    public void exportReferencesToBibtext() throws Exception {
        Tag tag = getTestTag();

        File exportedFile = service.exportReferencesToBibtex(tag);

        String exportedFileString = getNormalizedFileString(exportedFile);
        String expectedFileString = getNormalizedFileString(getTestExportBibTeXFile());

        assertEquals("Files should be the same", expectedFileString, exportedFileString);
    }

    @Test
    public void exportReferencesToCSV() throws Exception {
        Tag tag = getTestTag();

        File exportedFile = service.exportReferencesToCsv(tag);

        String exportedFileString = getNormalizedFileString(exportedFile);
        String expectedFileString = getNormalizedFileString(getTestExportCSVFile());

        assertEquals("Files should be the same", expectedFileString, exportedFileString);
    }

    private String getNormalizedFileString(File exportedFile) throws IOException {
        String result = new String(Files.readAllBytes(exportedFile.toPath()), Charset.defaultCharset());
        result = result.replaceAll("\\r\\n", "\n");
        return result.replaceAll("\\r", "\n");
    }

    private Tag getTestTag() {
        Set<Reference> references = new HashSet<>(getTestData());
        Tag tag = new Tag();
        tag.setName("Test tag");
        tag.setId(1L);
        tag.setReferences(references);

        return tag;
    }

}
