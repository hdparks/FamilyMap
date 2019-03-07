package database_access;

import domain.AuthToken;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.sql.Connection;
import java.sql.ResultSet;
import static org.junit.Assert.*;

public class AuthTokenDaoTest {

    class AuthTokenDaoTestException extends Exception{

    }

    Database db = new Database();
    AuthToken token = new AuthToken("authToken","userName");

    @Before
    public void setUp() throws Exception{
        db.clearTables();
    }

    @After
    public void tearDown() throws Exception{
        db.closeConnection(false);
        db.clearTables();
    }

    @Test
    public void add() throws Exception{
        //  This function tests adding an authToken to an empty database
        Connection conn = db.openConnection();
        AuthTokenDao authTokenDao = new AuthTokenDao(conn);
        authTokenDao.add(token);
        db.closeConnection(true);

        conn = db.openConnection();
        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM authTokens");
        boolean hasNext = rs.next();
        assert(hasNext);
        AuthToken actual = new AuthToken(rs.getString(1),rs.getString(2));

        assertEquals(token, actual);

    }

    @Test(expected = AuthTokenDaoTestException.class)
    public void addDuplicateFails() throws Exception {

        Connection conn = db.openConnection();
        AuthTokenDao authTokenDao = new AuthTokenDao(conn);

        authTokenDao.add(token);
        try{
            authTokenDao.add(token);
        } catch (DataAccessException ex){
            //  This is the expected behavior
            throw new AuthTokenDaoTestException();
        }

    }


    @Test
    public void clear() throws Exception {
        Connection conn = db.openConnection();
        AuthTokenDao authTokenDao = new AuthTokenDao(conn);
        authTokenDao.add(token);

        //  Now, clear it
        authTokenDao.clear();

        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM authTokens");

        assertFalse(rs.next());
    }

    @Test
    public void clearEmptyTable() throws Exception {
        Connection conn = db.openConnection();
        AuthTokenDao authTokenDao = new AuthTokenDao(conn);
        try {
            authTokenDao.clear();
        } catch (Exception ex){
            //  If anything goes wrong in clearing an empty table, fail.
            fail();
        }
        //  If no exceptions are thrown, pass
    }

    @Test
    public void getUsernameByAuthToken() throws Exception {
        Connection conn = db.openConnection();
        AuthTokenDao authTokenDao = new AuthTokenDao(conn);
        //  Put generic token in database
        authTokenDao.add(token);

        String actual = authTokenDao.getUsernameByAuthToken(token.authToken);

        assertEquals(actual, token.userName);
    }

    @Test
    public void getUsernameByNonexistentAuthToken() throws Exception {
        Connection conn = db.openConnection();
        AuthTokenDao authTokenDao = new AuthTokenDao(conn);
        //  Since the database is wiped each time, we query for any token
        String actual = authTokenDao.getUsernameByAuthToken(token.authToken);

        assertNull(actual);
    }
}