package database_access;

import domain.AuthToken;
import domain.User;

import javax.xml.crypto.Data;

/**
 * a class for interfacing with AuthToken data
 */
public class AuthTokenDao {

    /**
     * Clears the AuthToken table
     * @throws DataAccessException if the operation fails
     */
    public void clear() throws DataAccessException{

    }

    /**
     * Adds a new AuthToken
     * @param authToken the AuthToken to be added
     * @throws DataAccessException if the operation fails
     */
    public void add(AuthToken authToken) throws DataAccessException{

    }

    /**
     * Deletes the given AuthToken
     * @param authToken the AuthToken to be deleted
     * @throws DataAccessException if the operation fails, ie. the given AuthToken is not found
     */
    public void delete(AuthToken authToken) throws DataAccessException{

    }

    /**
     * Returns the User object linked to the given AuthToken.
     * @param auth the supplied AuthToken object
     * @return userName of the User associated with the given AuthToken
     * @throws DataAccessException if the operation fails
     */
    public String getUserByAuthToken(AuthToken auth) throws DataAccessException{
        return null;
    }

}
