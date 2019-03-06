package services;

import database_access.AuthTokenDao;
import database_access.DataAccessException;
import database_access.Database;
import database_access.UserDao;
import domain.AuthToken;
import domain.Generator;
import domain.Person;
import domain.User;
import requests.RegisterRequest;
import responses.RegisterResponse;

import javax.xml.crypto.Data;
import java.io.FileNotFoundException;
import java.sql.Connection;
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
    public RegisterResponse serveResponse(RegisterRequest req) throws DataAccessException{
        Database db = new Database();
        try{
            //  Spin up Database connection
            Connection conn = db.openConnection();

            //  Validate RegisterRequest object for non-null fields
            if( req.getUserName() == null ||
                req.getPassword() == null ||
                req.getEmail()    == null ||
                req.getFirstName()== null ||
                req.getLastName() == null ||
                req.getGender()   == null)  {

                throw new DataAccessException("Invalid request to /register : missing data.");
            }

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

            //  Add the User
            UserDao userDao = new UserDao(conn);
            userDao.add(user);

            //  Generate 4 generations of data
            new Generator(conn).generateGenerations(user, 4);

            //  Log user in
            AuthToken authToken = new AuthToken(user);
            AuthTokenDao authTokenDao = new AuthTokenDao(conn);
            authTokenDao.add(authToken);

            //  Close and save changes
            db.closeConnection(true);

            //  Return new authToken
            RegisterResponse res = new RegisterResponse(authToken.authToken,user.userName,personID);

            return res;


        } catch (DataAccessException ex){
            //  Undo any changes
            db.closeConnection(false);

            logger.severe(ex.getMessage());

            throw ex;

        } catch (FileNotFoundException ex){
            //  Undo any changes
            db.closeConnection(false);
            logger.severe(ex.getMessage());

            throw new DataAccessException("Error generating ancestor data.");
        }
    }
}
