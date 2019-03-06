package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import requests.ClearRequest;
import responses.ClearResponse;
import services.ClearService;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClearHandler extends THandler {

    private static Logger logger = Logger.getLogger("ClearHandler");

    public ClearHandler() {
        super(ClearRequest.class, ClearResponse.class, logger, "POST",false);
        this.service = new ClearService();
    }
}
