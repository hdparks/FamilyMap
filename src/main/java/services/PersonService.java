package services;

import database_access.AuthTokenDao;
import database_access.DataAccessException;
import database_access.Database;
import database_access.PersonDao;
import domain.Person;
import handlers.HttpExceptions.HttpAuthorizationException;
import handlers.HttpExceptions.HttpBadRequestException;
import handlers.HttpExceptions.HttpInternalServerError;
import requests.PersonRequest;
import responses.PersonResponse;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.util.logging.Logger;

/**
 * Returns ALL family members of the current user.
 *
 * The current user is determined from the provided auth token.
 */
public class PersonService implements Service<PersonRequest, PersonResponse> {

    private static Logger logger = Logger.getLogger("PersonService");

    /**
     * Returns a responses instance with the info of a person (or persons)
     * @param req a valid PersonRequest object
     * @return res a valid PersonResponse object if successful, a failing Response object if services fails
     */
    @Override
    public PersonResponse serveResponse(PersonRequest req) throws HttpInternalServerError, HttpBadRequestException, HttpAuthorizationException {
        //  Parse request
        if (req.getAuthToken() == null){
            throw new HttpBadRequestException("Invalid parameters");
        }

        //  Spin up database connection
        Database db = new Database();

        try {

            //  Authenticate
            Connection conn = db.openConnection();
            String authString = req.getAuthToken();
            String username = new AuthTokenDao(conn).getUsernameByAuthToken(authString);

            //  If username is null, authToken did not match any user
            if (null == username) {
                throw new HttpAuthorizationException("Authentication token not recognized");
            }

            //  Get person list
            Person[] personList = new PersonDao(conn).getPersonListByUser(username);
            db.closeConnection(true);
            return new PersonResponse(personList);

        } catch (DataAccessException ex){
            throw new HttpInternalServerError(ex.getMessage());

        } finally {
             db.hardClose();
        }
    }
}
