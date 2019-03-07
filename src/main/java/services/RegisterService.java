package services;

import database_access.*;
import domain.AuthToken;
import domain.Generator;
import domain.Person;
import domain.User;
import requests.RegisterRequest;
import responses.RegisterResponse;

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

            //  Create new User account
            User user = new User(
                    req.getUserName(),
                    req.getPassword(),
                    req.getEmail(),
                    req.getFirstName(),
                    req.getLastName(),
                    req.getGender());

            //  Add the User
            UserDao userDao = new UserDao(conn);
            userDao.add(user);

            //  Create accompanying Person data
            Person userPerson = new Person(
                    req.getUserName(),
                    req.getFirstName(),
                    req.getLastName(),
                    req.getGender(),
                    null,
                    null,
                    null
            );

            //  Add the Person data
            PersonDao personDao = new PersonDao(conn);
            personDao.add(userPerson);


            //  Generate 4 generations of data
            new Generator(conn).generateGenerations(userPerson, 4);

            //  Log user in
            AuthToken authToken = new AuthToken(user);
            AuthTokenDao authTokenDao = new AuthTokenDao(conn);
            authTokenDao.add(authToken);

            //  Close and save changes
            db.closeConnection(true);

            //  Return new authToken
            return new RegisterResponse(authToken.authToken,user.userName,userPerson.personID);

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
