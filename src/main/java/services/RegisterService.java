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
import java.util.UUID;
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
    public RegisterResponse serveResponse(RegisterRequest req) throws DataAccessException, HttpRequestParseException {
        logger.info("Servicing RegisterRequest");
        //  Parse request
        if( req.getUserName() == null ||
                req.getPassword() == null ||
                req.getEmail()    == null ||
                req.getFirstName()== null ||
                req.getLastName() == null ||
                req.getGender()   == null)  {
            logger.severe("Parse Error in RegisterResponse");
            throw new HttpRequestParseException("Invalid request to /register : missing data");
        }


        Database db = new Database();
        try{
            //  Spin up Database connection
            Connection conn = db.openConnection();


            String personID = UUID.randomUUID().toString();
            //  Create new User account
            User user = new User(
                    req.getUserName(),
                    req.getPassword(),
                    req.getEmail(),
                    req.getFirstName(),
                    req.getLastName(),
                    req.getGender(),
                    personID
                    );
            //  Create accompanying Person data

            Person userPerson = new Person(
                    personID,
                    req.getUserName(),
                    req.getFirstName(),
                    req.getLastName(),
                    req.getGender(),
                    null,
                    null,
                    null
            );
            logger.info("User and Person objects created");

            //  Add the User
            UserDao userDao = new UserDao(conn);
            userDao.add(user);

            logger.info("User data added to database.");
            //  Generate 4 generations of data
            Generator generator = new Generator(conn);
            generator.generateGenerations(userPerson, 4);

            logger.info("Generations generated.");
            //  Log user in
            AuthToken authToken = new AuthToken(user);
            AuthTokenDao authTokenDao = new AuthTokenDao(conn);
            authTokenDao.add(authToken);

            logger.info("User Logged in.");
            //  Close and save changes
            db.closeConnection(true);

            logger.info("Creating RegisterResponse");
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
        } finally {
            //  Always close connection
            db.closeConnection(false);
        }
    }
}
