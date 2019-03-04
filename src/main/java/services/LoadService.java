package services;

import requests.LoadRequest;
import responses.LoadResponse;
import responses.Response;

/**
 * Clears all data from database, just like clear, then loads the posted data.
 */
public class LoadService implements Service<LoadRequest, LoadResponse>{

    /**
     * Clears the database, loads in data from req
     * @param req LoadRequest object
     * @return res successful Response success, failing Response object on failure
     */
    @Override
    public LoadResponse handleRequest(LoadRequest req) {
        return null;
    }
}
