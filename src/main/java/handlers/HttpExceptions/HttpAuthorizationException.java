package handlers.HttpExceptions;

public class HttpAuthorizationException extends Exception{
    public HttpAuthorizationException(String m){
        super(m);
    }
}
