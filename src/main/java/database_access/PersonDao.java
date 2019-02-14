package database_access;

import domain.Person;
import domain.User;
import java.util.List;

/**
 * a class for interfacing with Person data
 */
public class PersonDao {

    /**
     * Clears the Persons table
     * @throws DataAccessException if the operation fails
     */
    void clear() throws DataAccessException{

    }

    /**
     * Returns an instance of domain.Person representing the person with the given ID.
     * @param id the personID of the person in question
     * @return Person a Person object
     * @throws DataAccessException if the operation fails
     */
    Person getPersonByID(String id) throws DataAccessException {
        return null;
    }

    /**
     * Returns a List of Person objects corresponding to every person whose descendant is {username}
     * @param username the username of the descendant of each Person in the list
     * @return a List of Person objects
     * @throws DataAccessException if the operation fails
     */
    List<Person> getPersonListByUser(String username) throws DataAccessException{
        return null;
    }
}
