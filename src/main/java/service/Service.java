package service;

import service.response.Response;

public interface Service<T> {
    Response handleRequest(T req);
}
