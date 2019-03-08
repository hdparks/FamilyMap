package services;

import database_access.*;
import domain.*;
import handlers.HttpExceptions.HttpBadRequestException;
import handlers.HttpExceptions.HttpInternalServerError;
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
    public RegisterResponse serveResponse(RegisterRequest req) throws HttpInternalServerError, HttpBadRequestException {
        logger.info("Servicing RegisterRequest");
        //  Parse request
        if( req.getUserName() == null ||
                req.getPassword() == null ||
                req.getEmail()    == null ||
                req.getFirstName()== null ||
                req.getLastName() == null ||
                req.getGender()   == null)  {
            logger.severe("Parse Error in RegisterResponse");
            throw new HttpBadRequestException("Invalid request to /register : missing data");
        }

        //  Parse gender
        if (!req.getGender().equals("m") && !req.getGender().equals("f")){
            throw new HttpBadRequestException("Gender must be m or f");
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

            //  Add the User
            UserDao userDao = new UserDao(conn);
            userDao.add(user);


           //  Generate 4 generations of data
            Generator generator = new Generator(conn);
            generator.generateGenerations(userPerson, 4);


           //  Log user in
            AuthToken authToken = new AuthToken(user);
            AuthTokenDao authTokenDao = new AuthTokenDao(conn);
            authTokenDao.add(authToken);


           //  Close and save changes
            db.closeConnection(true);


            //  Return new authToken
            return new RegisterResponse(authToken.authToken,user.userName,userPerson.personID);


        } catch (GenderException ex){

            throw new HttpBadRequestException(ex.getMessage());

        } catch (DataAccessException | FileNotFoundException ex){

            throw new HttpInternalServerError(ex.getMessage());
        } finally {
            db.hardClose();
        }
    }
}
