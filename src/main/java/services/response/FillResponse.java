package services.response;

/**
 * Represents a response to a request to the /fill endpoint
 */
public class FillResponse extends Response{

    /**
     * Creates a FillResponse object
     * @param message describes the success/failure of the operation
     * @param success the success/failure status of the operation
     */
    public FillResponse(String message, boolean success) {
        super(message, success);
    }

    /**
     * Creates a FillResponse object
     * @param success the success/failure status of the operation
     */
    public FillResponse(boolean success){
        super(success);
    }
}
