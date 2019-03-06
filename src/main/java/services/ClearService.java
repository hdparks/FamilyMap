package services;

import requests.ClearRequest;
import responses.ClearResponse;

/**
 * Deletes ALL data, including user accounts, auth tokens, person, and event data.
 */
public class ClearService implements Service<ClearRequest, ClearResponse>{
    
    @Override
    public ClearResponse serveResponse(ClearRequest req) {
        return null;
    }
}
