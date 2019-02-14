package service;

import service.response.Response;

/**
 * Deletes ALL data, including user accounts, auth tokens, person, and event data.
 */
public class ClearService {

    /**
     * Calls clear on all database_access classes, clearing each table in the database
     *
     * @return res A ClearResponse object
     */
    Response clear(){
        return null;
    }
}
