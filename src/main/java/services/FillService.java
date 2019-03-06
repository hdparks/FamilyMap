package services;

import database_access.DataAccessException;
import requests.FillRequest;
import responses.FillResponse;

/**
 * Populates the server's database with generated data for the specified username.
 *
 * The required 'username' parameter must be a registered user.
 * If there is any data in the database already associated with the given username, it is deleted.
 * The optional 'generations' parameter lets the caller specify the number of generations of ancestors to be generated,
 * and must be a non-negative integer, {default = 4}.
 */
public class FillService implements Service<FillRequest, FillResponse> {

    /**
     * Handle the incoming FillRequest
     * @param req a requests object representing a requests to the /fill endpoint
     * @return a valid FillResponse if successful, a generic error Response if not
     */
    @Override
    public FillResponse serveResponse(FillRequest req) throws DataAccessException {
        return null;
    }
}
