package services;

import database_access.Database;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import requests.EventRequest;
import requests.FillRequest;
import responses.EventResponse;
import responses.FillResponse;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;

public class EventServiceTest {

    class EventServiceTestException extends Exception {

    }

    Database db = new Database();
    EventService service = new EventService();

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
        //  Set up valid EventRequest object

        EventRequest req = new EventRequest("1101010");

        //  Pass through the service
        try {
            EventResponse res = service.serveResponse(req);

            assertEquals(res.getEvents().length,1);
        } catch (Exception ex){
            ex.printStackTrace();
            //  Throwing any exception causes immediate failure
            fail();
        }

    }


    @Test(expected = EventServiceTestException.class)
    public void invalidUserName() throws Exception {
        //  Create an incomplete EventResponse object
        EventRequest req = new EventRequest("/uhhh/not_a_real_authToken");

        //  Pass it through service
        try {
            EventResponse res = service.serveResponse(req);

        } catch (Exception ex){
            ex.printStackTrace();
            if (ex.getMessage().contains("Authentication failed")){
                //  This is the error we hoped for
                throw new EventServiceTestException();
            }
            //  Throwing any other exception causes immediate failure
            fail();
        }
    }

}
