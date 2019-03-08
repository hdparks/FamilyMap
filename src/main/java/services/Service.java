package services;

import database_access.DataAccessException;
import handlers.HttpExceptions.HttpAuthorizationException;
import handlers.HttpExceptions.HttpBadRequestException;
import handlers.HttpExceptions.HttpInternalServerError;
import responses.Response;

public interface Service<T,U> {
    U serveResponse(T req) throws HttpInternalServerError, HttpBadRequestException, HttpAuthorizationException;
}
