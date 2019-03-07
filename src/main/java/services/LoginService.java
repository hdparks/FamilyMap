package services;

import database_access.AuthTokenDao;
import database_access.DataAccessException;
import database_access.Database;
import database_access.UserDao;
import domain.AuthToken;
import domain.User;
import requests.LoginRequest;
import responses.LoginResponse;

import java.sql.Connection;
import java.util.logging.Logger;

/**
 * Logs in the user and returns a new auth token for the session
 */
public class LoginService implements Service<LoginRequest,LoginResponse>{

    private static Logger logger = Logger.getLogger("LoginService");

    /**
     * Calls functions to log user in, constructs and returns LoginResponse object
     *
     * @param req A valid LoginRequest object
     * @return res A LoginResponse object if successful, a failing Response if services fails
     */
    @Override
    public LoginResponse serveResponse(LoginRequest req) throws DataAccessException, HttpRequestParseException {
        //  Parse login request
        if (req.getUserName() == null || req.getPassword() == null){
            throw new HttpRequestParseException("Invalid parameters");
        }

        Database db = new Database();
        try{
            //  Spin up database connection
            Connection conn = db.openConnection();

            //  Query for User matching supplied credentials
            User user = new UserDao(conn).getUserByNameAndPassword(req.getUserName(),req.getPassword());

            if (user == null){
                throw new HttpRequestParseException("Login failed, userName/password not recognized");
            }

            //  Generate AuthToken for this session
            AuthToken authToken = new AuthToken(req.getUserName());
            new AuthTokenDao(conn).add(authToken);

            return new LoginResponse(authToken.authToken,authToken.userName,user.personID);

        } catch (DataAccessException ex){
            //  Undo any changes
            db.closeConnection(false);

            logger.severe(ex.getMessage());

            throw ex;

        } finally {
            db.closeConnection(false);
        }
    }
}
