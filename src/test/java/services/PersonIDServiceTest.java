package services;

import database_access.Database;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import requests.PersonIDRequest;
import responses.PersonIDResponse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class PersonIDServiceTest {

    class PersonIDServiceException extends Exception{

    }

    Database db = new Database();
    PersonIDService service = new PersonIDService();

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
    public void serveResponse() throws Exception {
        //  Set up a valid PersonIDReqest object
        PersonIDRequest req = new PersonIDRequest("1101010","100");

        //  Pass it through the PersonIDService
        try {
            PersonIDResponse res = service.serveResponse(req);

            assertEquals("100", res.personID);

        } catch (Exception ex){
            ex.printStackTrace();
            //  Throwing any exception causes immediate failure
            fail();
        }
    }


    @Test(expected = PersonIDServiceException.class)
    public void invalidAuthToken() throws Exception {
        //  Set up bad authToken
        PersonIDRequest req = new PersonIDRequest("Nonsense auth","100");

        //  Pass it through the PersonIDService
        try {
            service.serveResponse(req);

        } catch (HttpRequestParseException ex){
            ex.printStackTrace();
            if (ex.getMessage().contains("Auth")){
                //  This is the error we hoped for
                throw new PersonIDServiceException();
            }
            //  Throwing any other exception causes immediate failure
            fail();
        }
    }

    @Test(expected = PersonIDServiceException.class)
    public void invalidPersonID() throws Exception {
        //  Set up bad personID
        PersonIDRequest req = new PersonIDRequest("1101010","Does not exist");

        //  Pass it through the PersonIDService
        try {
            service.serveResponse(req);

        } catch (HttpRequestParseException ex){
            if (ex.getMessage().contains("No such person found")){
                //  This is the error we hoped for
                throw new PersonIDServiceException();
            }
            //  Throwing any other exception causes immediate failure
            fail();
        }
    }

    @Test(expected = PersonIDServiceException.class)
    public void notUsersPersonID() throws Exception {
        //  Set up bad personID
        PersonIDRequest req = new PersonIDRequest("1101010","200");

        //  Pass it through the PersonIDService
        try {
            service.serveResponse(req);

        } catch (HttpRequestParseException ex){
            ex.printStackTrace();
            if (ex.getMessage().contains("Person does not belong to current User")){
                //  This is the error we hoped for
                throw new PersonIDServiceException();
            }
            //  Throwing any other exception causes immediate failure
            fail();
        }
    }
}
