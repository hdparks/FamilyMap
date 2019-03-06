package handlers;

import requests.EventIDRequest;
import responses.EventIDResponse;
import services.EventIDService;

import java.util.logging.Logger;

public class EventIDHandler extends THandler{

    private static Logger logger = Logger.getLogger("EventIDHandler");

    public EventIDHandler() {
        super(EventIDRequest.class, EventIDResponse.class, logger, "GET",true);
        this.service = new EventIDService();
   }

}
