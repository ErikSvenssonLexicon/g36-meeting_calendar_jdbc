package se.lexicon.data;

import se.lexicon.data.factory.ConnectionFactory;
import se.lexicon.model.AppUser;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class AppUserDAOImpl extends AbstractDAOHelper implements AppUserDAO{

    private static final AppUserDAOImpl INSTANCE = new AppUserDAOImpl();

    private AppUserDAOImpl(){}

    static AppUserDAOImpl getInstance(){
        return INSTANCE;
    }



    @Override
    public AppUser persist(AppUser appUser) {
        if(appUser == null) throw new IllegalArgumentException("AppUser appUser was null");
        if(appUser.getId() != 0) throw new IllegalArgumentException("AppUser is already persisted in database");
        if(findByUsername(appUser.getUsername()).isPresent()){
            throw new IllegalArgumentException("Username " + appUser.getUsername() + " is already taken");
        }
        AppUser response = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet keySet = null;
        try{
            //We need a connection to the database
            connection = ConnectionFactory.getConnection();
            //We need a PreparedStatement to insert data
            statement = connection.prepareStatement("INSERT INTO app_users (username, password) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
            //We need to set the question marks (?) to real data
            statement.setString(1, appUser.getUsername());
            statement.setString(2, appUser.getPassword());
            //Performing the insert
            statement.execute();

            //Fetching the generated primary key from a ResultSet with statement.getGeneratedKeys()
            keySet = statement.getGeneratedKeys();
            while(keySet.next()){
                response = new AppUser(
                        keySet.getInt(1),
                        appUser.getUsername(),
                        appUser.getPassword()
                );
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }finally {
            closeAll(keySet, statement, connection);
        }
        return response;
    }



    @Override
    public Collection<AppUser> findAll() {
        List<AppUser> appUserList = new ArrayList<>();
        try(
                Connection connection = ConnectionFactory.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM app_users")
        ) {
            while(resultSet.next()){
                appUserList.add(resultSetToAppUser(resultSet));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return appUserList;
    }

    @Override
    public Optional<AppUser> findByUsername(String username) {
        AppUser result = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM app_users WHERE username = ?");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                result = resultSetToAppUser(resultSet);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
            closeAll(resultSet, preparedStatement, connection);
        }
        return Optional.ofNullable(result);
    }

    @Override
    public Optional<AppUser> findById(Integer id) {
        AppUser result = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM app_users WHERE id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                result = resultSetToAppUser(resultSet);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
            closeAll(resultSet, preparedStatement, connection);
        }
        return Optional.ofNullable(result);
    }

    /*
    UPDATE table_name
    SET column1 = value1, column2 = value2, ...
    WHERE condition;
     */
    @Override
    public AppUser update(AppUser appUser) {
        if(appUser == null) throw new IllegalArgumentException("AppUser appUser was null");
        if(appUser.getId() == 0) throw new IllegalArgumentException("AppUser is not persisted");
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement("UPDATE app_users SET username = ?, password = ? WHERE id = ?");
            statement.setString(1, appUser.getUsername());
            statement.setString(2, appUser.getPassword());
            statement.setInt(3, appUser.getId());
            statement.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
            closeAll(statement, connection);
        }
        return appUser;
    }


    /*
    DELETE FROM table_name WHERE condition;
     */
    @Override
    public boolean remove(Integer id) {
        Connection connection = null;
        PreparedStatement statement = null;
        int rowsDeleted = 0;
        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement("DELETE FROM app_users WHERE id = ?");
            statement.setInt(1, id);
            rowsDeleted = statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
            closeAll(statement, connection);
        }
        return rowsDeleted > 0;
    }
}
