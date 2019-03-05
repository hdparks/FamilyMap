package handlers;

import requests.PersonRequest;
import responses.PersonResponse;
import services.PersonService;

import java.util.logging.Logger;

public class PersonHandler extends THandler{

    private static Logger logger = Logger.getLogger("PersonHandler");


    PersonHandler() {
        super(PersonRequest.class, PersonResponse.class, logger);
        this.service = new PersonService();
    }
}
