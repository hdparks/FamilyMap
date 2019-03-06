package services;

import requests.EventRequest;
import responses.EventResponse;

/**
 * Returns ALL events for ALL family members of the current user.
 *
 * The current user is determined from the provided auth token
 */
public class EventService implements Service<EventRequest, EventResponse> {

    /**
     * Handles the requests made to the /event endpoint
     * @param req an EventRequest object
     * @return a valid EventResponse object if successful, a failing one if not.
     */
    @Override
    public EventResponse serveResponse(EventRequest req) {
        return null;
    }
}
