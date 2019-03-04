package services;

import responses.Response;

public interface Service<T,U> {
    U handleRequest(T req);
}
