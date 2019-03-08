package services;

import database_access.AuthTokenDao;
import database_access.DataAccessException;
import database_access.Database;
import database_access.PersonDao;
import domain.Person;
import requests.PersonRequest;
import responses.PersonResponse;

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
    public PersonResponse serveResponse(PersonRequest req) throws DataAccessException, HttpRequestParseException {
        //  Parse request
        if (req.getAuthToken() == null){
            throw new HttpRequestParseException("Invalid parameters");
        }

        //  Spin up database connection
        Database db = new Database();

        try{
            Connection conn = db.openConnection();

            //  Get authString from request
            String authString = req.getAuthToken();

            //  Get username associated with the authString
            String username = new AuthTokenDao(conn).getUsernameByAuthToken(authString);

            //  If username is null, authToken did not match
            if (null == username){
                throw new HttpRequestParseException("Authentication token not recognized");
            }

            Person[] personList = new PersonDao(conn).getPersonListByUser(username);

            //  Save changes
            db.closeConnection(true);

            //  Create PersonResponse
            return new PersonResponse(personList);

        } catch (DataAccessException | HttpRequestParseException ex) {
            //  Roll back changes
            db.closeConnection(false);

            logger.severe(ex.getMessage());

            throw ex;

        } finally {
            //  Default to rolling back
            db.closeConnection(false);
        }
    }
}
