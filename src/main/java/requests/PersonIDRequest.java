package requests;

/**
 * Represents a requests made to the /person/[personID] endpoint
 */
public class PersonIDRequest implements IRequest {
    /**
     * The authToken of the current user
     */
    private String authToken;

    /**
     * The ID of the person in question
     */
    private String personID;

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

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
