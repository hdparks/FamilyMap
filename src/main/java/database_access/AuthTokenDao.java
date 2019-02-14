package database_access;

import domain.AuthToken;
import domain.User;

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
