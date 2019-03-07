package services;

/**
 * This exception is thrown from within a service if a request object is faulty
 */
public class HttpRequestParseException extends Exception {

    public HttpRequestParseException(String m){
        super(m);
    }
}
