package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import requests.PersonIDRequest;
import responses.PersonIDResponse;
import services.PersonIDService;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PersonIDHandler extends THandler{

    public static Logger logger = Logger.getLogger("PersonIDHandler");


    public PersonIDHandler() {
        super(PersonIDRequest.class, PersonIDResponse.class, logger, "GET",true);
        this.service = new PersonIDService();
    }
}
