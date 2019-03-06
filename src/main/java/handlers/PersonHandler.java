package handlers;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import database_access.AuthTokenDao;
import database_access.DataAccessException;
import database_access.Database;
import requests.PersonIDRequest;
import requests.PersonRequest;
import responses.PersonIDResponse;
import responses.PersonResponse;
import services.PersonIDService;
import services.PersonService;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import java.util.logging.Logger;

public class PersonHandler implements HttpHandler {

    private static Logger logger = Logger.getLogger("PersonHandler");

    PersonIDService personIDService;
    PersonService personService;

    public PersonHandler(){
        personIDService = new PersonIDService();
        personService = new PersonService();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try{
            //  Expect a GET request
            if( exchange.getRequestMethod().toUpperCase().equals("GET") ){

                //  Authentication
                if ( AuthenticationHandler.authTokenIsValid(AuthenticationHandler.getAuthToken(exchange))){

                    //  Figure out if it needs a PersonIDService or PersonService
                    String jsonRes;

                    if( exchange.getHttpContext().getPath().equals("/person")){
                        //  PersonService
                        String authString = exchange.getRequestHeaders().getFirst("Authentication");
                        PersonRequest req = new PersonRequest(authString);

                        PersonResponse res = personService.handleRequest(req);

                        //  Convert response to JSON
                        jsonRes = JSONHandler.generateResponseJSON(res);

                    } else {
                        //  PersonIDService
                        //  Parse path after "/person/"
                        String personID = exchange.getHttpContext().getPath().substring(8);
                        String authString = exchange.getRequestHeaders().getFirst("Authentication");
                        PersonIDRequest req = new PersonIDRequest(authString, personID);

                        PersonIDResponse res = personIDService.handleRequest(req);

                        //  Convert response to JSON
                        jsonRes = JSONHandler.generateResponseJSON(res);
                    }

                    //  Write to the response object
                    OutputStream responseBody = exchange.getResponseBody();
                    JSONHandler.writeString(jsonRes, responseBody);

                } else {
                    //  Expected valid authentication
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_UNAUTHORIZED,0);
                }
            } else {
                //  Expected a GET request
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST,0);
            }

        } catch (DataAccessException ex){

            //  Something went wrong on our end
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR,0);
            logger.severe(ex.getMessage());
        }
        exchange.close();
    }

}
