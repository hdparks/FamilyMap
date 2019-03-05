package handlers;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import database_access.AuthTokenDao;
import database_access.DataAccessException;
import database_access.Database;
import services.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

public class THandler<Req,Res> implements HttpHandler {

    private Logger logger;

    final Class<Req> reqClass;
    final Class<Res> resClass;

    Service<Req,Res> service = null;
    String requestMethod;
    boolean authRequired;

    THandler(Class<Req> reqClass, Class<Res> resClass, Logger logger, String method, boolean authRequired){
        this.reqClass = reqClass;
        this.resClass = resClass;
        this.logger = logger;
        this.requestMethod = method;
        this.authRequired = authRequired;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        try {
            //  Expect a POST requests
            if(exchange.getRequestMethod().toUpperCase().equals(this.requestMethod)) {

                if(!authRequired || authTokenIsValid(getAuthToken(exchange))){

                    //  Parse request body into RegisterRequest object

                    Req req = JSONHandler.createRequest(exchange.getRequestBody(), this.reqClass);

                    //  Pass the request to the RegisterService to get the response data
                    Res res = this.service.handleRequest(req);

                    //  Convert response content to JSON
                    String json = JSONHandler.generateResponseJSON(res);

                    //  Write to the response object
                    OutputStream responseBody = exchange.getResponseBody();
                    JSONHandler.writeString(json, responseBody);

                } else {
                    //  Authentication failure
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_UNAUTHORIZED,0);
                }

            } else {
                //  We expected a different request method to this endpoint.
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST,0);
            }

        } catch (IOException | DataAccessException ex ){
            //  Something went wrong on the server side, so we return
            //  "Internal Service Error"
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR,0);
            logger.log(Level.SEVERE,ex.getMessage());
        }
        exchange.close();
    }

    private String getAuthToken(HttpExchange exchange){
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
    private boolean authTokenIsValid(String authToken) throws DataAccessException {
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
