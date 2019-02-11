package service.response;

/**
 * The generic Response class. Has a boolean field corresponding to success/failure
 * Also as optional message field, usually describing the error on failure
 */
public class Response {

    public String message;
    public boolean success;

    /**
     * Constructs a Response object with the specified message and success values
     * @param message a description of the Service event, (often the error message on failure)
     * @param success a boolean value describing the success of the Service events
     */
    public Response(String message, boolean success){
        this.message = message;
        this.success = success;
    }

    /**
     * Constructs a Response object with the given success state
     * @param success a boolean value describing the success of the Service
     */
    public Response(boolean success){
        this.success = success;
    }
}
