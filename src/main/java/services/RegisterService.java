package services;

import database_access.DataAccessException;
import database_access.Database;
import domain.Person;
import domain.User;
import requests.RegisterRequest;
import responses.RegisterResponse;

import java.util.logging.Logger;

/**
 * Creates a new user account, generates 4 generations of ancestor data,
 * logs the user in, and returns an auth token
 */
public class RegisterService implements Service<RegisterRequest,RegisterResponse>{

    private static Logger logger =  Logger.getLogger("RegisterService");

    /**
     * Registers a new user based on the http post requests
     * Returns a responses indicating the success/failure of the operation
     *
     * @param req A valid RegisterRequest object
     * @return res a valid RegisterResponse instance if successful, a Response instance if services failed
     *
     */
    @Override
    public RegisterResponse handleRequest(RegisterRequest req) {
        try{
            //  Validate RegisterRequest object for non-null fields
            if( req.getUserName() == null ||
                req.getPassword() == null ||
                req.getEmail()    == null ||
                req.getFirstName()== null ||
                req.getLastName() == null ||
                req.getGender()   == null)  {

                throw new DataAccessException("Invalid request to /register : missing data.");
            }

            //  Spin up Database connection
            Database db = new Database();

            //  Create new User account
            String personID = Person.getNewPersonID();

            User user = new User(
                    req.getUserName(),
                    req.getPassword(),
                    req.getEmail(),
                    req.getFirstName(),
                    req.getLastName(),
                    req.getGender(),
                    personID);


            //  Generate 4 generations of data



            //  Log user in

            //  Return new authToken


        } catch (DataAccessException ex){
            logger.severe(ex.getMessage());
            //  Create failing RegisterResponse
            RegisterResponse res = new RegisterResponse(ex.getMessage());
        } finally {
            //  Close Database connection

        }


    }




}
