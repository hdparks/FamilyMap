package handlers;

import requests.EventIDRequest;
import responses.EventIDResponse;
import services.EventIDService;

import java.util.logging.Logger;

public class EventIDHandler extends THandler{

    private static Logger logger = Logger.getLogger("EventIDHandler");

    EventIDHandler() {
        super(EventIDRequest.class, EventIDResponse.class, logger);
        this.service = new EventIDService();
   }

}
