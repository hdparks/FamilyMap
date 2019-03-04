package database_access;

import domain.User;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * a class for interfacing with User data
 */
public class UserDao {

    private static Logger logger = Logger.getLogger("UserDao");

    Connection conn;

    /**
     * Connects a UserDao object to the Database object
     * @param conn a database connection
     */
    UserDao(Connection conn) { this.conn = conn; }



    /**
     * Clears the users table
     *
     * @throws DataAccessException if operation fails
     */
    public void clear() throws DataAccessException {
        try {
           conn.createStatement().executeUpdate("DELETE FROM users");
        } catch (SQLException ex){
            logger.log(Level.SEVERE,ex.getMessage());
            throw new DataAccessException("Error occurred while clearing users table");
        }
    }

    /**
     * Adds a User
     *
     * @param user the User to be added
     * @throws DataAccessException if operation fails
     */
    public void add(User user) throws DataAccessException{
        String sql = "INSERT INTO users (userName, password, email, firstName, lastName, gender, personID) " +
                "VALUES (?,?,?,?,?,?,?)";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, user.userName);
            stmt.setString(2, user.password);
            stmt.setString(3, user.email);
            stmt.setString(4, user.firstName);
            stmt.setString(5, user.lastName);
            stmt.setString(6, user.gender);
            stmt.setString(7, user.personID);

            stmt.executeUpdate();
            stmt.close();
        } catch(SQLException ex){
            logger.log(Level.SEVERE,ex.getMessage());
            throw new DataAccessException("Error while creating User");
        }
    }

    /**
     * Deletes the given User
     * @param userName username of the User to deleteUserByUsername
     * @throws DataAccessException if operation fails, ie. User is not found
     */
    public void deleteUserByUsername(String userName) throws DataAccessException{
        try{
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM users WHERE username = ?");
            stmt.setString(1,userName);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            logger.log(Level.SEVERE,ex.getMessage());
            throw new DataAccessException("Error encountered while deleting User");
        }
    }

    /**
     * Retrieves a user which has the given username
     * @param username the username of the desired User entity
     * @return User an object representing the user with the given username. Null if not found.
     * @throws DataAccessException if operation fails
     */
    public User getUserByName(String username) throws DataAccessException{
        User user = null;
        String sql = "SELECT * FROM users WHERE userName = ?";

        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1,username);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                String userName = rs.getString(1);
                String password = rs.getString(2);
                String email = rs.getString(3);
                String firstName = rs.getString(4);
                String lastName = rs.getString(5);
                String gender= rs.getString(6);
                String personID = rs.getString(7);

                user = new User(userName, password, email, firstName, lastName, gender, personID);
            }

        } catch (SQLException ex){
            logger.log(Level.SEVERE,ex.getMessage());
            throw new DataAccessException("Error getting User from database");
        }
        //  If ResultSet is empty, returns a null user object.
        return user;
    }

}
