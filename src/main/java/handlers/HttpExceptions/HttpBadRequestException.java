package handlers.HttpExceptions;

public class HttpBadRequestException extends Exception{
    public HttpBadRequestException(String m){
        super(m);
    }
}