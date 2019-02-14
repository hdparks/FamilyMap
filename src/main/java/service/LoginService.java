package service;

import service.request.LoginRequest;
import service.response.LoginResponse;
import service.response.Response;

/**
 * Logs in the user and returns an auth token
 */
public class LoginService implements Service<LoginRequest>{

    /**
     * Calls functions to log user in, constructs and returns LoginResponse object
     *
     * @param req A valid LoginRequest object
     * @return res A LoginResponse object if successful, a failing Response if service fails
     */
    @Override
    public Response handleRequest(LoginRequest req) {
        return null;
    }
}
