package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import database_access.DataAccessException;
import handlers.HttpExceptions.HttpBadRequestException;
import handlers.HttpExceptions.HttpInternalServerError;
import requests.EventIDRequest;
import requests.EventRequest;
import responses.Response;
import services.EventIDService;
import services.EventService;

import java.io.IOException;
import java.util.logging.Logger;

public class EventHandler implements HttpHandler {

    private static Logger logger = Logger.getLogger("EventHandler");

    EventService eventService;
    EventIDService eventIDService;

    public EventHandler() {
        eventIDService = new EventIDService();
        eventService = new EventService();
    }


    @Override
    public void handle(HttpExchange exchange) throws IOException {
        logger.info("Handling request");
        try {
            //  Expect a GET request
            if (!exchange.getRequestMethod().toUpperCase().equals("GET")) {
                throw new HttpBadRequestException("Invalid request method");
            }

            Response res;
            String authString = AuthUtilities.getAuthToken(exchange);
            String uri = exchange.getRequestURI().toString();


            if (uri.equals("/event") || uri.equals("/event/")) {

                //  EventService

                EventRequest req = new EventRequest(authString);
                req.setAuthToken(authString);
                res = eventService.serveResponse(req);


            } else {

                //  EventIDService

                String eventID = uri.substring("/event/".length());
                EventIDRequest req = new EventIDRequest(authString, eventID);
                res = eventIDService.serveResponse(req);


            }

            //  Successful operation, write to the response object
            exchange.sendResponseHeaders(200, 0);
            ExchangeUtilities.writeResponseToHttpExchange(res, exchange);


        } catch (HttpBadRequestException ex){
            exchange.sendResponseHeaders(400,0);
            ExchangeUtilities.sendErrorBody(ex,exchange);

        } catch (HttpInternalServerError ex){
            exchange.sendResponseHeaders(500,0);
            ExchangeUtilities.sendErrorBody(ex,exchange);

        }  catch (Exception ex){
            ex.printStackTrace();
            exchange.sendResponseHeaders(500,0);
            ExchangeUtilities.sendErrorBody(ex,exchange);

        } finally {
            exchange.close();

        }
    }
}
