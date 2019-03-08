package services;

import database_access.DataAccessException;
import database_access.Database;
import handlers.HttpExceptions.HttpInternalServerError;
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
    public ClearResponse serveResponse(ClearRequest req) throws HttpInternalServerError {
        Database db = new Database();
        try {

            db.clearTables();
            return new ClearResponse("Clear succeeded.", true);

        } catch (DataAccessException ex){

            throw new HttpInternalServerError(ex.getMessage());

        } finally {

            db.hardClose();

        }
    }
}
