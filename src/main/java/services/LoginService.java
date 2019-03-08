package services;

import database_access.AuthTokenDao;
import database_access.DataAccessException;
import database_access.Database;
import database_access.UserDao;
import domain.AuthToken;
import domain.User;
import handlers.HttpExceptions.HttpAuthorizationException;
import handlers.HttpExceptions.HttpBadRequestException;
import handlers.HttpExceptions.HttpInternalServerError;
import requests.LoginRequest;
import responses.LoginResponse;

import java.sql.Connection;
import java.util.logging.Logger;

/**
 * Logs in the user and returns a new auth token for the session
 */
public class LoginService implements Service<LoginRequest,LoginResponse>{

    private static Logger logger = Logger.getLogger("LoginService");


    @Override
    public LoginResponse serveResponse(LoginRequest req) throws HttpAuthorizationException, HttpInternalServerError, HttpBadRequestException {

        //  Parse login request
        if (req.getUserName() == null || req.getPassword() == null){
            throw new HttpBadRequestException("Invalid parameters");
        }

        Database db = new Database();

        try {

            //  Spin up database connection
            Connection conn = db.openConnection();

            //  Verify user exists
            UserDao userDao = new UserDao(conn);
            User user = userDao.getUserByNameAndPassword(req.getUserName(), req.getPassword());
            if (user == null) {
                throw new HttpAuthorizationException("Login failed, userName/password not recognized");
            }


            //  Generate AuthToken for this session
            AuthToken authToken = new AuthToken(req.getUserName());
            AuthTokenDao authTokenDao = new AuthTokenDao(conn);
            authTokenDao.add(authToken);

            return new LoginResponse(authToken.authToken, authToken.userName, user.personID);

        }catch (DataAccessException ex){

            throw new HttpInternalServerError(ex.getMessage());

        } finally {

            db.hardClose();

        }
    }
}
