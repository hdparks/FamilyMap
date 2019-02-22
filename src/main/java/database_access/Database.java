package database_access;

import javax.xml.crypto.Data;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
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

    public boolean hasOpenConnection(){
        return conn != null;
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

        List<String> sql = new ArrayList<>();
        try(Scanner scin = new Scanner(new File("db/dbschema.txt"))){
            scin.useDelimiter(";");
            while(scin.hasNext()){
                sql.add(scin.next());
            }
        } catch (FileNotFoundException ex){
            ex.printStackTrace();
            closeConnection(false);
            throw new DataAccessException("Could not locate database construction file");
        }

        try{

            // Execute instructions

            for (String s : sql){
                PreparedStatement stmt = this.conn.prepareStatement(s);
                stmt.executeUpdate();
                stmt.close();
            }

            // Commit the successful operation
            closeConnection(true);

        } catch (SQLException ex){
            throw new DataAccessException("SQL Error encountered while creating tables");
        } catch (DataAccessException ex){
            ex.printStackTrace();
            closeConnection(false);
            throw ex;
        }
    }final

    public void clearTables() throws DataAccessException{

        openConnection();

        try{
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM users");
            stmt.executeUpdate("DELETE FROM persons");
            stmt.executeUpdate("DELETE FROM events");
            stmt.executeUpdate("DELETE FROM authTokens");

            closeConnection(true);
        } catch(DataAccessException ex){
            closeConnection(false);
            throw ex;
        } catch(SQLException ex){
            closeConnection(false);
            throw new DataAccessException("SQL error in clearing tables: \n" + ex.getMessage());
        }
    }
}
