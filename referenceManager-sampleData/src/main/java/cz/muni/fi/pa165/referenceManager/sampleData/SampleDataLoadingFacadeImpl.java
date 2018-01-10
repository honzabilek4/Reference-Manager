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
        Note note1 = note("This highly anticipated new edition of the classic," +
            " Jolt Award-winning work has been thoroughly updated to cover Java SE 5 " +
            "and Java SE 6 features introduced since the first edition.");
        Note note2 = note("Best trilogy ever!");
        Note note3 = note("Bloch explores new design patterns and language idioms, " +
            "showing you how to make the most of features ranging from generics to enums, " +
            "annotations to autoboxing.");
        Note note4 = note("Check other work of Tolkien.");
        Note note5 = note("This is the 5th note.");
        Note note6 = note("This is the 6th note.");
        log.info("Notes loaded.");

        Reference ref1 = reference("Effective Java",
            Arrays.asList("Joshua Bloch"), "USA", 110, 152,
            note1, note3);
        Reference ref2 = reference("Lord of the Rings",
            Arrays.asList("J. R. R. Tolkien"), "UK", null, null,
            note2, note4);
        Reference ref3 = reference("Another title",
            Arrays.asList("Author1", "Different Author"), "Prague", 34, 67,
            note5, note6);
        log.info("References loaded.");

        Tag school = tag("School", ref1, ref3);
        Tag freeTime = tag("Free time", ref2);
        log.info("Tags loaded.");

        User adam = user("Adam", "adam@user.cz", "adamPassword");
        User kaja = user("Kaja", "kaja@user.cz", "kajaPassword");
        User anna = user("Anna", "anna@user.cz", "annaPassword");
        log.info("Users loaded.");

        userAddReferences(adam, ref1, ref3);
        userAddReferences(anna, ref2);

        userAddTags(adam, school);
        userAddTags(anna, freeTime);

//        userAddSharedTags(kaja, freeTime);
//        userAddSharedTags(anna, school);
        log.info("References, tags and shared tags added to users.");
    }

    private Note note(String text) {
        Note note = new Note();
        note.setText(text);
        noteService.create(note);
        return note;
    }

    private Reference reference(String title, List<String> authors, String venue,
                                Integer pagesStart, Integer pagesEnd, Note... notes) {
        Reference ref = new Reference();
        ref.setTitle(title);
        ref.setAuthors(authors);
        ref.setVenue(venue);
        ref.setPagesStart(pagesStart);
        ref.setPagesEnd(pagesEnd);
        for (Note note : notes) {
            ref.addNote(note);
        }
        referenceService.createReference(ref);
        return ref;
    }

    private Tag tag(String name, Reference... references){
        Tag tag = new Tag();
        tag.setName(name);
        for (Reference ref : references) {
            tag.addReference(ref);
        }
        tagService.create(tag);
        return tag;
    }

    private User user(String name, String email, String password) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
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
