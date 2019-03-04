package services;

import services.request.PersonIDRequest;
import services.response.Response;

/**
 * Returns the single Person object with the specified ID.
 */
public class PersonIDService implements Service<PersonIDRequest> {

    /**
     * Returns a response instance with the info of a person (or persons)
     * @param req a valid PersonIDRequest object
     * @return res a valid PersonIDResponse object if successful, a failing Response object if services fails
     */
    @Override
    public Response handleRequest(PersonIDRequest req) {
        return null;
    }
}
