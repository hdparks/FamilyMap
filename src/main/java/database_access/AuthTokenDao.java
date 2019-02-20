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
    void clear() throws DataAccessException{

    }

    /**
     * Adds a new AuthToken
     * @param authToken the AuthToken to be added
     * @throws DataAccessException if the operation fails
     */
    void add(AuthToken authToken) throws DataAccessException{

    }

    /**
     * Deletes the given AuthToken
     * @param authToken the AuthToken to be deleted
     * @throws DataAccessException if the operation fails, ie. the given AuthToken is not found
     */
    void delete(AuthToken authToken) throws DataAccessException{

    }

    /**
     * Returns the User object linked to the given AuthToken. Calls UserDao.getUserByName
     * @param auth the supplied AuthToken object
     * @return user the User associated with the given AuthToken
     * @throws DataAccessException if the operation fails
     */
    User getUserByAuthToken(AuthToken auth) throws DataAccessException{
        return null;
    }

    /**
     * Generates a new AuthToken, storing it in the database, corresponding to user
     * @param user the User object who is requesting a new AuthToken
     * @return token the new AuthToken
     * @throws DataAccessException if the operation fails
     */
    AuthToken createAuthToken(User user) throws DataAccessException{
        return new AuthToken(user);
    }
}
