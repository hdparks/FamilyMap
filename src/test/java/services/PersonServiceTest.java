package services;

import database_access.AuthTokenDao;
import database_access.DataAccessException;
import database_access.Database;
import domain.AuthToken;
import domain.Person;
import handlers.HttpExceptions.HttpAuthorizationException;
import handlers.HttpExceptions.HttpBadRequestException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import requests.PersonRequest;
import requests.RegisterRequest;
import responses.PersonResponse;
import responses.RegisterResponse;

import java.sql.Connection;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class PersonServiceTest {

    class PersonServiceException extends Exception{

    }

    Database db = new Database();
    PersonService service = new PersonService();

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
        //  Set up a valid PersonRequest object
        PersonRequest req = new PersonRequest("1101010");

        //  Send through the Person Service
        PersonResponse res;

        try{

            res = service.serveResponse(req);
            assertNotNull(res.data);
            assert(res.success);

        } catch (Exception ex){
            //  Should not throw any exceptions
            fail();
            return;
        }
    }

    @Test(expected = PersonServiceException.class)
    public void invalidAuthToken() throws Exception {
        //  set up a PersonRequest object with an invalid authToken
        PersonRequest req = new PersonRequest("Nonsense token");

        //  Send through Person Service
        try {
            PersonResponse res = service.serveResponse(req);

        } catch (HttpAuthorizationException ex){
            if (ex.getMessage().contains("Auth")){
                //  This is the exception we expect for faulty authToken
                throw new PersonServiceException();
            }
        }
    }
}
