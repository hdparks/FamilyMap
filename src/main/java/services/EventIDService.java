package services;

import database_access.AuthTokenDao;
import database_access.DataAccessException;
import database_access.Database;
import database_access.EventDao;
import domain.Event;
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
    public EventIDResponse serveResponse(EventIDRequest req) throws HttpRequestParseException, DataAccessException {
        //  Parse request
        if (req.getAuthToken() == null || req.getEventID() == null){
            throw new HttpRequestParseException("Invalid parameters: missing data");
        }

        Database db = new Database();
        try {
            Connection conn = db.openConnection();
            Event event = new EventDao(conn).getEventByEventID(req.getEventID());

            if (event == null) throw new HttpRequestParseException("Invalid parameters: eventID not found");

            String userName = new AuthTokenDao(conn).getUsernameByAuthToken(req.getAuthToken());

            if (!event.descendant.equals(userName)) throw new HttpRequestParseException("Invalid parameters: Event does not belong to current user");

            db.closeConnection(true);

            return new EventIDResponse(event);
        } finally {
            db.closeConnection(false);
        }
    }
}
