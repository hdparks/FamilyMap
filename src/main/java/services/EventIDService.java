package services;

import requests.EventIDRequest;
import responses.EventIDResponse;
import responses.Response;

/**
 * Returns a single Event object with the specified ID
 *
 */
public class EventIDService implements Service<EventIDRequest, EventIDResponse> {
    /**
     * Handles a requests made to /event/[eventID]
     * @param req the EventIDRequest object
     * @return a EventIDResponse object
     */
    @Override
    public EventIDResponse handleRequest(EventIDRequest req) {
        return null;
    }
}
