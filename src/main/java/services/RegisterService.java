package services;

import requests.RegisterRequest;
import responses.RegisterResponse;

/**
 * Creates a new user account, generates 4 generations of ancestor data,
 * logs the user in, and returns an auth token
 */
public class RegisterService implements Service<RegisterRequest,RegisterResponse>{


    /**
     * Registers a new user based on the http post requests
     * Returns a responses indicating the success/failure of the operation
     *
     * @param req A valid RegisterRequest object
     * @return res a valid RegisterResponse instance if successful, a Response instance if services failed
     *
     */
    @Override
    public RegisterResponse handleRequest(RegisterRequest req) {
        return null;

    }




}
