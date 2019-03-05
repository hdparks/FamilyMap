package handlers;

import requests.EventRequest;
import responses.EventResponse;
import services.EventService;

import java.util.logging.Logger;

public class EventHandler extends THandler {

    private static Logger logger = Logger.getLogger("EventHandler");

    EventHandler() {
        super(EventRequest.class, EventResponse.class, logger,"GET",true);
        this.service = new EventService();
    }
}
