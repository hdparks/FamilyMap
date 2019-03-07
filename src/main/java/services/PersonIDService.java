package services;

import database_access.AuthTokenDao;
import database_access.DataAccessException;
import database_access.Database;
import database_access.PersonDao;
import domain.Person;
import requests.PersonIDRequest;
import responses.PersonIDResponse;
import responses.PersonResponse;

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
    public PersonIDResponse serveResponse(PersonIDRequest req) throws DataAccessException, HttpRequestParseException{
        //  Parse request
        if (req.getAuthToken() == null ||
            req.getPersonID()  == null){
            throw new HttpRequestParseException("Invalid request parameters");
        }

        Database db = new Database();

        try{
            Connection conn = db.openConnection();
            //  Get requested Person from personID
            Person person = new PersonDao(conn).getPersonByID(req.getPersonID());

            if (person == null){
                //  No such person exists.
                throw new HttpRequestParseException("No such person found");
            }

            //  Ensure person belongs to user
            if( new AuthTokenDao(conn).getUsernameByAuthToken(req.getAuthToken())
                    .equals(person.descendant)){

                db.closeConnection(true);
                return new PersonIDResponse(person);

            } else{
                //  Person does not belong to user
                throw new HttpRequestParseException("Person does not belong to current User");
            }

        } catch (DataAccessException | HttpRequestParseException ex){
            db.closeConnection(false);

            logger.severe(ex.getMessage());

            throw ex;

        } finally {
            //  Roll everything back just in case
            db.closeConnection(false);
        }
    }
}
