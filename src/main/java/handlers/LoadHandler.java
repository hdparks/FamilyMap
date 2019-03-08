package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import database_access.DataAccessException;
import handlers.HttpExceptions.HttpAuthorizationException;
import handlers.HttpExceptions.HttpBadRequestException;
import handlers.HttpExceptions.HttpInternalServerError;
import requests.LoadRequest;
import responses.LoadResponse;
import services.LoadService;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.logging.Logger;

public class LoadHandler implements HttpHandler {

    private static Logger logger = Logger.getLogger("LoadHandler");

    LoadService loadService;

    public LoadHandler(){
        loadService = new LoadService();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            //  Expect POST request
            if (!exchange.getRequestMethod().toUpperCase().equals("POST")) {
                throw new HttpBadRequestException("Invalid request method");
            }

            LoadRequest req = ExchangeUtilities.generateRequest(exchange, LoadRequest.class);

            LoadResponse res = loadService.serveResponse(req);

            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            ExchangeUtilities.writeResponseToHttpExchange(res, exchange);


        } catch (HttpBadRequestException ex) {

            exchange.sendResponseHeaders(400, 0);
            ExchangeUtilities.sendErrorBody(ex, exchange);

        } catch (HttpInternalServerError ex) {

            exchange.sendResponseHeaders(500, 0);
            ExchangeUtilities.sendErrorBody(ex, exchange);

        }  catch (Exception ex){
            ex.printStackTrace();
            exchange.sendResponseHeaders(500,0);
            ExchangeUtilities.sendErrorBody(ex,exchange);

        } finally{
            exchange.close();

        }
    }
}
