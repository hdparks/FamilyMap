package database_access;

import domain.AuthToken;
import domain.User;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * a class for interfacing with AuthToken data
 */
public class AuthTokenDao {

    private static Logger logger  = Logger.getLogger("AuthTokenDao");

    Connection conn;


    /**
     * Connects an AuthTokenDao object to the Database object
     * @param conn a database connection
     */
    public AuthTokenDao(Connection conn){ this.conn = conn; }

    /**
     * Clears the AuthToken table
     * @throws DataAccessException if the operation fails
     */
    public void clear() throws DataAccessException{
        try {
            conn.createStatement().executeUpdate("DELETE FROM authTokens");
        } catch (SQLException ex){
            logger.log(Level.SEVERE,ex.getMessage());
            throw new DataAccessException("Error occurred while clearing Authentication Token data");
        }
    }

    /**
     * Adds a new AuthToken
     * @param authToken the AuthToken to be added
     * @throws DataAccessException if the operation fails
     */
    public void add(AuthToken authToken) throws DataAccessException{
        String sql = "INSERT INTO authTokens (authToken, userName) VALUES (?,?)";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1,authToken.authToken);
            stmt.setString(2,authToken.userName);
            stmt.executeUpdate();
        } catch (SQLException ex){
            logger.log(Level.SEVERE,ex.getMessage());
            throw new DataAccessException("Error adding AuthToken");
        }
    }

    /**
     * Returns the User object linked to the given AuthToken.
     * @param auth the supplied AuthToken string
     * @return userName of the User associated with the given AuthToken, null if no such token is found
     * @throws DataAccessException if the operation fails
     */
    public String getUsernameByAuthToken(String auth) throws DataAccessException{
        String sql = "SELECT username FROM authTokens WHERE authToken = ?";
        String userName = null;

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1,auth);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                userName = rs.getString(1);
            }
            return userName;
        } catch (SQLException ex){
            logger.log(Level.SEVERE, ex.getMessage());
            throw new DataAccessException("Error fetching User data from given Authentication Token");
        }
    }
}
