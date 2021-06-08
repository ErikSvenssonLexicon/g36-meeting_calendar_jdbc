package se.lexicon;

import se.lexicon.data.AppUserDAO;
import se.lexicon.data.AppUserDAOImpl;
import se.lexicon.data.PersonDAO;
import se.lexicon.data.PersonDAOImpl;
import se.lexicon.model.AppUser;
import se.lexicon.model.Person;

import java.sql.SQLException;

public class App {
    public static void main(String[] args){
        AppUserDAO appUserDAO = AppUserDAO.getInstance();
        PersonDAO personDAO = PersonDAO.getInstance();

        personDAO.findByFirstName("nil").forEach(System.out::println);
        personDAO.findByLastName("en").forEach(System.out::println);
    }
}
