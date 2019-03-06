package database_access;

import domain.Person;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class PersonDaoTest {
    private Database db;
    private Person person;

    private class PersonDaoTestException extends Exception {

    }

    @Before
    public void setUp() throws Exception {
        // Create an empty database, with tables defined in db/dbschema.txt
        db = new Database();
        db.createTables();

        // Create a generic Person object. (Matches Person object in db/dbfiller.txt)
        person = new Person("100","user","first","last","f",null,null,null);

    }

    @After
    public void tearDown() throws Exception {
        //  Gets rid of any stray connections, in case of failures
        if (db.hasOpenConnection()) {
            db.closeConnection(false);
        }

        //  Clears any lingering content in the database.
        db.clearTables();
    }

    @Test
    public void getPersonByIDPass() throws Exception {
        //  We will compare the result we get with the person we expect
        Person result = null;

        //  First we fill the person table with some person data from db/dbfiller.txt
        db.fillDatabase();

        //  In db/dbfiller.txt, we see there is a Person which matches our prepared person instance.
        //  so we query a PersonDao object for that person.personID

        PersonDao pDao = new PersonDao(db.openConnection());
        result = pDao.getPersonByID(person.personID);
        db.closeConnection(true);
        //  We want the result to be the same as the generated person object
        assertEquals(result,person);
    }

    @Test
    public void getPersonByNonexistentIDFails() throws Exception {
        //  If a Person with the given ID is not found, the PersonDao returns a null pointer
        PersonDao personDao = new PersonDao(db.openConnection());
        Person result = personDao.getPersonByID("Give_Me_A_Fake_ID.mp3");
        assertNull(result);
    }

    @Test
    public void addPerson() throws Exception {
        //  This test adds a Person object to the database,
        //  then compares the person object it pulls back from the database
        //  to the original person object to check if insertion worked on all fields.

        Person result = null;

        //  Just to be sure its cleared.
        db.clearTables();

        PersonDao pDao = new PersonDao(db.openConnection());

        String personID = pDao.add(person);

        result = pDao.getPersonByID(person.personID);

        db.closeConnection(true);

        assertNotNull(result);
        assertEquals(result, person);

    }

    @Test
    public void addTwoPersonsIDunique() throws Exception {
        //  This tests adding multiple Person objects to see if the returned
        //  personID is truly unique

    }

    @Test(expected = PersonDaoTestException.class)
    public void addDuplicateFails() throws Exception {
        //  This test ensures ID remains unique
        PersonDao personDao = new PersonDao(db.openConnection());
        personDao.add(person);

        try{
            //  This second addition of the same Person should cause
            //  a DataAccessException to be thrown, since personId
            //  must remain unique.
            personDao.add(person);
            db.closeConnection(true);
        } catch (DataAccessException ex){
            db.closeConnection(true);
            throw new PersonDaoTestException();
        }
    }


    @Test
    public void clearFullTable() throws Exception{
        //  Create and fill the database
        db.fillDatabase();

        //  Create PersonDao and call clear
        PersonDao personDao = new PersonDao(db.openConnection());
        personDao.clear();
        db.closeConnection(true);

        //  Make sure that the persons table is now empty
        Connection conn = db.openConnection();
        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM persons");

        //  rs.next returns false if the query comes up empty
        assertFalse(rs.next());
        db.closeConnection(true);
    }

    @Test
    public void clearEmptyTable() throws Exception{
        //  Create PersonDao object, call clear on the empty database.
        PersonDao personDao = new PersonDao(db.openConnection());
        personDao.clear();
        db.closeConnection(true);

        //  If no exceptions were thrown, calling clear on an empty table worked.
    }


    @Test
    public void deletePersonByID() throws Exception {
        //  Put a Person object in the persons table
        db.fillDatabase();

        //  Delete the person with ID of "100" (matches person.personID)
        PersonDao personDao = new PersonDao(db.openConnection());
        personDao.deletePersonByID(person.personID);
        db.closeConnection(true);

        //  Ensure the person with ID of "100" is no longer there
        Connection conn = db.openConnection();
        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM persons WHERE personID = '100'");
        assertFalse(rs.next());
        db.closeConnection(true);

    }

    @Test
    public void deleteNonexistentIDPasses() throws Exception {
        //  We start with an empty database, so deleting anything
        //  should not cause a DataAccessError to be thrown
        PersonDao personDao = new PersonDao(db.openConnection());
        personDao.deletePersonByID(person.personID);
        db.closeConnection(true);
    }

    @Test
    public void deletePersonsByDescendant() throws Exception{
        //  Put a Person object in the persons table
        db.fillDatabase();

        //  Delete the person with descendant "user" (matches person.descendant)
        PersonDao personDao = new PersonDao(db.openConnection());
        personDao.deletePersonsByDescendant(person.descendant);
        db.closeConnection(true);

        //  Ensure the person with desce is no longer there
        Connection conn = db.openConnection();
        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM persons WHERE descendant = 'user'");
        assertFalse(rs.next());
        db.closeConnection(true);

    }

}