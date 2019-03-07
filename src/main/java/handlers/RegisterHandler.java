package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import database_access.DataAccessException;

import requests.RegisterRequest;
import responses.RegisterResponse;
import services.HttpRequestException;
import services.HttpRequestParseException;
import services.RegisterService;

import java.io.IOException;
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
                exchange.sendResponseHeaders(400,0);
                throw new HttpRequestException("Invalid request method");
            }


        } catch (HttpRequestException ex){
            //  Something went wrong with the request
            ExchangeUtilities.handleRequestError(ex,exchange);
            logger.severe(ex.getMessage());


        } catch (DataAccessException ex){
            //  Something went wrong server-side
            exchange.sendResponseHeaders(500,0);
            ExchangeUtilities.handleInternalError(ex,exchange);
            exchange.getResponseBody().close();
            logger.info(ex.getMessage());


        } catch (HttpRequestParseException ex) {
            //  The request was missing some data
            exchange.sendResponseHeaders(400, 0);

            ExchangeUtilities.handleRequestError(ex, exchange);
            exchange.getResponseBody().close();
            logger.info(ex.getMessage());

        } catch (Exception ex){
            ex.printStackTrace();
            logger.info(ex.getMessage());
            throw ex;
        } finally {
            //  And after everything,
            exchange.close();
            logger.info("End of RegisterHandler");
        }

    }
}
