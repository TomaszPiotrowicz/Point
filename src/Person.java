import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

public class Person {

    private static final int numOfTokenWithPhone = 4;
    private static final int numOfTokenWithoutPhoneNum = 3;

    private String name;
    private String surname;
    private LocalDate dateOfBirth;
    private String phoneNumber;

    public Person(String name, String surname, LocalDate dateOfBirth, String phoneNumber) {
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
    }

    private static boolean isValidPersonRecord(String[] personTokens) {

        return personTokens.length == numOfTokenWithPhone || personTokens.length == numOfTokenWithoutPhoneNum;
    }

    public static Optional<Person> createPersonFromTextLine(String line, String tokenSeparator) {
        final int numOfTokenWithPhone = 4;
        final int numOfTokenWithoutPhoneNum = 3;

        return Optional.of(line)
                .map(s -> s.split(tokenSeparator))
                .filter(Person::isValidPersonRecord)
                .flatMap(Person::createPersonFromTokens);
    }

    private static Optional<Person> createPersonFromTokens(String[] arguments) {
        final int indexOfName = 0;
        final int indexOfSurname = 1;
        final int indexOfDateOfBirth = 2;
        final int indexOfPhoneNum = 3;

        LocalDate dateOfBirth = null;

        Optional<Person> result = Optional.empty();
        try {
            dateOfBirth = LocalDate.parse(arguments[indexOfDateOfBirth]);
            result = arguments.length == numOfTokenWithPhone ? Optional.of(new Person(arguments[indexOfName], arguments[indexOfSurname], dateOfBirth, arguments[indexOfPhoneNum])) :
                    Optional.of(new Person(arguments[indexOfName], arguments[indexOfSurname], dateOfBirth, null));
        } catch (DateTimeParseException exc) {
            System.out.println("Exception during parsing line. Discarding...");
        }

        return result;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
