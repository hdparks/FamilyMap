package service;

import service.request.PersonIDRequest;
import service.response.Response;

/**
 * Generates JSON data in response to a request to /person/personID
 */
public class PersonIDService implements Service<PersonIDRequest> {

    /**
     * Returns a response instance with the info of a person (or persons)
     * @param req a valid PersonIDRequest object
     * @return res a valid PersonIDResponse object if successful, a failing Response object if service fails
     */
    @Override
    public Response handleRequest(PersonIDRequest req) {
        return null;
    }
}
