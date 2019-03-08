package handlers;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import database_access.AuthTokenDao;
import database_access.DataAccessException;
import database_access.Database;
import database_access.UserDao;

import java.sql.Connection;
import java.util.logging.Logger;


public class AuthUtilities {

    private static Logger logger = Logger.getLogger("AuthUtilities");

    public static String getAuthToken(HttpExchange exchange){
        Headers headers = exchange.getRequestHeaders();

        if (headers.containsKey("Authorization")){
            return headers.getFirst("Authorization");
        }

        return null;
    }

    /**
     * Quickly spin up a Database connection to check the validity of an
     * authentication token
     * @param authToken the token which was passed in as the Authorization header
     * @return whether the token is valid or not
     * @throws DataAccessException if operation fails
     */
    public static boolean authTokenIsValid(String authToken, Connection conn) throws DataAccessException {

        AuthTokenDao authTokenDao = new AuthTokenDao(conn);

        boolean valid = (authTokenDao.getUsernameByAuthToken(authToken) != null);

        if (!valid) logger.severe("Authentication failed");

        return valid;

    }

    public static boolean isValidUsername(String userName, Connection conn) throws DataAccessException {

        UserDao userDao = new UserDao(conn);

        boolean valid = userDao.getUserByName(userName) != null;

        return valid;
    }
}
