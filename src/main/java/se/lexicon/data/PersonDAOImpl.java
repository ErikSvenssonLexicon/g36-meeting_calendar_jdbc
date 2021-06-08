package se.lexicon.data;

import se.lexicon.data.factory.ConnectionFactory;
import se.lexicon.model.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class PersonDAOImpl extends AbstractDAOHelper implements PersonDAO{

    private static final PersonDAOImpl INSTANCE = new PersonDAOImpl();

    private PersonDAOImpl(){}

    protected static PersonDAOImpl getInstance(){
        return INSTANCE;
    }

    @Override
    public Person persist(Person person) {
        //We dont want to persist null
        if(person == null) throw new IllegalArgumentException("Person person was null");
        //We want person to have id of 0
        if(person.getId() != 0) throw new IllegalArgumentException("Person person is already persisted");
        //We want person to have valid persisted UserCredentials (Persisted AppUser)
        if(person.getUserCredentials() != null){
            if(person.getUserCredentials().getId() == 0) throw new IllegalArgumentException("Person dont have persisted UserCredentials");

        }else {
            throw new IllegalArgumentException("UserCredentials was null");
        }
        //We want to avoid nasty stacktrace by checking for email
        if(findByEmail(person.getEmail()).isPresent()){
            throw new IllegalArgumentException("Email " + person.getEmail()+ " is already taken");
        }

        Person persisted = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement("INSERT INTO people (first_name, last_name, email, app_users_id) VALUES(?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, person.getFirstName());
            statement.setString(2, person.getLastName());
            statement.setString(3, person.getEmail());
            statement.setInt(4, person.getUserCredentials().getId());
            statement.execute();

            resultSet = statement.getGeneratedKeys();
            while(resultSet.next()){
                persisted = new Person(
                        resultSet.getInt(1),
                        person.getFirstName(),
                        person.getLastName(),
                        person.getEmail(),
                        person.getUserCredentials()
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
            closeAll(resultSet, statement, connection);
        }
        return persisted;
    }

    @Override
    public Optional<Person> findById(Integer id) {
        Person person = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement("SELECT people.id AS personId, first_name, last_name, email, app_users.id AS userId, username, password " +
                    "FROM people INNER JOIN app_users ON people.app_users_id = app_users.id WHERE people.id = ?");
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            while(resultSet.next()){
                person = resultSetToPerson(resultSet);
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }finally {
            closeAll(resultSet, statement, connection);
        }
        return Optional.ofNullable(person);
    }

    @Override
    public Optional<Person> findByEmail(String email) {
        Person person = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement("SELECT people.id AS personId, first_name, last_name, email, app_users.id AS userId, username, password " +
                    "FROM people INNER JOIN app_users ON people.app_users_id = app_users.id WHERE email = ?");
            statement.setString(1, email);
            resultSet = statement.executeQuery();

            while(resultSet.next()){
                person = resultSetToPerson(resultSet);
            }

        }catch (SQLException ex){
            ex.printStackTrace();
        }finally {
            closeAll(resultSet, statement, connection);
        }
        return Optional.ofNullable(person);
    }

    @Override
    public Optional<Person> findByUsername(String username) {
        Person person = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement("SELECT people.id AS personId, first_name, last_name, email, app_users.id AS userId, username, password " +
                    "FROM people INNER JOIN app_users ON people.app_users_id = app_users.id WHERE username = ?");
            statement.setString(1, username);
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                person = resultSetToPerson(resultSet);
            }

        }catch (SQLException ex){
            ex.printStackTrace();
        }finally {
            closeAll(resultSet, statement, connection);
        }
        return Optional.ofNullable(person);
    }

    @Override
    public Collection<Person> findAll() {
        List<Person> personList = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT people.id AS personId, first_name, last_name, email, app_users.id AS userId, username, password " +
                    "FROM people INNER JOIN app_users ON people.app_users_id = app_users.id");

            while(resultSet.next()){
                personList.add(resultSetToPerson(resultSet));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
            closeAll(resultSet, statement, connection);
        }
        return personList;
    }

    @Override
    public Collection<Person> findByFirstName(String firstName) {
        Collection<Person> people = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement("SELECT people.id AS personId, first_name, last_name, email, app_users.id AS userId, username, password " +
                    "FROM people INNER JOIN app_users ON people.app_users_id = app_users.id " +
                    "WHERE UPPER(first_name) LIKE UPPER(CONCAT('%',?,'%'))");
            statement.setString(1, firstName);
            resultSet = statement.executeQuery();
            while(resultSet.next()){
                people.add(resultSetToPerson(resultSet));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
            closeAll(resultSet, statement, connection);
        }
        return people;
    }

    @Override
    public Collection<Person> findByLastName(String lastName) {
        Collection<Person> people = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement("SELECT people.id AS personId, first_name, last_name, email, app_users.id AS userId, username, password " +
                    "FROM people INNER JOIN app_users ON people.app_users_id = app_users.id " +
                    "WHERE UPPER(last_name) LIKE UPPER(CONCAT('%',?,'%'))");

            statement.setString(1, lastName);

            resultSet = statement.executeQuery();
            while(resultSet.next()){
                people.add(resultSetToPerson(resultSet));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
            closeAll(resultSet, statement, connection);
        }
        return people;
    }

    /*
    UPDATE table_name
    SET column1 = value1, column2 = value2, ...
    WHERE condition;
     */
    @Override
    public Person update(Person person) {
        if(person == null) throw new IllegalArgumentException("Person person was null");
        if(person.getId() == 0) throw new IllegalArgumentException("Person person is not yet persisted");
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement("UPDATE people SET first_name = ?, last_name = ?, email = ? WHERE id = ?");
            statement.setString(1, person.getFirstName().trim());
            statement.setString(2, person.getLastName().trim());
            statement.setString(3, person.getEmail().trim());
            statement.setInt(4, person.getId());
            statement.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
            closeAll(statement, connection);
        }
        return person;
    }

    @Override
    public boolean remove(Integer id) {
        int rowsAffected = 0;
        try(
                Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement("DELETE FROM people WHERE id = ?")
        ) {
            rowsAffected = statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rowsAffected > 0;
    }
}
