package services;

import database_access.AuthTokenDao;
import database_access.DataAccessException;
import database_access.Database;
import database_access.PersonDao;
import domain.Person;
import handlers.HttpExceptions.HttpAuthorizationException;
import handlers.HttpExceptions.HttpBadRequestException;
import handlers.HttpExceptions.HttpInternalServerError;
import requests.PersonIDRequest;
import responses.PersonIDResponse;
import responses.PersonResponse;

import javax.xml.ws.http.HTTPException;
import java.sql.Connection;
import java.util.logging.Logger;

/**
 * Returns the single Person object with the specified ID.
 */
public class PersonIDService implements Service<PersonIDRequest, PersonIDResponse> {

    private static Logger logger = Logger.getLogger("PersonIDService");

    /**
     * Returns a responses instance with the info of a person (or persons)
     * @param req a valid PersonIDRequest object
     * @return res a valid PersonIDResponse object if successful, a failing Response object if services fails
     */
    @Override
    public PersonIDResponse serveResponse(PersonIDRequest req) throws HttpAuthorizationException,HttpInternalServerError,HttpBadRequestException{
        //  Parse request
        if (req.getAuthToken() == null ||
            req.getPersonID()  == null){
            throw new HttpBadRequestException("Invalid request parameters");
        }

        Database db = new Database();

        try{
            Connection conn = db.openConnection();

            //  Get username from authString
            AuthTokenDao authTokenDao = new AuthTokenDao(conn);
            String username = authTokenDao.getUsernameByAuthToken(req.getAuthToken());
            if (null == username){
                throw new HttpBadRequestException("Invalid Authentication");
            }


            //  Get requested Person from personID
            Person person = new PersonDao(conn).getPersonByID(req.getPersonID());
            if (null == person){
                throw new HttpBadRequestException("No such person found");
            }

            if( !person.descendant.equals(username)) {
                throw new HttpBadRequestException("Person does not belong to current User");
            }

            //  It worked!
            db.closeConnection(true);
            return new PersonIDResponse(person);

        } catch (DataAccessException ex){

            throw new HttpInternalServerError(ex.getMessage());

        } finally {

            db.hardClose();
        }

    }
}
