package handlers;

import requests.PersonIDRequest;
import responses.PersonIDResponse;
import services.PersonIDService;

import java.util.logging.Logger;

public class PersonIDHandler extends THandler{

    public static Logger logger = Logger.getLogger("PersonIDHandler");


    public PersonIDHandler() {
        super(PersonIDRequest.class, PersonIDResponse.class, logger, "GET",true);
        this.service = new PersonIDService();
    }
}
