package services;

public class HttpRequestException extends Exception {
    /**
     * Generates the exception with the given message
     * @param m a message field which details what cause the exception
     */
    public HttpRequestException(String m){
        super(m);
    }
}
