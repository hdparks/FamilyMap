package database_access;

import domain.User;

/**
 * a class for interfacing with User data
 */
public class UserDao {

    /**
     * Delete all users from the "users" table
     *
     * @throws DataAccessException if operation fails
     */
    void clear() throws DataAccessException {

    }

    /**
     * Adds a User
     *
     * @param user the User to be added
     * @throws DataAccessException if operation fails
     */
    void add(User user) throws DataAccessException{

    }

    /**
     * Deletes a User
     * @param user the User to delete
     * @throws DataAccessException if operation fails, ie. User is not found
     */
    void delete(User user) throws DataAccessException{

    }

    /**
     * Retrieves a user which has the given username
     * @param username the username of the desired User entity
     * @return User an object representing the user with the given username. Null if not found.
     * @throws DataAccessException if operation fails
     */
    User getUserByName(String username) throws DataAccessException{
        return null;
    }

}
