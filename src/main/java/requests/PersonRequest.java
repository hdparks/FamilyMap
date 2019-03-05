package requests;

/**
 * Represents a requests to the /person endpoint
 */
public class PersonRequest implements IRequest {
    /**
     * The authToken of the current user
     */
    public String authToken;

    /**
     * Construct a PersonRequest
     * @param authToken the authToken of the current user
     */
    public PersonRequest(String authToken){
        this.authToken =  authToken;
    }
}
