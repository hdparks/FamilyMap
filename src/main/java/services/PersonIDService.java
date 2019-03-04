package services;

import requests.PersonIDRequest;
import responses.PersonIDResponse;

/**
 * Returns the single Person object with the specified ID.
 */
public class PersonIDService implements Service<PersonIDRequest, PersonIDResponse> {

    /**
     * Returns a responses instance with the info of a person (or persons)
     * @param req a valid PersonIDRequest object
     * @return res a valid PersonIDResponse object if successful, a failing Response object if services fails
     */
    @Override
    public PersonIDResponse handleRequest(PersonIDRequest req) {
        return null;
    }
}
