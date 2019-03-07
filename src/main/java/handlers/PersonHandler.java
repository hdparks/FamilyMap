package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.net.HttpURLConnection;

import services.HttpRequestException;
import database_access.DataAccessException;

import requests.PersonIDRequest;
import requests.PersonRequest;
import responses.Response;

import services.HttpRequestParseException;
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
        logger.info("Handling /person");
        try {


            //  Expect a GET request
            if (exchange.getRequestMethod().toUpperCase().equals("GET")) {


                //  Authentication
                if (AuthUtilities.isValidAuthentication(exchange)) {


                    String authString = exchange.getRequestHeaders().getFirst("Authorization");
                    String uri = exchange.getRequestURI().toString();
                    Response res;
                    if (uri.equals("/person") || uri.equals("/person/")) {
                        logger.info("Person Service");
                        //  PersonService


                        PersonRequest req = new PersonRequest(authString);

                        res = personService.serveResponse(req);


                    } else {
                        logger.info("PersonID Service");
                        //  PersonIDService


                        //  Parse path after "/person/"
                        String personID = uri.substring("/person/".length());
                        PersonIDRequest req = new PersonIDRequest(authString, personID);

                        res = personIDService.serveResponse(req);
                    }

                    //  Success!
                    //  Write to the response object
                    exchange.sendResponseHeaders(200,0);
                    ExchangeUtilities.writeResponseToHttpExchange(res, exchange);
                    logger.info("Successful operation");

                } else {
                    //  Expected valid authentication

                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_UNAUTHORIZED, 0);
                    throw new HttpRequestException("Authentication failed");
                }


            } else {
                //  Expected a GET request
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                throw new HttpRequestException("Invalid request method");
            }


        } catch (HttpRequestException ex) {
            //  Something was wrong with the request
            ExchangeUtilities.handleRequestError(ex, exchange);
            logger.severe(ex.getMessage());

        } catch (DataAccessException ex){
            //  Something went wrong server side
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR,0);
            ExchangeUtilities.handleInternalError(ex, exchange);
            logger.severe(ex.getMessage());

        } catch (HttpRequestParseException e) {
            //  Something was missing from the request
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST,0);
            ExchangeUtilities.handleRequestError(e,exchange);
            logger.severe(e.getMessage());

        } catch (Exception ex){
            ex.printStackTrace();
            logger.severe("Unchecked exception");
            throw ex;
        }

        //  And after everything,
        exchange.close();
        logger.info("/person handled");
    }
}
