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

/**
 * Returns the single Person object with the specified ID.
 */
public class PersonIDService implements Service<PersonIDRequest, PersonIDResponse> {

    /**
     * Returns a responses instance with the info of a person (or persons)
     * @param req a valid PersonIDRequest object
     * @return res a valid PersonIDResponse object if successful, a failing Response object if services fails
     */
    @Override
    public PersonIDResponse serveResponse(PersonIDRequest req) throws DataAccessException {
        Database db = new Database();

        try{
            Connection conn = db.openConnection();
            //  Get requested Person from personID
            Person person = new PersonDao(conn).getPersonByID(req.personID);

            //  Ensure person belongs to user
            if( new AuthTokenDao(conn).getUsernameByAuthToken(req.authToken)
                    .equals(person.descendant)){

                db.closeConnection(true);
                return new PersonIDResponse(person);

            } else{
                //  Person does not belong to user
                throw new DataAccessException("Person does not belong to current User");
            }
        } catch (DataAccessException ex){
            db.closeConnection(false);
            throw ex;
        } finally {
            //  Roll everything back just in case
            db.closeConnection(false);
        }
    }
}
