package database_access;

import handlers.HttpExceptions.HttpInternalServerError;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * a class which represents the Database itself.
 *
 * Used by other classes in database_access package to generate and handle connections to the database.
 */
public class Database {

    private static Logger logger = Logger.getLogger("Database");



    /**
     * A Connection object
     */
    private static Connection conn;



    static {
        try{
            //Setting up driver for our database
            final String driver = "org.sqlite.JDBC";
            Class.forName(driver);
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }





    /**
     *
     * @return whether or not the Database has an open connection.
     */
    public boolean hasOpenConnection(){
        return conn != null;
    }

    /**
     * Opens a new connection via this Database object
     * @return a new Connection object
     * @throws DataAccessException if operation fails
     */
    public Connection openConnection() throws DataAccessException{
        logger.fine("Opening connection...");
        if (hasOpenConnection()){
            closeConnection(false);
            throw new DataAccessException("A previous connection has not been closed. Close previous connection.");
        }
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
        logger.fine("Connection opened.");
        return conn;
    }





    /**
     * Closes the current Connection object, with option to commit or rollback the connection
     * @param commit Whether or not to commit (vs rollback) the effects of the connection
     * @throws DataAccessException if operation fails
     */
    public void closeConnection(boolean commit) throws DataAccessException{
        //  If already closed, do nothing.
        if(!hasOpenConnection()) return;

        logger.fine("Closing connection...");
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
            logger.fine("Connection closed.");
        } catch (SQLException ex){
            ex.printStackTrace();
            throw new DataAccessException("Unable to close database connection");
        }
    }




    /**
     * Reads in the table data from db/dbschema.txt file, populates Database with given data
     * @throws DataAccessException if operation fails
     */
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
            }

            // Commit the successful operation
            closeConnection(true);

        } catch (SQLException ex){
            ex.printStackTrace();
            logger.severe(ex.getMessage());
            throw new DataAccessException("SQL Error encountered while creating tables");
        } catch (DataAccessException ex){
            ex.printStackTrace();
            closeConnection(false);
            throw ex;
        }
    }




    /**
     * Clears all data from all tables
     * @throws DataAccessException if operation fails
     */
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




    /**
     * Fills Database with data defined in db/dbfiller.txt file
     * @throws DataAccessException if operation fails
     */
    public void fillDatabase() throws DataAccessException{

        openConnection();

        List<String> sql = new ArrayList<>();
        try(Scanner scin = new Scanner(new File("db/dbfiller.txt"))){
            scin.useDelimiter(";");
            while(scin.hasNext()){
                sql.add(scin.next());
            }
        } catch (FileNotFoundException ex){
            ex.printStackTrace();
            closeConnection(false);
            throw new DataAccessException("Could not locate database filler file");
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
            throw new DataAccessException("SQL Error encountered while filling tables");
        } catch (DataAccessException ex){
            ex.printStackTrace();
            closeConnection(false);
            throw ex;
        }



    }

    public void hardClose() throws HttpInternalServerError{
        try {
            closeConnection(false);
        } catch (DataAccessException ex){
            throw new HttpInternalServerError(ex.getMessage());
        }
    }


    public static void main(String[] args){
        // Show me everything in the database
        Database db = new Database();
        try {
            Connection conn = db.openConnection();
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM users");
            int i = 0;
            while (rs.next()){
                i += 1;
            }
            System.out.println("Number of users: "+ i);

            rs = conn.createStatement().executeQuery("SELECT * FROM persons");
            i = 0;
            while (rs.next()){
                i += 1;
            }
            System.out.println("Number of persons: "+ i);

            rs = conn.createStatement().executeQuery("SELECT * FROM events");
            i = 0;
            while (rs.next()){
                i += 1;
            }
            System.out.println("Number of events: "+ i);

            rs = conn.createStatement().executeQuery("SELECT * FROM authTokens");
            i = 0;
            while (rs.next()){
                i += 1;
            }
            System.out.println("Number of authTokens: "+ i);

            db.closeConnection(false);




            //  Toggle clear database
            if(false){
                db.createTables();
            }


        } catch (DataAccessException | SQLException ex){
            ex.printStackTrace();
        }

    }
}
