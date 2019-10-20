import java.util.List;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


public class PersonExercice {

    public static class PersonExercise {

        public static void main(String[] args) {
            PersonExercise personExercise = new PersonExercise();
            List<Person> persons = personExercise.readPersonsFromFile("C:/Users/tommu/Desktop/Person_NewsPoint.txt", ",");
            System.out.println(personExercise.findOldestPersonWithPhone(persons));

            System.out.println(String.format("List contains [%d] users", personExercise.getNumberOfUsers(persons)));

            Optional<Person> oldestPerson = personExercise.findOldestPersonWithPhone(persons);
            oldestPerson.ifPresent(person -> {
                System.out.println(String.format("The oldest user of age [%d] is [%s %s] with phone number [%s]",
                        personExercise.calculatePersonAge(person.getDateOfBirth()), person.getName(), person.getSurname(), person.getPhoneNumber()));
            });

        }

        public List<Person> readPersonsFromFile(String filePath, String tokenSeparator) {
            Objects.requireNonNull(filePath, "Path cannot be null!!!");

            List<Person> result = Collections.emptyList();
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                result = reader.lines()
                        .map(s -> Person.createPersonFromTextLine(s, tokenSeparator))
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .collect(Collectors.toList());

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        int getNumberOfUsers(List<Person> persons) {
            return persons.size();
        }

        Optional<Person> findOldestPersonWithPhone(List<Person> persons) {
            return persons.stream()
                    .filter(person -> person.getPhoneNumber() != null)
                    .min(Comparator.comparing(Person::getDateOfBirth));
        }

        int calculatePersonAge(LocalDate dateOfBirth) {
            return LocalDate.now().getYear() - dateOfBirth.getYear();
        }
    }



}
