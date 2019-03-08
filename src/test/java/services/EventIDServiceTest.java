package services;

import database_access.Database;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import requests.EventIDRequest;
import responses.EventIDResponse;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;

public class EventIDServiceTest {

    class EventIDServiceTestException extends Exception{

    }

    Database db = new Database();
    EventIDService service = new EventIDService();

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

        EventIDRequest req = new EventIDRequest("1101010","1");

        //  Pass through the LoginService
        try {
            EventIDResponse res = service.serveResponse(req);

            assertEquals("1",res.getEventID());
        } catch (Exception ex){
            ex.printStackTrace();
            //  Throwing any exception causes immediate failure
            fail();
        }

    }

    @Test(expected = EventIDServiceTestException.class)
    public void invalidAuthToken() throws Exception {
        //  Create an incomplete EventIDRequest object
        EventIDRequest req = new EventIDRequest("/uhhh/not_a_real_authToken","1");

        //  Pass it through service
        try {
            EventIDResponse res = service.serveResponse(req);

        } catch (Exception ex){
            ex.printStackTrace();
            if (ex.getMessage().contains("Authentication failed")){
                //  This is the error we hoped for
                throw new EventIDServiceTestException();
            }
            //  Throwing any other exception causes immediate failure
            fail();
        }
    }

    @Test(expected = EventIDServiceTestException.class)
    public void invalidEventID() throws Exception {
        //  Create an incomplete EventIDRequest object
        EventIDRequest req = new EventIDRequest("1101010","4");

        //  Pass it through service
        try {
            EventIDResponse res = service.serveResponse(req);

        } catch (Exception ex){
            ex.printStackTrace();
            if (ex.getMessage().contains("Invalid parameters: eventID")){
                //  This is the error we hoped for
                throw new EventIDServiceTestException();
            }
            //  Throwing any other exception causes immediate failure
            fail();
        }
    }

    @Test(expected = EventIDServiceTestException.class)
    public void eventDoesNotBelongToUser() throws Exception {
        //  Create an incomplete EventIDRequest object
        EventIDRequest req = new EventIDRequest("1101010","2");

        //  Pass it through service
        try {
            EventIDResponse res = service.serveResponse(req);

        } catch (Exception ex){
            ex.printStackTrace();
            if (ex.getMessage().contains("Event does not belong to current user")){
                //  This is the error we hoped for
                throw new EventIDServiceTestException();
            }
            //  Throwing any other exception causes immediate failure
            fail();
        }
    }
}
