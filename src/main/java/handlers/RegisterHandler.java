package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class RegisterHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        //  Expect a POST request
        if(exchange.getRequestMethod().toUpperCase().equals("POST")) {
            //  Try parsing
        }
    }
}
