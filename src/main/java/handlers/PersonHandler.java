package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.net.HttpURLConnection;

import services.HttpRequestException;
import database_access.DataAccessException;

import requests.PersonIDRequest;
import requests.PersonRequest;
import responses.Response;

import services.PersonIDService;
import services.PersonService;

import java.io.IOException;

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
                if ( AuthUtilities.isValidAuthentication(exchange)){

                    //  Figure out if it needs a PersonIDService or PersonService
                    Response res;

                    if( exchange.getHttpContext().getPath().equals("/person")){
                        //  PersonService
                        String authString = exchange.getRequestHeaders().getFirst("Authentication");
                        PersonRequest req = new PersonRequest(authString);

                        res = personService.handleRequest(req);

                    } else {
                        //  PersonIDService
                        //  Parse path after "/person/"
                        String personID = exchange.getHttpContext().getPath().substring("/person/".length());
                        String authString = exchange.getRequestHeaders().getFirst("Authentication");
                        PersonIDRequest req = new PersonIDRequest(authString, personID);

                        res = personIDService.handleRequest(req);
                    }

                    //  Write to the response object
                    ExchangeUtilities.writeResponseToHttpExchange(res,exchange);

                } else {
                    //  Expected valid authentication
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_UNAUTHORIZED,0);

                    throw new HttpRequestException("Authentication failed");
                }
            } else {
                //  Expected a GET request
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST,0);

                throw new HttpRequestException("Invalid request method");
            }

        } catch (DataAccessException ex){
            //  Something went wrong server side
            ExchangeUtilities.handleInternalError(ex, exchange);

            logger.severe(ex.getMessage());

        } catch (HttpRequestException ex) {
            //  Something was wrong with the request
            ExchangeUtilities.handleRequestError(ex, exchange);

            logger.severe(ex.getMessage());
        }
        exchange.close();
    }
}
