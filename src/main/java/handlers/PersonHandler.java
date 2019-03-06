package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import requests.PersonRequest;
import responses.PersonResponse;
import services.PersonService;

import java.io.IOException;
import java.util.logging.Logger;

public class PersonHandler implements HttpHandler {

    private static Logger logger = Logger.getLogger("PersonHandler");

    public PersonHandler() {

    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

    }
}
