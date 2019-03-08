package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.net.HttpURLConnection;

import database_access.DataAccessException;

import handlers.HttpExceptions.HttpAuthorizationException;
import handlers.HttpExceptions.HttpBadRequestException;
import handlers.HttpExceptions.HttpInternalServerError;
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
        logger.info("Handling /person");
        try {


            //  Expect a GET request
            if (!exchange.getRequestMethod().toUpperCase().equals("GET")) {

                throw new HttpBadRequestException("Invalid request method");
            }

            //  Pass the authToken into the PersonRequest
            String authString = exchange.getRequestHeaders().getFirst("Authorization");
            String uri = exchange.getRequestURI().toString();
            Response res;

            if (uri.equals("/person") || uri.equals("/person/")) {
                //  PersonService


                PersonRequest req = new PersonRequest(authString);

                res = personService.serveResponse(req);


            } else {
                //  PersonIDService


                String personID = uri.substring("/person/".length());

                PersonIDRequest req = new PersonIDRequest(authString, personID);

                res = personIDService.serveResponse(req);
            }

            //  Success!
            //  Write to the response object
            exchange.sendResponseHeaders(200,0);
            ExchangeUtilities.writeResponseToHttpExchange(res, exchange);

        } catch (HttpBadRequestException ex) {

            exchange.sendResponseHeaders(400, 0);
            ExchangeUtilities.sendErrorBody(ex, exchange);

        } catch (HttpInternalServerError ex){

            exchange.sendResponseHeaders(500,0);
            ExchangeUtilities.sendErrorBody(ex, exchange);

        } catch (HttpAuthorizationException ex){

            exchange.sendResponseHeaders(401,0);
            ExchangeUtilities.sendErrorBody(ex, exchange);

        } finally{
            exchange.close();

        }
    }
}
