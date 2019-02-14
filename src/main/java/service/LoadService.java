package service;

import service.request.LoadRequest;
import service.response.Response;

/**
 * Clears all data from database, just like clear, then loads the posted data.
 */
public class LoadService implements Service<LoadRequest>{

    /**
     * Clears the database, loads in data from req
     * @param req LoadRequest object
     * @return res successful Response success, failing Response object on failure
     */
    @Override
    public Response handleRequest(LoadRequest req) {
        return null;
    }
}
