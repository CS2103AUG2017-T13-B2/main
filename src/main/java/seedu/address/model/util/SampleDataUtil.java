package seedu.address.model.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Score;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    //@@author Linus
    public static int NUM_OF_SAMPLE_DATA = 8;
    public static String[] firstNames = {"Roy", "David", "Alex", "Linus", "Siri", "Hector", "Achilles", "Odysseus",
            "Jack", "Donald"};
    public static String[] lastNames = {"Herason", "Trump", "Thompson", "Curry", "Westbrook", "Harden", "O'niel",
            "Clinton", "Obama", "Washington"};
    public static String[] emails = {"gmail.com", "yahoo", "outlook", "hotmail", "qq.com", "sina.com"};
    public static String[] tags = {"colleagues", "friends", "president", "students", "customers", "lawyers", "farmers"};
    public static String[] birthdays = {"01/09/198", "03/08/197", "02/02/198", "31/12/198", "28/05/198", "03/02/198"};

    public static Person[] getSamplePersons() {
            /*
             *  Sample data
             *  It generates 100 test data
             * */
            Random rand = new Random();
            String firstName = null;
            ArrayList<Person> persons = new ArrayList<Person>();

                for (int i = 0; i < NUM_OF_SAMPLE_DATA; i++) {

                    firstName = firstNames[i % (NUM_OF_SAMPLE_DATA)];

                    for (int j = 0; j < NUM_OF_SAMPLE_DATA - 1; j++) {

                        try {

                            persons.add(generatePerson(firstName, j, rand));

                        } catch (IllegalValueException e) {

                            throw new AssertionError("sample data cannot be invalid", e);

                        }
                    }
                }
                return persons.toArray(new Person[persons.size()]);
    }

    public static Set<Tag> generateTags(int i) throws IllegalValueException {

        HashSet<Tag> addTags = new HashSet<>();
        for(int j = 0; j < i; j++){
            addTags.add(new Tag(tags[j]));
        }
        return addTags;
    }

    public static Person generatePerson(String firstName, int j, Random rand) throws IllegalValueException {

        /*
        *  Those numbers are totally arbitrary for generating sample data
        * */
        String lastName = lastNames[j % (NUM_OF_SAMPLE_DATA-2)];
        String phone = Integer.toString(00000 + rand.nextInt(90000000));
        String email = emails[j % (NUM_OF_SAMPLE_DATA-2)];
        String units = Integer.toString(rand.nextInt(900));
        String score = Integer.toString(rand.nextInt(9));
        Set<Tag> tag = generateTags(j);

        if(j / 3 == 1){
            return new Person(new Name(firstName + " " + lastName), new Phone(phone),
                    new Birthday("No Birthday Listed"),
                    new Email(firstName + "@" + email),
                    new Address("Blk 45 Aljunied Street 85, #" + units),
                    new Score(score), tag);
        }
        return new Person(new Name(firstName + " " + lastName), new Phone(phone),
                new Birthday(generateBirthday(j)),
                new Email(firstName + "@" + email),
                new Address("Blk 45 Aljunied Street 85, #" + units),
                new Score(score), tag);

    }

    public static String generateBirthday(int i){
        int n = i % 6;
        return birthdays[n] + Integer.toString(n);
    }

    //@@author Linus

    public static ReadOnlyAddressBook getSampleAddressBook() {
        try {
            AddressBook sampleAb = new AddressBook();
            for (Person samplePerson : getSamplePersons()) {
                sampleAb.addPerson(samplePerson);
            }
            return sampleAb;
        } catch (DuplicatePersonException e) {
            throw new AssertionError("sample data cannot contain duplicate persons", e);
        }
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) throws IllegalValueException {
        HashSet<Tag> tags = new HashSet<>();
        for (String s : strings) {
            tags.add(new Tag(s));
        }

        return tags;
    }

}
