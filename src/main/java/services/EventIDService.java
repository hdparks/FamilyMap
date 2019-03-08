package services;

import database_access.AuthTokenDao;
import database_access.DataAccessException;
import database_access.Database;
import database_access.EventDao;
import domain.Event;
import handlers.AuthUtilities;
import handlers.HttpExceptions.HttpAuthorizationException;
import handlers.HttpExceptions.HttpBadRequestException;
import handlers.HttpExceptions.HttpInternalServerError;
import requests.EventIDRequest;
import responses.EventIDResponse;

import java.sql.Connection;

/**
 * Returns a single Event object with the specified ID
 *
 */
public class EventIDService implements Service<EventIDRequest, EventIDResponse> {

    /**
     * Handles a request made to /event/[eventID]
     * @param req the EventIDRequest object
     * @return a EventIDResponse object
     */
    @Override
    public EventIDResponse serveResponse(EventIDRequest req) throws HttpAuthorizationException, HttpInternalServerError,HttpBadRequestException {
        //  Parse request
        if (req.getAuthToken() == null || req.getEventID() == null){
            throw new HttpBadRequestException("Invalid parameters: missing data");
        }

        Database db = new Database();
        try {
            //  Authenticate
            if (!AuthUtilities.authTokenIsValid(req.getAuthToken())){
                throw new HttpAuthorizationException("Authentication failed");
            }


            Connection conn = db.openConnection();
            Event event = new EventDao(conn).getEventByEventID(req.getEventID());

            if (event == null) throw new HttpBadRequestException("Invalid parameters: eventID not found");

            String userName = new AuthTokenDao(conn).getUsernameByAuthToken(req.getAuthToken());

            if (!event.descendant.equals(userName))
                throw new HttpBadRequestException("Invalid parameters: Event does not belong to current user");

            db.closeConnection(true);

            return new EventIDResponse(event);

        } catch (DataAccessException ex ){

            throw new HttpInternalServerError(ex.getMessage());

        } finally {
            db.hardClose();
        }
    }
}
