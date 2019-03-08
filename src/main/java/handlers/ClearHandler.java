package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import database_access.DataAccessException;
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
            if(exchange.getRequestMethod().toUpperCase().equals("POST")){

                ClearRequest req = new ClearRequest();

                ClearResponse res = clearService.serveResponse(req);

                //  Job's finished!
                exchange.sendResponseHeaders(200,0);
                ExchangeUtilities.writeResponseToHttpExchange(res,exchange);


            } else {

                exchange.sendResponseHeaders(400,0);
                throw new HttpRequestException("Invalid request method");

            }
        } catch (HttpRequestException ex){
            ExchangeUtilities.handleRequestError(ex,exchange);
            logger.severe(ex.getMessage());


        } catch (DataAccessException ex){
            exchange.sendResponseHeaders(500,0);
            ExchangeUtilities.handleInternalError(ex,exchange);
            logger.severe(ex.getMessage());
        }
        exchange.close();
    }
}
