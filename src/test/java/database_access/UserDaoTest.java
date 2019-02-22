package database_access;

import domain.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;

import static org.junit.Assert.*;

public class UserDaoTest {
    private Database db;
    private User user;

    private class UserDaoTestException extends Exception{

    }

    @Before
    public void setUp() throws Exception {
        //  Create an empty database, as defined in db/dbschema.txt
        db = new Database();
        db.createTables();

        //  Create a generic User object. (Matches User object in db/dbfiller.txt)
        user = new User("user","pass","email","first","last","f","100");
    }

    @After
    public void tearDown() throws Exception {
        //  Get rid of any lingering connections, in case of failure
        if (db.hasOpenConnection()){
            db.closeConnection(false);
        }

        //  Clears any lingering content in the database
        db.clearTables();
    }

    @Test
    public void addUser() throws Exception {

        User result = null;

        //  Just to be sure the database is cleared
        db.clearTables();

        //  Create new userDao
        UserDao userDao = new UserDao(db.openConnection());
        //  Add a generic user
        userDao.add(user);

        //  Get the newly added user, make sure it matches the original
        result = userDao.getUserByName(user.userName);

        //  Close the userDao connection
        db.closeConnection(true);

        assertNotNull(result);
        assertEquals(user, result);

    }

    @Test(expected = UserDaoTestException.class)
    public void addDuplicateFails() throws Exception {
        //  This test ensures that userName remains unique across users
        UserDao userDao = new UserDao(db.openConnection());
        userDao.add(user);
        try{
            //  Attempting to add the same user twice should produce a
            //  DatabaseAccessException, since userNames must be unique
            userDao.add(user);
        } catch (DataAccessException ex){
            throw new UserDaoTestException();
        }
    }

    @Test
    public void getUserByUsernamePass() throws Exception {
        //  Populate the users table with the data found in our user object
        db.fillDatabase();

        //  The users table now matches our user object, so we query for
        //  user.userName
        UserDao userDao = new UserDao(db.openConnection());
        User result = userDao.getUserByName(user.userName);
        db.closeConnection(true);

        //  Make sure the two match
        assertEquals(user, result);
    }

    @Test void getUserByNonexistentUsernameFails() throws Exception {
        //  Database starts empty, so any query should return a null User value

        UserDao userDao = new UserDao(db.openConnection());
        User result = userDao.getUserByName(user.userName);
        db.closeConnection(true);

        //  Ensure that result is null
        assertNull(result);
    }

    @Test
    public void clearFullTable() throws Exception {
        //  Fill the users table
        db.fillDatabase();

        //  Call clear on UserDao
        UserDao userDao = new UserDao(db.openConnection());
        userDao.clear();
        db.closeConnection(true);

        //  Now we make sure that users table is empty
        Connection conn = db.openConnection();
        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM users");

        //  rs.next returns false if the query comes up empty, which it should
        assertFalse(rs.next());
        db.closeConnection(true);

    }

    @Test
    public void clearEmptyTable() throws Exception {
        // Call clear on the empty database
        UserDao userDao = new UserDao(db.openConnection());
        userDao.clear();
        db.closeConnection(true);

        //  If no exceptions were thrown, then
        //  Calling clear on the empty database worked as it should.

    }

    @Test
    public void deleteUserByUsername() throws Exception {
        //  Populate the users table with a copy of user
        db.fillDatabase();

        //  Delete the person with the userName "user"
        UserDao userDao = new UserDao(db.openConnection());
        userDao.deleteUserByUsername(user.userName);
        db.closeConnection(true);

        //  Ensure the person is gone
        Connection conn = db.openConnection();
        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM users WHERE userName = 'user'");
        assertFalse(rs.next());
        db.closeConnection(true);
    }

    @Test
    public void deleteNonexistentUsername() throws Exception{
        //  Since we start with an empty database, no userName exists
        //  Deleting any username should not cause a DataAccessError to be thrown
        UserDao userDao = new UserDao(db.openConnection());
        userDao.deleteUserByUsername(user.userName);
    }
}