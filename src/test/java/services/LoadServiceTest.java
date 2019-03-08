package services;

import com.google.gson.Gson;
import database_access.Database;
import domain.Event;
import domain.User;
import handlers.ExchangeUtilities;
import handlers.JSONUtilities;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import requests.LoadRequest;
import responses.LoadResponse;


import java.io.File;
import java.io.FileInputStream;
import java.util.concurrent.ExecutionException;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;

public class LoadServiceTest {

    class LoadServiceException extends Exception{

    }


    public LoadRequest createLoadRequest() throws Exception {
        File file = new File("familymapserver"+File.separator+"json"+File.separator+"example.json");
        FileInputStream is = new FileInputStream(file);
        return JSONUtilities.createRequestInstance(is,LoadRequest.class);

    }

    Database db = new Database();
    LoadService service = new LoadService();


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

        LoadRequest req = createLoadRequest();

        //  Pass through the LoginService
        try {
            LoadResponse res = service.serveResponse(req);

            assertEquals("Successfully added 1 users, 3 persons, and 2 events to the database.",((LoadResponse) res).message);
        } catch (Exception ex){
            ex.printStackTrace();
            //  Throwing any exception causes immediate failure
            fail();
        }

    }

    @Test(expected = LoadServiceException.class)
    public void missingRequestData() throws Exception {
        //  Create an incomplete LoadResponse object
        LoadRequest req = createLoadRequest();
        req.setEvents(null);

        //  Pass it through service
        try {
            LoadResponse res = service.serveResponse(req);

        } catch (Exception ex){
//            ex.printStackTrace();
            if (ex.getMessage().contains("Invalid parameters")){
                //  This is the error we hoped for
                throw new LoadServiceException();
            }
            //  Throwing any other exception causes immediate failure
            fail();
        }
    }

    @Test(expected = LoadServiceException.class)
    public void invalidRequestData() throws Exception {
        //  Create an incomplete LoadResponse object
        LoadRequest req = createLoadRequest();
        req.getUsers()[0] = new User("user","pass","email",null,null,"f");

        //  Pass it through service
        try {
            LoadResponse res = service.serveResponse(req);

        } catch (Exception ex){
            ex.printStackTrace();
            if (ex.getMessage().contains("User")){
                //  This is the error we hoped for
                throw new LoadServiceException();
            }
            //  Throwing any other exception causes immediate failure
            fail();
        }
    }


}
