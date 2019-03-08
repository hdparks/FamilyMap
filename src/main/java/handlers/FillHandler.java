package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import database_access.DataAccessException;

import handlers.HttpExceptions.HttpAuthorizationException;
import handlers.HttpExceptions.HttpBadRequestException;
import handlers.HttpExceptions.HttpInternalServerError;
import requests.FillRequest;
import responses.FillResponse;
import services.FillService;

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
            if( !exchange.getRequestMethod().toUpperCase().equals("POST") ) {
                throw new HttpBadRequestException("Invalid request method");
            }
            String uri = exchange.getRequestURI().toString();

            FillRequest req = new FillRequest(uri);

            FillResponse res = fillService.serveResponse(req);

            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,0);

            ExchangeUtilities.writeResponseToHttpExchange(res,exchange);

        } catch (HttpBadRequestException ex) {

            exchange.sendResponseHeaders(400, 0);
            ExchangeUtilities.sendErrorBody(ex, exchange);

        } catch (HttpInternalServerError ex){

            exchange.sendResponseHeaders(500,0);
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
