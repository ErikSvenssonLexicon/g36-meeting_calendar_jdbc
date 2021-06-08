package se.lexicon.data;

import se.lexicon.model.AppUser;
import se.lexicon.model.Person;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractDAOHelper {
    protected void closeAll(AutoCloseable...closeables){
        if(closeables != null){
            for(AutoCloseable closeable : closeables){
                if(closeable != null){
                    try {
                        closeable.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    protected Person resultSetToPerson(ResultSet resultSet) throws SQLException {
        return new Person(
                resultSet.getInt("personId"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getString("email"),
                new AppUser(
                        resultSet.getInt("userId"),
                        resultSet.getString("username"),
                        resultSet.getString("password")
                )
        );
    }

    protected AppUser resultSetToAppUser(ResultSet resultSet) throws SQLException {
        return new AppUser(
                resultSet.getInt("id"),
                resultSet.getString("username"),
                resultSet.getString("password")
        );
    }

}
