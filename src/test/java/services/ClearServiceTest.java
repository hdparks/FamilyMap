package services;


import database_access.Database;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import requests.ClearRequest;
import responses.ClearResponse;
import static org.junit.Assert.assertEquals;


import static org.junit.Assert.fail;

public class ClearServiceTest {

    Database db = new Database();
    ClearService service = new ClearService();

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
        //  Set up valid ClearRequest object
        ClearRequest req = new ClearRequest();

        //  Pass through the service
        try {
            ClearResponse res = service.serveResponse(req);

            assertEquals(res.message,"Clear succeeded.");
        } catch (Exception ex){
            ex.printStackTrace();
            //  Throwing any exception causes immediate failure
            fail();
        }
    }

    @Test
    public void clearEmptyTable() throws Exception {
        //  Clear the database
        db.clearTables();

        ClearRequest req = new ClearRequest();

        //  Pass through service
        try {
            ClearResponse res = service.serveResponse(req);

            assertEquals(res.message,"Clear succeeded.");
        } catch (Exception ex){
            ex.printStackTrace();
            //  Throwing any exception causes immediate failure
            fail();
        }
    }



}
