package services;

import requests.PersonRequest;
import responses.PersonResponse;

/**
 * Returns ALL family members of the current user.
 *
 * The current user is determined from the provided auth token.
 */
public class PersonService implements Service<PersonRequest, PersonResponse> {

    /**
     * Returns a responses instance with the info of a person (or persons)
     * @param req a valid PersonRequest object
     * @return res a valid PersonResponse object if successful, a failing Response object if services fails
     */
    @Override
    public PersonResponse handleRequest(PersonRequest req) {
        return null;
    }
}
