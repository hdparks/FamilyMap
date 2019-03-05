package requests;

/**
 * Represents a requests made to the /person/[personID] endpoint
 */
public class PersonIDRequest implements IRequest {
    /**
     * The authToken of the current user
     */
    public String authToken;

    /**
     * The ID of the person in question
     */
    public String personID;

    /**
     * Create a PersonID IRequest
     * @param authToken the authToken of the current User
     * @param personID the ID of the person in question
     */
    public PersonIDRequest(String authToken, String personID){
        this.authToken = authToken;
        this.personID = personID;
    }

}
