package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import database_access.DataAccessException;
import handlers.HttpExceptions.HttpAuthorizationException;
import handlers.HttpExceptions.HttpBadRequestException;
import handlers.HttpExceptions.HttpInternalServerError;
import requests.LoginRequest;
import responses.LoginResponse;
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
           if (!exchange.getRequestMethod().toUpperCase().equals("POST")){
               throw new HttpBadRequestException("Invalid response method");
           }

           LoginRequest req = ExchangeUtilities.generateRequest(exchange, LoginRequest.class);

           LoginResponse res = loginService.serveResponse(req);

           exchange.sendResponseHeaders(200,0);
           ExchangeUtilities.writeResponseToHttpExchange(res,exchange);


        } catch (HttpBadRequestException ex) {

            exchange.sendResponseHeaders(400, 0);
            ExchangeUtilities.sendErrorBody(ex, exchange);

        } catch (HttpInternalServerError ex){

            exchange.sendResponseHeaders(500,0);
            ExchangeUtilities.sendErrorBody(ex, exchange);

        } catch (HttpAuthorizationException ex){

            exchange.sendResponseHeaders(401,0);
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
