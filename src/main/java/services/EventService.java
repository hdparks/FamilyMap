package services;

import services.request.EventRequest;
import services.response.Response;

/**
 * Returns ALL events for ALL family members of the current user.
 *
 * The current user is determined from the provided auth token
 */
public class EventService implements Service<EventRequest> {

    /**
     * Handles the request made to the /event endpoint
     * @param req an EventRequest object
     * @return a valid EventResponse object if successful, a failing one if not.
     */
    @Override
    public Response handleRequest(EventRequest req) {
        return null;
    }
}
