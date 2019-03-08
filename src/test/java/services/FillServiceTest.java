package services;

import database_access.Database;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import requests.FillRequest;
import responses.FillResponse;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;

public class FillServiceTest {

    class FillServiceTestException extends Exception {

    }

    Database db = new Database();
    FillService service = new FillService();

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

        FillRequest req = new FillRequest("/fill/user/4");

        //  Pass through the LoginService
        try {
            FillResponse res = service.serveResponse(req);

            assertEquals("Successfully added 31 persons and 91 events to the database.",res.message);
        } catch (Exception ex){
            ex.printStackTrace();
            //  Throwing any exception causes immediate failure
            fail();
        }

    }

    @Test(expected = FillServiceTestException.class)
    public void invalidUserName() throws Exception {
        //  Create an incomplete LoadResponse object
        FillRequest req = new FillRequest("/fill/not_a_real_user/4");

        //  Pass it through service
        try {
            FillResponse res = service.serveResponse(req);

        } catch (Exception ex){
            ex.printStackTrace();
            if (ex.getMessage().contains("Invalid parameter: username")){
                //  This is the error we hoped for
                throw new FillServiceTestException();
            }
            //  Throwing any other exception causes immediate failure
            fail();
        }
    }

    @Test(expected = FillServiceTestException.class)
    public void invalidGenerationsParam() throws Exception {
        //  Create an incomplete LoadResponse object
        FillRequest req = new FillRequest("/fill/user/-1");

        //  Pass it through service
        try {
            FillResponse res = service.serveResponse(req);

        } catch (Exception ex){
            ex.printStackTrace();
            if (ex.getMessage().contains("Invalid parameter: generations")){
                //  This is the error we hoped for
                throw new FillServiceTestException();
            }
            //  Throwing any other exception causes immediate failure
            fail();
        }
    }




}
