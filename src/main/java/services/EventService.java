package services;

import database_access.*;
import domain.Event;
import domain.User;
import requests.EventRequest;
import responses.EventResponse;

import java.sql.Connection;
import java.util.logging.Logger;

/**
 * Returns ALL events for ALL family members of the current user.
 *
 * The current user is determined from the provided auth token
 */
public class EventService implements Service<EventRequest, EventResponse> {

    private static Logger logger = Logger.getLogger("EventService");

    /**
     * Handles the requests made to the /event endpoint
     * @param req an EventRequest object
     * @return a valid EventResponse object if successful, a failing one if not.
     */
    @Override
    public EventResponse serveResponse(EventRequest req) throws DataAccessException, HttpRequestParseException {
        //  Parse request
        if (req.getData() == null || req.getAuthToken() == null){
            throw new HttpRequestParseException("Invalid parameters: missing data");
        }

        Database db = new Database();
        try {
            //  Spin up a database connection
            Connection conn = db.openConnection();
            String userName = new AuthTokenDao(conn).getUsernameByAuthToken(req.getAuthToken());
            User user = new UserDao(conn).getUserByName(userName);

            //  Parse user
            if (user == null){
                throw new HttpRequestParseException("Authentication failed");
            }

            Event[] events = new EventDao(conn).getEventsByDescendant(user.userName);

            db.closeConnection(true);
            return new EventResponse(events);

        } finally {
            db.closeConnection(false);
        }
    }
}
