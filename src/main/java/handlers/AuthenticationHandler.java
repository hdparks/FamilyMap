package handlers;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import database_access.AuthTokenDao;
import database_access.DataAccessException;
import database_access.Database;

public class AuthenticationHandler {

    public static String getAuthToken(HttpExchange exchange){
        Headers headers = exchange.getRequestHeaders();
        if (headers.containsKey("Authentication")){
            return headers.getFirst("Authentication");
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
    public static boolean authTokenIsValid(String authToken) throws DataAccessException {
        //  If there is no authToken string, we fail to authenticate
        if(authToken == null){
            return false;
        }

        Database db = new Database();
        AuthTokenDao authTokenDao = new AuthTokenDao(db.openConnection());

        //  If a non-null userName is returned, the authToken was valid.
        return authTokenDao.getUsernameByAuthToken(authToken) != null;
    }
}
