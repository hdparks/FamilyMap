package services;

import services.response.Response;

public interface Service<T> {
    Response handleRequest(T req);
}
