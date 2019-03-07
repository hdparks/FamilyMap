package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import database_access.DataAccessException;

import requests.FillRequest;
import responses.FillResponse;
import services.FillService;

import services.HttpRequestException;
import services.HttpRequestParseException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.logging.Logger;

public class FillHandler implements HttpHandler {

    private static Logger logger = Logger.getLogger("FillHandler");

    private FillService fillService;

    public FillHandler() {
        fillService = new FillService();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        try{
            //  Expect a POST
            if( exchange.getRequestMethod().toUpperCase().equals("POST") ){

                FillRequest req = new FillRequest(exchange.getHttpContext().getPath());

                FillResponse res = fillService.serveResponse(req);

                //  Send "OK" header and fill the response body with JSON
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,0);
                ExchangeUtilities.writeResponseToHttpExchange(res,exchange);

            } else {
                //  Expected a POST request
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST,0);
                throw new HttpRequestException("Invalid request method");
            }

        } catch (DataAccessException ex){
            //  Something went wrong server side
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR,0);
            ExchangeUtilities.handleInternalError(ex, exchange);
            logger.severe(ex.getMessage());

        } catch (HttpRequestException ex) {
            //  Something was wrong with the request
            ExchangeUtilities.handleRequestError(ex, exchange);
            logger.severe(ex.getMessage());

        } catch (HttpRequestParseException ex) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST,0);
            ExchangeUtilities.handleRequestError(ex, exchange);
            logger.severe(ex.getMessage());
        }
        //  Close the exchange explicitly
        exchange.close();
    }
}
