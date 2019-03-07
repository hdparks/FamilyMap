package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import database_access.DataAccessException;

import requests.RegisterRequest;
import responses.RegisterResponse;
import services.HttpRequestException;
import services.RegisterService;

import java.io.IOException;
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
        try{


            //  Expect a POST request
            if(exchange.getRequestMethod().toUpperCase().equals("POST")){

                RegisterRequest req = ExchangeUtilities.generateRequest(exchange,RegisterRequest.class);

                //  Parse request
                if( req.getUserName() == null ||
                        req.getPassword() == null ||
                        req.getEmail()    == null ||
                        req.getFirstName()== null ||
                        req.getLastName() == null ||
                        req.getGender()   == null)  {

                    throw new HttpRequestException("Invalid request to /register : missing data");
                }

                RegisterResponse res = registerService.serveResponse(req);

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
            ExchangeUtilities.handleInternalError(ex,exchange);
            logger.severe(ex.getMessage());
        }

        //  And after everything,
        exchange.close();
    }
}
