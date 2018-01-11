package cz.muni.fi.pa165.referenceManager.sampleData;

import cz.muni.fi.pa165.referenceManager.entity.Note;
import cz.muni.fi.pa165.referenceManager.entity.Reference;
import cz.muni.fi.pa165.referenceManager.entity.Tag;
import cz.muni.fi.pa165.referenceManager.entity.User;
import cz.muni.fi.pa165.referenceManager.service.NoteService;
import cz.muni.fi.pa165.referenceManager.service.ReferenceService;
import cz.muni.fi.pa165.referenceManager.service.TagService;
import cz.muni.fi.pa165.referenceManager.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author Lenka Smitalova
 */
@Component
@Transactional
public class SampleDataLoadingFacadeImpl implements SampleDataLoadingFacade {
    final static Logger log = LoggerFactory.getLogger(SampleDataLoadingFacadeImpl.class);

    @Autowired
    private UserService userService;

    @Autowired
    private ReferenceService referenceService;

    @Autowired
    private TagService tagService;

    @Autowired
    private NoteService noteService;

    @Override
    @SuppressWarnings("unused")
    public void loadData() {

        User adam = user("Adam", "adam@user.cz", "adamPassword", true);
        User kaja = user("Kaja", "kaja@user.cz", "kajaPassword", false);
        User anna = user("Anna", "anna@user.cz", "annaPassword", false);
        log.info("Users loaded.");

        Tag school = tag("School", adam, anna);
        Tag freeTime = tag("Free time", anna, kaja);
        log.info("Tags loaded and added to users.");

        Reference ref1 = reference("Effective Java",
            Arrays.asList("Joshua Bloch"), "USA", 110, 152, adam, school);
        Reference ref2 = reference("Lord of the Rings",
            Arrays.asList("J. R. R. Tolkien"), "UK", null, null, anna, freeTime);
        Reference ref3 = reference("The Pilgrimage",
            Arrays.asList("Paulo Coelho"), "Brazil", 34, 67, adam,  freeTime);
        log.info("References loaded and added to users.");

        Note note1 = note("This highly anticipated new edition of the classic," +
            " Jolt Award-winning work has been thoroughly updated to cover Java SE 5 " +
            "and Java SE 6 features introduced since the first edition.", ref1);
        Note note2 = note("Best trilogy ever!", ref2);
        Note note3 = note("Bloch explores new design patterns and language idioms, " +
            "showing you how to make the most of features ranging from generics to enums, " +
            "annotations to autoboxing.", ref1);
        Note note4 = note("Check other work of Tolkien.", ref2);
        Note note5 = note("A recollection of Paulo's experiences as he made his way " +
            "across northern Spain on a pilgrimage to Santiago de Compostela", ref3);
        Note note6 = note("First novel written by him.", ref3);
        log.info("Notes loaded.");
    }

    private Note note(String text, Reference ref) {
        Note note = new Note();
        note.setText(text);
        note.setReference(ref);
        noteService.create(note);
        return note;
    }

    private Reference reference(String title, List<String> authors, String venue,
                                Integer pagesStart, Integer pagesEnd, User user, Tag... tags) {
        Reference ref = new Reference();
        ref.setTitle(title);
        ref.setAuthors(authors);
        ref.setVenue(venue);
        ref.setPagesStart(pagesStart);
        ref.setPagesEnd(pagesEnd);
        ref.setOwner(user);
        for (Tag tag : tags) {
            ref.addTag(tag);
        }
        referenceService.createReference(ref);
        return ref;
    }

    private Tag tag(String name, User... users){
        Tag tag = new Tag();
        tag.setName(name);
        for (User u : users) {
            tag.addUser(u);
        }
        tagService.create(tag);
        return tag;
    }

    private User user(String name, String email, String password, boolean admin) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setAdmin(admin);
        userService.registerUser(user, password);
        return user;
    }

    private void userAddReferences(User user, Reference... references) {
        for (Reference ref : references) {
            userService.addReference(user.getId(), ref.getId());
        }
    }

    private void userAddTags(User user, Tag... tags) {
        for (Tag tag : tags) {
            userService.addTag(user.getId(), tag.getId());
        }
    }

    private void userAddSharedTags(User user, Tag... sharedTags) {
        for(Tag sharedTag : sharedTags) {
            userService.shareTag(user.getId(), sharedTag.getId());
        }
    }
}
