package se.lexicon.data.factory;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {

    private static final String URL;
    private static final String USER;
    private static final String PASSWORD;

    static {
        Properties properties = new Properties();
        try(FileReader reader = new FileReader("credentials/connection.properties")){
            properties.load(reader);
        }catch (IOException ex){
            throw new RuntimeException(ex);
        }
        URL = properties.getProperty("URL");
        USER = properties.getProperty("USER");
        PASSWORD = properties.getProperty("PASSWORD");
    }



    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
