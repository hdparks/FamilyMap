package database_access;

import domain.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * a class for interfacing with Person data
 */
public class PersonDao {

    Connection conn;

    /**
     * Connects a PersonDAO object to a Database object
     * @param conn a database connection
     */
    PersonDao(Connection conn){
        this.conn = conn;
    }

    /**
     * Clears all Person data
     * @throws DataAccessException if the operation fails
     */
    public void clear() throws DataAccessException{
        try(PreparedStatement stmt = conn.prepareStatement("DELETE FROM persons")) {
            stmt.executeUpdate();
        } catch (SQLException ex){
            throw new DataAccessException("Error occurred while clearing table from database");
        }
    }


    /**
     * Adds a Person to the database. Generates and returns a new personID.
     * @param person the Person to add
     * @throws DataAccessException if operation fails
     */
    void add(Person person) throws DataAccessException{
        String sql = "INSERT INTO persons (personID, descendant, firstName, lastName, gender, fatherID, motherID, spouseID)" +
                "VALUES (?,?,?,?,?,?,?,?)";
        try{

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, person.personID);
            stmt.setString(2, person.descendant);
            stmt.setString(3, person.firstName);
            stmt.setString(4, person.lastName);
            stmt.setString(5, person.gender);
            stmt.setString(6, person.fatherID);
            stmt.setString(7, person.motherID);
            stmt.setString(8, person.spouseID);

            stmt.executeUpdate();
            stmt.close();
         } catch(SQLException ex){
            throw new DataAccessException("Error encountered while adding Person into the database:\n"+ex.getMessage());
        }

    }

    /**
     * Deletes the given Person
     * @param personID the Id of the Person to deleteUserByUsername
     * @throws DataAccessException if the operation fails, ie. the Person is not found
     */
    void deletePersonByID(String personID) throws DataAccessException{
        try{
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM persons WHERE personID = ?");
            stmt.setString(1,personID);
            stmt.executeUpdate();
        } catch (SQLException ex){
            throw new DataAccessException("Error while deleting Person:\n"+ex.getMessage());
        }
    }

    /**
     * Returns an instance of domain.Person representing the person with the given ID.
     * @param id the personID of the person in question
     * @return Person a Person object
     * @throws DataAccessException if the operation fails
     */
    Person getPersonByID(String id) throws DataAccessException {
        Person person = null;
        String sql = "SELECT * FROM persons WHERE PersonID = ?";
        try {

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1,id);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                String personID = rs.getString(1);
                String descendant = rs.getString(2);
                String firstName = rs.getString(3);
                String lastName = rs.getString(4);
                String gender = rs.getString(5);
                String fatherID = rs.getString(6);
                String motherID = rs.getString(7);
                String spouseID = rs.getString(8);

                person = new Person(personID, descendant, firstName, lastName, gender, fatherID, motherID, spouseID);
            }
            //  If result set is empty, there is no such person
            //  So we return a null Person object
        } catch (SQLException ex){
            throw new DataAccessException("Error occurred in PersonDao getPersonByID\n"+ex.getMessage());
        }

        return person;
    }

    /**
     * Returns a List of Person objects corresponding to every person whose descendant is {username}
     * @param username the username of the descendant of each Person in the list
     * @return a List of Person objects
     * @throws DataAccessException if the operation fails
     */
    List<Person> getPersonListByUser(String username) throws DataAccessException{

        List<Person> list = new ArrayList<Person>();
        String sql = "SELECT personID, descendant, firstName, lastName, gender, fatherID, motherID, spouseID FROM persons";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()){

            while (rs.next()){

                String personID = rs.getString(1);
                String descendant = rs.getString(2);
                String firstName = rs.getString(3);
                String lastName = rs.getString(4);
                String gender = rs.getString(5);
                String fatherID = rs.getString(6);
                String motherID = rs.getString(7);
                String spouseID = rs.getString(8);

                list.add(new Person(personID, descendant, firstName, lastName, gender, fatherID, motherID, spouseID));
            }

            return list;

        } catch (SQLException ex){
            ex.printStackTrace();
            throw new DataAccessException("Error encountered while finding Person list");
        }

    }
}
