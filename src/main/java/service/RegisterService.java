package service;

import service.request.RegisterRequest;
import service.response.RegisterResponse;

/**
 * Creates a new user account, generates 4 generations of ancestor data,
 * logs the user in, and returns an auth token
 */
public class RegisterService {

    /**
     * Registers a new user based on the http post request
     * Returns a response indicating the success/failure of the operation
     *
     * @param req A valid RegisterRequest object
     * @return res A RegisterResponse object
     */
    RegisterResponse register(RegisterRequest req) {
        return null;
    }
}
