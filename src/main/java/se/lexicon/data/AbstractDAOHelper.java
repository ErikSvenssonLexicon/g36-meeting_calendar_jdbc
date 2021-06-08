package se.lexicon.data;

import se.lexicon.model.AppUser;

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

    protected AppUser resultSetToAppUser(ResultSet resultSet) throws SQLException {
        return new AppUser(
                resultSet.getInt("id"),
                resultSet.getString("username"),
                resultSet.getString("password")
        );
    }

}
