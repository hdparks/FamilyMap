package handlers;

import requests.RegisterRequest;
import responses.RegisterResponse;
import services.RegisterService;

import java.util.logging.Logger;

public class RegisterHandler extends THandler {

    private static Logger logger = Logger.getLogger("RegisterHandler");

    public RegisterHandler(){
        super(RegisterRequest.class, RegisterResponse.class, logger);
        this.service = new RegisterService();
    }

}
