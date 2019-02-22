package database_access;

import domain.Person;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class PersonDaoTest {
    private Database db;

    private Person person;

    @Before
    public void setUp() throws Exception {
        // Create an empty table
        db = new Database();
        db.createTables();

        person = new Person("1","user","first","last","f",null,null,null);

    }

    @After
    public void tearDown() throws Exception {
        if (db.hasOpenConnection()) {
            db.closeConnection(false);
        }
        db.clearTables();
    }

    @Test
    public void getPersonByID() throws Exception {
        //  We will compare the result we get with the person we expect
        Person result = null;

        //  First we manually insert a person with the same data.
        Connection conn = db.openConnection();
        conn.prepareStatement("INSERT INTO persons (personID, descendant, firstName, lastName, gender, fatherID, motherID, spouseID)" +
                "VALUES ('001','user','")
        db.closeConnection(true);

    }

    @Test
    public void clear() {


    }

    /**
     * This tests the insertion of a Person object into the database.
     * @throws Exception
     */
    @Test
    public void addPass() throws Exception {
        //  We will compare this resulting person object from the database
        //  to our original person object to see if insertion worked.
        Person result = null;
        db.clearTables();

        Connection conn = db.openConnection();
        PersonDao pDao = new PersonDao(conn);

        pDao.add(person);

        result = pDao.getPersonByID(person.personID);

        db.closeConnection(true);

        assertNotNull(result);
        assertEquals(result, person);

    }


    @Test
    public void addFail()  {

    }

    @Test
    public void deletePersonByID() {
    }

    @Test
    public void getPersonListByUser() {
    }
}