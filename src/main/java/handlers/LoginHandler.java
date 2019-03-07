package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import database_access.DataAccessException;
import requests.LoginRequest;
import responses.LoginResponse;
import services.HttpRequestException;
import services.HttpRequestParseException;
import services.LoginService;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.logging.Logger;

public class LoginHandler implements HttpHandler {

    private static Logger logger = Logger.getLogger("LoginHandler");

    LoginService loginService;

    public LoginHandler(){
        loginService = new LoginService();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try{
           //   Expect POST request
           if (exchange.getRequestMethod().toUpperCase().equals("POST")){

               LoginRequest req = ExchangeUtilities.generateRequest(exchange, LoginRequest.class);

               //   Parse LoginRequest
               if(  req.getPassword() == null ||
                    req.getUserName() == null){

                   throw new HttpRequestException("Invalid request to /login : missing data");
               }

               LoginResponse res = loginService.serveResponse(req);

               exchange.sendResponseHeaders(200,0);
               ExchangeUtilities.writeResponseToHttpExchange(res,exchange);

           } else {
               //   Expected POST request
               exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST,0);
               throw new HttpRequestException("Invalid response method");
           }

        } catch (HttpRequestException e) {
            //  Handles any request errors
            ExchangeUtilities.handleRequestError(e,exchange);
            logger.severe(e.getMessage());

        } catch (DataAccessException e) {
            //  Handles any bubbling internal errors
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR,0);
            ExchangeUtilities.handleInternalError(e,exchange);
            logger.severe(e.getMessage());

        } catch (HttpRequestParseException ex) {
            //  Handles parse errors
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST,0);
            ExchangeUtilities.handleRequestError(ex,exchange);
            logger.severe(ex.getMessage());
        }

        //  And after everything,
        exchange.close();
    }
}
