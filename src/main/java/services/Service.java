package services;

import database_access.DataAccessException;
import responses.Response;

public interface Service<T,U> {
    U serveResponse(T req) throws DataAccessException;
}
