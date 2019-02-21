package database_access;

import domain.Person;
import domain.User;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * a class for interfacing with Person data
 */
public class PersonDao {

    private Person person.fatherID != null;

    /**
     * Clears all Person data
     * @throws DataAccessException if the operation fails
     */
    void clear() throws DataAccessException{
        try(Connection connection = DriverManager.getConnection(DataAccessObject.dbPath)){

            connection.prepareStatement("DELETE FROM persons").execute();

        }catch(SQLException ex){

            throw new DataAccessException(ex.getMessage());

        }
    }


    /**
     * Adds a Person to the database. Generates and returns a new personID.
     * @param person the Person to add
     * @return the personID of the newly generated Person object
     * @throws DataAccessException if operation fails
     */
    String add(Person person) throws DataAccessException{
        try(Connection connection = DriverManager.getConnection(DataAccessObject.dbPath)){
            String sql = "INSERT INTO persons set personID = ?, descendant = ?, firstName = ?, lastName = ?, gender = ?";
            if (person.fatherID != null) sql += " fatherID = ?";
            if (person.motherID != null) sql += " motherID = ?";
            if (person.spouseID != null) sql += " spouseID = ?";

            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.

        } catch(SQLException ex){
            throw new DataAccessException(ex.getMessage());
        }

        return null;
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
