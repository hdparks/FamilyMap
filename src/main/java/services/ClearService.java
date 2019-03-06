package services;

import database_access.DataAccessException;
import database_access.Database;
import requests.ClearRequest;
import responses.ClearResponse;

/**
 * Deletes ALL data, including user accounts, auth tokens, person, and event data.
 */
public class ClearService implements Service<ClearRequest, ClearResponse>{

    /**
     * Calls Database.clear(), which clears everything from every table
     * @param req
     * @return
     * @throws DataAccessException
     */
    @Override
    public ClearResponse serveResponse(ClearRequest req) throws DataAccessException {
        Database db = new Database();
        db.clearTables();
        return new ClearResponse(true);
    }
}
