package services;

import database_access.DataAccessException;
import responses.Response;

public interface Service<T,U> {
    U handleRequest(T req) throws DataAccessException;
}
