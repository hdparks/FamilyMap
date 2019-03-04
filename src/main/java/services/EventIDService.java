package services;

import services.request.EventIDRequest;
import services.response.Response;

/**
 * Returns a single Event object with the specified ID
 *
 */
public class EventIDService implements Service<EventIDRequest> {
    /**
     * Handles a request made to /event/[eventID]
     * @param req the EventIDRequest object
     * @return a EventIDResponse object
     */
    @Override
    public Response handleRequest(EventIDRequest req) {
        return null;
    }
}
