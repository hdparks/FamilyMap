package handlers;

import requests.LoadRequest;
import responses.LoadResponse;
import services.LoadService;

import java.util.logging.Logger;

public class LoadHandler extends THandler{

    private static Logger logger = Logger.getLogger("LoadHandler");

    LoadHandler() {
        super(LoadRequest.class,LoadResponse.class,logger,"POST",false);
        this.service = new LoadService();
    }
}
