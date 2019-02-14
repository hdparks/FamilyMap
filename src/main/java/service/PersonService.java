package service;

import service.request.PersonRequest;
import service.response.Response;

/**
 * Generates JSON data in response to a request to /person
 */
public class PersonService implements Service<PersonRequest> {

    /**
     * Returns a response instance with the info of a person (or persons)
     * @param req a valid PersonRequest object
     * @return res a valid PersonResponse object if successful, a failing Response object if service fails
     */
    @Override
    public Response handleRequest(PersonRequest req) {
        return null;
    }
}
