package services;

import requests.LoginRequest;
import responses.LoginResponse;

/**
 * Logs in the user and returns an auth token
 */
public class LoginService implements Service<LoginRequest,LoginResponse>{

    /**
     * Calls functions to log user in, constructs and returns LoginResponse object
     *
     * @param req A valid LoginRequest object
     * @return res A LoginResponse object if successful, a failing Response if services fails
     */
    @Override
    public LoginResponse handleRequest(LoginRequest req) {
        return null;
    }
}
