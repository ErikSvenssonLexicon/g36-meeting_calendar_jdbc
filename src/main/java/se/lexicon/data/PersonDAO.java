package se.lexicon.data;

import se.lexicon.model.Person;

import java.util.Collection;
import java.util.Optional;

public interface PersonDAO {

    static PersonDAO getInstance(){
        return PersonDAOImpl.getInstance();
    }

    Person persist(Person person);
    Optional<Person> findById(Integer id);
    Optional<Person> findByEmail(String email);
    Optional<Person> findByUsername(String username);
    Collection<Person> findAll();
    Collection<Person> findByFirstName(String firstName);
    Collection<Person> findByLastName(String lastName);
    Person update(Person person);
    boolean remove(Integer id);


}
