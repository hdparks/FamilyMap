package handlers;

import requests.FillRequest;
import responses.FillResponse;
import services.FillService;

import java.util.logging.Logger;

public class FillHandler extends THandler{

    private static Logger logger = Logger.getLogger("FillHandler");


    public FillHandler() {
        super(FillRequest.class, FillResponse.class, logger,"POST",false );
        this.service = new FillService();
    }
}
