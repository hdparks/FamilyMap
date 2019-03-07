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
import services.HttpRequestParseException;

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
        logger.info("Handling request");
        try{
            //  Expect a GET request
            if(exchange.getRequestMethod().toUpperCase().equals("GET")){

                //  Authentication
                if ( AuthUtilities.isValidAuthentication(exchange)){
                    //  Set up blank response string
                    String authString = AuthUtilities.getAuthToken(exchange);
                    Response res;



                    String uri = exchange.getRequestURI().toString();
                    logger.info("Request URI: "+uri);
                    //  Figure out if it needs an EventService or EventIDService
                    if(uri.equals("/event") || uri.equals("/event/")){
                        logger.info("Event Service");
                        //  EventService
                        EventRequest req = new EventRequest(authString);

                        req.setAuthToken(authString);

                        res = eventService.serveResponse(req);

                    } else {
                        logger.info("EventID Service");
                        //  EventIDService
                        //  Parse path after "/event/"
                        String eventID = uri.substring("/event/".length());

                        EventIDRequest req = new EventIDRequest(authString, eventID);

                        res = eventIDService.serveResponse(req);

                    }

                    //  Successful operation, write to the response object
                    exchange.sendResponseHeaders(200,0);
                    ExchangeUtilities.writeResponseToHttpExchange(res,exchange);
                    logger.info("Successful operation");
                } else {
                    //  Expected valid authentication
                    exchange.sendResponseHeaders(401,0);
                    throw new HttpRequestException("Authentication failed");
                }
            } else {
                //  Expected "GET" request
                exchange.sendResponseHeaders(400,0);
                throw new HttpRequestException("Invalid request method");
            }

        } catch (DataAccessException ex){
            //  Something wrong on our end
            exchange.sendResponseHeaders(500,0);
            ExchangeUtilities.handleInternalError(ex,exchange);
            logger.severe(ex.getMessage());

        } catch (HttpRequestException ex) {
            //  Something was wrong with the request
            ExchangeUtilities.handleRequestError(ex, exchange);
            logger.severe(ex.getMessage());

        } catch (HttpRequestParseException ex) {
            //  Something wrong in request data
            exchange.sendResponseHeaders(400,0);
            ExchangeUtilities.handleRequestError(ex,exchange);

            logger.severe(ex.getMessage());
        } catch (Exception ex){
            ex.printStackTrace();
            logger.severe("Unchecked exception");
            throw ex;
        }
        finally {
            exchange.close();
            logger.info("Event handled");
        }
    }
}
