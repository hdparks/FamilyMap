package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import database_access.DataAccessException;
import requests.LoadRequest;
import responses.LoadResponse;
import services.HttpRequestException;
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
            if (exchange.getRequestMethod().toUpperCase().equals("POST")){

                LoadRequest req = ExchangeUtilities.generateRequest(exchange,LoadRequest.class);

                //  Service parses the request, so we go ahead
                LoadResponse res = loadService.serveResponse(req);

                //  Send successful response
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,0);
                ExchangeUtilities.writeResponseToHttpExchange(res,exchange);

            } else {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST,0);
                throw new HttpRequestException("Invalid request method");
            }

        } catch (HttpRequestException ex) {
            ExchangeUtilities.handleRequestError(ex,exchange);
            logger.severe(ex.getMessage());

        } catch (DataAccessException ex) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR,0);
            ExchangeUtilities.handleInternalError(ex,exchange);
            logger.severe(ex.getMessage());
        }


    }
}
