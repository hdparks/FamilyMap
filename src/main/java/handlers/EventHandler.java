package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import database_access.DataAccessException;
import requests.EventIDRequest;
import requests.EventRequest;
import responses.Response;
import services.EventIDService;
import services.EventService;
import services.HttpRequestException;

import java.io.IOException;
import java.net.HttpURLConnection;
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
        try{
            //  Expect a GET request
            if(exchange.getRequestMethod().toUpperCase().equals("GET")){

                //  Authentication
                if ( AuthUtilities.isValidAuthentication(exchange)){
                    //  Set up blank response string
                    String authString = exchange.getRequestHeaders().getFirst("Authentication");
                    String jsonRes;
                    Response res;

                    //  Figure out if it needs an EventService or EventIDService
                    if( exchange.getHttpContext().getPath().equals("/event")){
                        //  EventService
                        EventRequest req = new EventRequest(authString);

                        res = eventService.serveResponse(req);

                    } else {
                        //  EventIDService
                        //  Parse path after "/event/"
                        String eventID = exchange.getHttpContext().getPath().substring("/event/".length());

                        EventIDRequest req = new EventIDRequest(authString, eventID);

                        res = eventIDService.serveResponse(req);

                    }

                    //  Successful operation, write to the response object
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,0);
                    ExchangeUtilities.writeResponseToHttpExchange(res,exchange);

                } else {
                    //  Expected valid authentication
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_UNAUTHORIZED,0);
                    throw new HttpRequestException("Authentication failed");
                }
            } else {
                //  Expected "GET" request
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST,0);
                throw new HttpRequestException("Invalid request method");
            }

        } catch (DataAccessException ex){
            //  Something wrong on our end
            ExchangeUtilities.handleInternalError(ex,exchange);

            logger.severe(ex.getMessage());

        } catch (HttpRequestException ex) {
            //  Something was wrong with the request
            ExchangeUtilities.handleRequestError(ex, exchange);

            logger.severe(ex.getMessage());
        }
    }
}
