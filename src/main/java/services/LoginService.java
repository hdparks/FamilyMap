package services;

import database_access.DataAccessException;
import database_access.Database;
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
    public LoginResponse serveResponse(LoginRequest req) throws DataAccessException {
        Database db = new Database();
        try{
            //  Spin up database connection
            Connection conn = db.openConnection();

            //  Query for User matching supplied credentials


        } catch (DataAccessException ex){
            //  Undo any changes
            db.closeConnection(false);

            logger.severe(ex.getMessage());

            throw ex;

        }
    }
}
