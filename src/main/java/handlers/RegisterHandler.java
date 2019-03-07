package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import database_access.DataAccessException;

import requests.RegisterRequest;
import responses.RegisterResponse;
import responses.Response;
import services.HttpRequestException;
import services.HttpRequestParseException;
import services.RegisterService;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.util.logging.Logger;

public class RegisterHandler implements HttpHandler {

    private static Logger logger = Logger.getLogger("RegisterHandler");

    private RegisterService registerService;

    public RegisterHandler(){
        registerService = new RegisterService();
    }


    @Override
    public void handle(HttpExchange exchange) throws IOException {
        logger.info("Recieved request");
        try{
            //  Expect a POST request
            if(exchange.getRequestMethod().toUpperCase().equals("POST")){

                RegisterRequest req = ExchangeUtilities.generateRequest(exchange,RegisterRequest.class);

                logger.entering("RegisterService","handle");
                RegisterResponse res = registerService.serveResponse(req);
                logger.exiting("RegisterService","handle");
                logger.info("Successful RegisterService");
                exchange.sendResponseHeaders(200,0);
                ExchangeUtilities.writeResponseToHttpExchange(res,exchange);

            } else{
                //  Expected POST request
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST,0);
                throw new HttpRequestException("Invalid request method");
            }


        } catch (HttpRequestException ex){
            //  Something went wrong with the request
            ExchangeUtilities.handleRequestError(ex,exchange);
            logger.severe(ex.getMessage());


        } catch (DataAccessException ex){
            //  Something went wrong server-side
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR,0);
            ExchangeUtilities.handleInternalError(ex,exchange);
            exchange.getResponseBody().close();
            logger.info("DataAccess"+ex.getMessage());


        } catch (HttpRequestParseException ex) {
            //  The request was missing some data
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);

            ExchangeUtilities.handleRequestError(ex, exchange);
            exchange.getResponseBody().close();
            logger.info("HERE "+ex.getMessage());

        } catch (Exception ex){
            ex.printStackTrace();
            logger.info(ex.getMessage());
            throw ex;
        } finally {
            //  And after everything,
            logger.info("End of RegisterHandler");
        }

    }
}
