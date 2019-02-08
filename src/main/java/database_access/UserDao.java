package database_access;

import domain.User;

/**
 * UserDao contains all user-related database operations
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
     * Insert a new user into the table
     *
     * @param user an instance of domain.user
     * @throws DataAccessException if operation fails
     */
    void createUser(User user) throws DataAccessException{

    }

    /**
     * Retrieves a user from the database which has the given username
     * @param username the username of the desired User entity
     * @return User an object representing the user with the given username. Null if not found.
     * @throws DataAccessException if operation fails
     */
    User getUserByName(String username) throws DataAccessException{
        return null;
    }





}
