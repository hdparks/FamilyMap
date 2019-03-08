package services;

import database_access.Database;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import requests.LoginRequest;
import responses.LoginResponse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class LoginServiceTest {

    class LoginServiceException extends Exception{

    }

    Database db = new Database();
    LoginService service = new LoginService();

    @Before
    public void setUp() throws Exception{
        db.clearTables();
        db.fillDatabase();
        db.closeConnection(true);
    }

    @After
    public void tearDown() throws Exception{
        db.clearTables();
        db.closeConnection(false);
    }

    @Test
    public void serveResponse() throws  Exception {
        //  Set up valid LoginRequest object
        LoginRequest req = new LoginRequest("user","pass");

        //  Pass through the LoginService
        try {
            LoginResponse res = service.serveResponse(req);
            assertEquals(req.getUserName(), res.userName);
        } catch (Exception ex){
            ex.printStackTrace();
            //  Throwing any exception causes immediate failure
            fail();
        }

    }


    @Test(expected = LoginServiceException.class)
    public void missingValue() throws Exception {
        //  Set up bad request
        LoginRequest req = new LoginRequest("Nonsense",null);

        //  Pass it through the Service
        try {
            service.serveResponse(req);

        } catch (HttpRequestParseException ex){
//            ex.printStackTrace();
            if (ex.getMessage().contains("Invalid parameters")){
                //  This is the error we hoped for
                throw new LoginServiceException();
            }
            //  Throwing any other exception causes immediate failure
            fail();
        }
    }

    @Test(expected = LoginServiceException.class)
    public void incorrectPassword() throws Exception{
        //  Set up bad request
        LoginRequest req = new LoginRequest("user","not a password");

        //  Pass it through the Service
        try {
            service.serveResponse(req);

        } catch (HttpRequestParseException ex){
            ex.printStackTrace();
            if (ex.getMessage().contains("userName/password not recognized")){
                //  This is the error we hoped for
                throw new LoginServiceException();
            }
            //  Throwing any other exception causes immediate failure
            fail();
        }
    }
}

