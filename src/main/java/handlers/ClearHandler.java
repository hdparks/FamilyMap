package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import database_access.DataAccessException;
import handlers.HttpExceptions.HttpBadRequestException;
import handlers.HttpExceptions.HttpInternalServerError;
import requests.ClearRequest;
import responses.ClearResponse;
import services.ClearService;

import java.io.IOException;
import java.util.logging.Logger;

public class ClearHandler implements HttpHandler {

    private static Logger logger = Logger.getLogger("ClearHandler");

    private ClearService clearService;

    public ClearHandler() {
        clearService = new ClearService();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            //  Expect a POST request
            if(!exchange.getRequestMethod().toUpperCase().equals("POST")) {
                throw new HttpBadRequestException("Invalid request method");
            }

            ClearRequest req = new ClearRequest();

            ClearResponse res = clearService.serveResponse(req);

            //  Job's finished!
            exchange.sendResponseHeaders(200,0);
            ExchangeUtilities.writeResponseToHttpExchange(res,exchange);

        } catch (HttpBadRequestException ex) {
            exchange.sendResponseHeaders(400,0);
            ExchangeUtilities.sendErrorBody(ex, exchange);

        } catch (HttpInternalServerError ex){

            exchange.sendResponseHeaders(500,0);
            ExchangeUtilities.sendErrorBody(ex,exchange);

        } finally {
            exchange.close();

        }
    }
}
