package services;

import database_access.*;
import domain.User;
import handlers.HttpExceptions.HttpBadRequestException;
import handlers.HttpExceptions.HttpInternalServerError;
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

    class RegisterServiceException extends Exception{

    }

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
        RegisterResponse res;
        try{
            res = service.serveResponse(req);
        } catch (Exception ex){
            //  Should not throw an exception for a valid request.
            fail();
            return;
        }

        assert(res.success);
        assert(res.userName.equals("username"));

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

    @Test(expected = RegisterServiceException.class)
    public void serveResponseMissingProperFieldsFails() throws Exception {
        //  Set up an invalid RegisterRequest object
        RegisterRequest req = new RegisterRequest("username","password","email",null,null,"f");

        RegisterService service = new RegisterService();

        try{
            service.serveResponse(req);
        } catch (HttpBadRequestException ex){
            //  This is the exception we expect it to throw
            throw new RegisterServiceException();
        }
    }


    @Test(expected = RegisterServiceException.class)
    public void serveResponseIncorrectUserGender() throws Exception {
        //  Set up an invalid RegisterRequest object
        RegisterRequest req = new RegisterRequest("username","password","email","firstName","lastName","something new");

        RegisterService service = new RegisterService();

        try{
            service.serveResponse(req);
        } catch (HttpBadRequestException ex){

            if (ex.getMessage().contains("Gender")){
                //  This is the exception we expect it to throw
                throw new RegisterServiceException();
            }

        }
    }

    @Test(expected = RegisterServiceException.class)
    public void userNameAlreadyTaken() throws Exception{
        //  Set up a valid RegisterRequest object
        RegisterRequest req = new RegisterRequest("username","password","email","firstName","lastName","f");

        RegisterService service = new RegisterService();

        //  Put a User in the database
        User user = new User("username","password","email","firstName","lastName","f","personID");
        Connection conn = db.openConnection();
        UserDao userDao = new UserDao(conn);
        userDao.add(user);
        db.closeConnection(true );


        //  Try adding the same user into the database.
        try{
            service.serveResponse(req);
        } catch (HttpInternalServerError ex){

            if (ex.getMessage().contains("User")){
                //  This is the exception we expect it to throw
                throw new RegisterServiceException();
            }
        }
    }
}