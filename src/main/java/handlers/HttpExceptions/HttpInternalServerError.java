package handlers.HttpExceptions;

public class HttpInternalServerError extends Exception{
    public HttpInternalServerError(String m){
        super(m);
    }
}