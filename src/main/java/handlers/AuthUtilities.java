package handlers;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import database_access.AuthTokenDao;
import database_access.DataAccessException;
import database_access.Database;
import database_access.UserDao;

import java.util.logging.Logger;


public class AuthUtilities {

    private static Logger logger = Logger.getLogger("AuthUtilities");

    public static String getAuthToken(HttpExchange exchange){
        Headers headers = exchange.getRequestHeaders();
        if (headers.containsKey("Authorization")){
            return headers.getFirst("Authorization");
        }
        logger.severe("Authentication token not found");
        return null;
    }

    /**
     * Quickly spin up a Database connection to check the validity of an
     * authentication token
     * @param authToken the token which was passed in as the Authorization header
     * @return whether the token is valid or not
     * @throws DataAccessException if operation fails
     */
    public static boolean authTokenIsValid(String authToken) throws DataAccessException {
        //  If there is no authToken string, we fail to authenticate
        if(authToken == null){
            return false;
        }

        Database db = new Database();

        AuthTokenDao authTokenDao = new AuthTokenDao(db.openConnection());

        //  If a non-null userName is returned, the authToken was valid.
        boolean valid = authTokenDao.getUsernameByAuthToken(authToken) != null;

        db.closeConnection(true);
        if (!valid) logger.severe("Authentication failed");
        return valid;

    }

    /**
     * Chains authentication methods into one method, for quick and easy use
     * @param exchange an HttpExchange object
     * @return whether the authentication was valid or not
     * @throws DataAccessException if the operation fails
     */
    public static boolean isValidAuthentication(HttpExchange exchange) throws DataAccessException {

        return authTokenIsValid(getAuthToken(exchange));

    }

    public static boolean isValidUsername(String userName) throws DataAccessException {

        Database db = new Database();

        UserDao userDao = new UserDao(db.openConnection());

        //  GetUserByName returns null if no such user is found.
        boolean valid = userDao.getUserByName(userName) != null;

        db.closeConnection(false);

        return valid;
    }
}
