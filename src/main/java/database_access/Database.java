package database_access;

import javax.xml.crypto.Data;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Database {
    private Connection conn;

    static {
        try{
            //Setting up driver for our database
            final String driver = "org.sqlite.JDBC";
            Class.forName(driver);
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public Connection openConnection() throws DataAccessException{
        try {
            final String CONNECTION_URL = "jdbc:sqlite:db" + File.separator + "familymapdatabase.sqlite";

            // Open a connection
            conn = DriverManager.getConnection(CONNECTION_URL);

            // Start a transaction
            conn.setAutoCommit(false);
        } catch (SQLException ex){
            ex.printStackTrace();
            throw new DataAccessException("Unable to open connection to database");
        }
        return conn;
    }

    public void closeConnection(boolean commit) throws DataAccessException{
        try {
            if(commit){
                // commit the changes to the database
                conn.commit();
            } else {
                // roll back
                conn.rollback();
            }

            conn.close();
            conn = null;
        } catch (SQLException ex){
            ex.printStackTrace();
            throw new DataAccessException("Unable to close database connection");
        }
    }


    public void createTables() throws  DataAccessException{

        openConnection();

        try(Statement stmt = conn.createStatement(); Scanner scin = new Scanner(new File("main/java/database_access/dbschema.txt"))){
            // Read in database instructions
            String sql = scin.useDelimiter("//Z").next();
            // Execute instructions
            stmt.executeUpdate(sql);
            // Commit the successful operation
            closeConnection(true);

        } catch (FileNotFoundException ex){
            ex.printStackTrace();
            throw new DataAccessException("Could not locate database construction file");
        } catch (SQLException ex){
            ex.printStackTrace();
            throw new DataAccessException("SQL Error encountered while creating tables");
        } catch (DataAccessException ex){
            closeConnection(false);
            throw ex;
        }
    }
}
