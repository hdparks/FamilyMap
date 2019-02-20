package database_access;

import domain.Person;
import domain.User;

import javax.xml.crypto.Data;
import java.util.List;

/**
 * a class for interfacing with Person data
 */
public class PersonDao {

    /**
     * Clears all Person data
     * @throws DataAccessException if the operation fails
     */
    void clear() throws DataAccessException{

    }


    /**
     * Adds a Person
     * @param person the Person to add
     * @throws DataAccessException if operation fails
     */
    void add(Person person) throws DataAccessException{

    }

    /**
     * Deletes the given Person
     * @param person the Person to delete
     * @throws DataAccessException if the operation fails, ie. the Person is not found
     */
    void delete(Person person) throws DataAccessException{

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
