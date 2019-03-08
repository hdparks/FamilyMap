package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import handlers.HttpExceptions.HttpBadRequestException;
import handlers.HttpExceptions.HttpInternalServerError;
import requests.RegisterRequest;
import responses.RegisterResponse;
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
        logger.info("handling /register");
        try{

            //  Expect a POST request

            if(exchange.getRequestMethod().toUpperCase().equals("POST")){

                RegisterRequest req = ExchangeUtilities.generateRequest(exchange,RegisterRequest.class);

                RegisterResponse res = registerService.serveResponse(req);

                exchange.sendResponseHeaders(200,0);
                ExchangeUtilities.writeResponseToHttpExchange(res,exchange);


            } else{
                //  Expected POST request
                throw new HttpBadRequestException("Invalid request method");
            }

        } catch (HttpBadRequestException ex){
            exchange.sendResponseHeaders(400,0);
            ExchangeUtilities.sendErrorBody(ex,exchange);

        } catch (HttpInternalServerError ex){
            exchange.sendResponseHeaders(500,0);
            ExchangeUtilities.sendErrorBody(ex,exchange);

        } catch (Exception ex){
            ex.printStackTrace();

        } finally {
            exchange.close();

        }

    }
}
