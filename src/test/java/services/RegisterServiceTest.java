package services;

import database_access.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import requests.RegisterRequest;
import responses.RegisterResponse;

import java.sql.Connection;
import java.sql.ResultSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class RegisterServiceTest {

    Database db = new Database();

    @Before
    public void setUp() throws Exception{
        db.clearTables();
    }

    @After
    public void tearDown() throws Exception{
        db.closeConnection(false);
    }

    public int countResultSet(ResultSet rs) throws Exception{
        int i = 0;
        while(rs.next()){
            i++;
        }
        return i;
    }

    @Test
    public void serveResponse() throws Exception {
        //  Set up a valid RegisterRequest object
        RegisterRequest req = new RegisterRequest(
                "username","password",
                "email","firstName",
                "lastName","m");


        RegisterService service = new RegisterService();

        try{
            service.serveResponse(req);
        } catch (Exception ex){
            //  Should not throw an exception for a valid request.
            fail();
        }

        //  Ensure that the User, Person, Event, and AuthTokens
        //  Were created: 1 User, 31 Persons, 91 Events, 1 AuthTokens
        Connection conn = db.openConnection();
        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM users");
        int userCount = countResultSet(rs);

        rs = conn.createStatement().executeQuery("SELECT * FROM persons");
        int personCount = countResultSet(rs);

        rs = conn.createStatement().executeQuery("SELECT * FROM events");
        int eventCount = countResultSet(rs);

        rs = conn.createStatement().executeQuery("SELECT * FROM authTokens");
        int authCount = countResultSet(rs);

        assertEquals(userCount,1);
        assertEquals(personCount, 31);
        assertEquals(eventCount,91);
        assertEquals(authCount, 1);
    }
}