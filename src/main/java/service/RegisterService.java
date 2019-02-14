package service;

import service.request.RegisterRequest;
import service.request.Request;
import service.response.RegisterResponse;
import service.response.Response;

/**
 * Creates a new user account, generates 4 generations of ancestor data,
 * logs the user in, and returns an auth token
 */
public class RegisterService implements Service<RegisterRequest>{


    /**
     * Registers a new user based on the http post request
     * Returns a response indicating the success/failure of the operation
     *
     * @param req A valid RegisterRequest object
     * @return res a valid RegisterResponse instance if successful, a Response instance if service failed
     *
     */
    @Override
    public Response handleRequest(RegisterRequest req) {
        return null;

    }




}
