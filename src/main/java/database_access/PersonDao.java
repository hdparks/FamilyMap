package database_access;

import domain.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * a class for interfacing with Person data
 */
public class PersonDao {

    private static Logger logger = Logger.getLogger("PersonDao");

    Connection conn;


    /**
     * Connects a PersonDAO object to a Database object
     * @param conn a database connection
     */
    public PersonDao(Connection conn){
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
            logger.log(Level.SEVERE,ex.getMessage());
            throw new DataAccessException("Error occurred while clearing persons table");
        }
    }


    /**
     * Adds a Person to the database.
     * Adds a personID to the supplied person and returns it.
     * @param person the Person to add
     * @throws DataAccessException if operation fails
     */
    public Person add(Person person) throws DataAccessException{
        //  If the Person object has no personID, add one
        if (person.personID == null){
            person.personID = UUID.randomUUID().toString();
        }


        String sql = "INSERT INTO persons (descendant, firstName, lastName, gender, father, mother, spouse, personID) " +
                "VALUES (?,?,?,?,?,?,?,?)";


        try {

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, person.descendant);
            stmt.setString(2, person.firstName);
            stmt.setString(3, person.lastName);
            stmt.setString(4, person.gender);
            stmt.setString(5, person.father);
            stmt.setString(6, person.mother);
            stmt.setString(7, person.spouse);
            stmt.setString(8, person.personID);

            stmt.executeUpdate();

            return person;

        } catch(SQLException ex){
            logger.log(Level.SEVERE,ex.getMessage());
            throw new DataAccessException("Error while adding Person data");
        }

    }

    public void updateRelationships(Person child, Person mother, Person father) throws DataAccessException{
        try {
            String childUpdate = "UPDATE persons SET mother = ?, father = ? WHERE personID = ?";
            PreparedStatement stmt = conn.prepareStatement(childUpdate);
            stmt.setString(1,mother.personID);
            stmt.setString(2,father.personID);
            stmt.setString(3,child.personID);
            stmt.executeUpdate();

            String motherUpdate = "UPDATE persons SET spouse = ? WHERE personID = ?";
            stmt = conn.prepareStatement(motherUpdate);
            stmt.setString(1,father.personID);
            stmt.setString(2,mother.personID);
            stmt.executeUpdate();

            String fatherUpdate = "UPDATE persons SET spouse = ? WHERE personID = ?";
            stmt = conn.prepareStatement(fatherUpdate);
            stmt.setString(1,mother.personID);
            stmt.setString(2,father.personID);
            stmt.executeUpdate();

        } catch (SQLException ex){
            logger.severe(ex.getMessage());
            throw new DataAccessException("Error while updating family relationships");
        }
    }

    /**
     * Deletes the given Person
     * @param personID the Id of the Person to deleteUserByUsername
     * @throws DataAccessException if the operation fails, ie. the Person is not found
     */
    public void deletePersonByID(String personID) throws DataAccessException{
        try{
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM persons WHERE personID = ?");
            stmt.setString(1,personID);
            stmt.executeUpdate();
        } catch (SQLException ex){
            logger.log(Level.SEVERE,ex.getMessage());
            throw new DataAccessException("Error deleting Person data");
        }
    }

    public void deletePersonsByDescendant(String descendant) throws DataAccessException{
        try{
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM persons WHERE descendant = ?");
            stmt.setString(1,descendant);
            stmt.executeUpdate();
        } catch (SQLException ex){
            logger.log(Level.SEVERE,ex.getMessage());
            throw new DataAccessException("Error deleting Persons");
        }
    }

    /**
     * Returns an instance of domain.Person representing the person with the given ID.
     * @param id the personID of the person in question
     * @return Person a Person object
     * @throws DataAccessException if the operation fails
     */
    public Person getPersonByID(String id) throws DataAccessException {
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
            logger.log(Level.SEVERE,ex.getMessage());
            throw new DataAccessException("Error occurred in PersonDao getPersonByID");
        }

        return person;
    }

    /**
     * Returns a List of Person objects corresponding to every person whose descendant is {username}
     * @param username the username of the descendant of each Person in the list
     * @return a List of Person objects
     * @throws DataAccessException if the operation fails
     */
    public Person[] getPersonListByUser(String username) throws DataAccessException{

        List<Person> list = new ArrayList<>();
        String sql = "SELECT personID, descendant, firstName, lastName, gender, father, mother, spouse FROM persons";

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

            Person[] personArray = new Person[list.size()];
            return list.toArray(personArray);

        } catch (SQLException ex){
            logger.log(Level.SEVERE,ex.getMessage());
            throw new DataAccessException("Error encountered while finding list of Persons");
        }

    }
}
