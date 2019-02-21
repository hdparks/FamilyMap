package database_access;

import domain.Person;

import java.sql.*;
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

            stmt.setString(1, person.personID);
            stmt.setString(2, person.descendant);
            stmt.setString(3, person.firstName);
            stmt.setString(4, person.lastName);
            stmt.setString(5, person.gender);

            int next = 6;
            if (person.fatherID != null){ stmt.setString(next, person.fatherID); next += 1; }
            if (person.motherID != null){ stmt.setString(next, person.motherID); next += 1; }
            if (person.spouseID != null){ stmt.setString(next, person.spouseID); next += 1; }

            stmt.execute();

        } catch(SQLException ex){
            throw new DataAccessException(ex.getMessage());
        }

        return person.personID;
    }

    /**
     * Deletes the given Person
     * @param personID the Id of the Person to delete
     * @throws DataAccessException if the operation fails, ie. the Person is not found
     */
    void deletePersonByID(String personID) throws DataAccessException{
        try(Connection connection = DriverManager.getConnection(DataAccessObject.dbPath)){

            PreparedStatement stmt = connection.prepareStatement("DELETE FROM persons WHERE personID = ?");
            stmt.setString(1,personID);
            stmt.execute();

        } catch (SQLException ex){
            throw new DataAccessException(ex.getMessage());
        }
    }

    /**
     * Returns an instance of domain.Person representing the person with the given ID.
     * @param id the personID of the person in question
     * @return Person a Person object
     * @throws DataAccessException if the operation fails
     */
    Person getPersonByID(String id) throws DataAccessException {
        try (Connection connection = DriverManager.getConnection(DataAccessObject.dbPath)){
            String sql = "SELECT personID, descendant, firstName, lastName, gender, fatherID, motherID, spouseID FROM persons";

            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            String personID = rs.getString(1);
            String descendant = rs.getString(2);
            String firstName = rs.getString(3);
            String lastName = rs.getString(4);
            String gender = rs.getString(5);
            String fatherID = rs.getString(6);
            String motherID = rs.getString(7);
            String spouseID = rs.getString(8);

            Person person = new Person(personID, descendant, firstName, lastName, gender, fatherID, motherID, spouseID);

        }catch (SQLException ex){
            throw new DataAccessException(ex.getMessage());
        }

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
