package responses;

/**
 * A response object to the /clear endpoint
 */
public class ClearResponse extends Response {

    public ClearResponse(String message, boolean success) {
        super(message,true);
    }
}
