package services;

import database_access.*;
import domain.Event;
import domain.User;
import handlers.AuthUtilities;
import handlers.HttpExceptions.HttpAuthorizationException;
import handlers.HttpExceptions.HttpBadRequestException;
import handlers.HttpExceptions.HttpInternalServerError;
import requests.EventRequest;
import responses.EventResponse;

import javax.xml.crypto.Data;
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
    public EventResponse serveResponse(EventRequest req) throws HttpBadRequestException, HttpInternalServerError, HttpAuthorizationException {
        //  Parse request
        if (req.getAuthToken() == null){
            throw new HttpBadRequestException("Invalid parameters: missing data");
        }


        Database db = new Database();
        try {
            Connection conn = db.openConnection();

            //  Authenticate
            if (!AuthUtilities.authTokenIsValid(req.getAuthToken(),conn)){
                throw new HttpAuthorizationException("Authentication failed.");
            }

            //  Spin up a database connection
            String userName = new AuthTokenDao(conn).getUsernameByAuthToken(req.getAuthToken());
            User user = new UserDao(conn).getUserByName(userName);

            //  Parse user
            if (null == user) {
                throw new HttpBadRequestException("No user found");
            }

            Event[] events = new EventDao(conn).getEventsByDescendant(user.userName);

            db.closeConnection(true);
            return new EventResponse(events);

        }catch (DataAccessException ex){

            throw new HttpInternalServerError(ex.getMessage());

        } finally {
            db.hardClose();
        }
    }
}
