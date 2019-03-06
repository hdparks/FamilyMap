package services;

import database_access.AuthTokenDao;
import database_access.DataAccessException;
import database_access.Database;
import database_access.PersonDao;
import domain.Person;
import requests.PersonRequest;
import responses.PersonResponse;

import java.sql.Connection;

/**
 * Returns ALL family members of the current user.
 *
 * The current user is determined from the provided auth token.
 */
public class PersonService implements Service<PersonRequest, PersonResponse> {

    Database db;

    public PersonService(){
        db = new Database();
    }


    /**
     * Returns a responses instance with the info of a person (or persons)
     * @param req a valid PersonRequest object
     * @return res a valid PersonResponse object if successful, a failing Response object if services fails
     */
    @Override
    public PersonResponse serveResponse(PersonRequest req) throws DataAccessException {
        //  Spin up database connection
        Connection conn = db.openConnection();

        try{
            //  Get authString from request
            String authString = req.authToken;

            //  Get username associated with the authString
            String username = new AuthTokenDao(conn).getUsernameByAuthToken(authString);

            Person[] personList = new PersonDao(conn).getPersonListByUser(username);

            //  Save changes
            db.closeConnection(true);

            //  Create PersonResponse
            return new PersonResponse(personList);

        } catch (DataAccessException ex) {
            //  Roll back changes
            db.closeConnection(false);

            throw ex;

        } finally {
            //  Default to rolling back
            db.closeConnection(false);
        }
    }
}
