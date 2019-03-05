package handlers;

import requests.LoginRequest;
import responses.LoginResponse;
import services.LoginService;

import java.util.logging.Logger;

public class LoginHandler extends THandler{

    private static Logger logger = Logger.getLogger("LoginHandler");

    public LoginHandler() {
        super(LoginRequest.class,LoginResponse.class, logger);
        this.service = new LoginService();
    }
}
