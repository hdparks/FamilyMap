package responses;

/**
 * represents a responses to a requests to the /login endpoint
 */
public class LoginResponse extends Response{
    /**
     * Non-empty String
     */
    public String authToken;

    /**
     * Non-empty String
     */
    public String userName;

    /**
     * Non-empty String
     */
    public String personID;

    /**
     * Creates a LoginResponse object with an error message
     * @param errorMessage a string describing the error
     */
    public LoginResponse(String errorMessage){
        super(errorMessage,false);
    }

    /**
     * Creates a successful LoginResponse object with the necessary info
     * @param authToken the authToken generated for the user
     * @param userName the userName passed in with the requests
     * @param personID the personID related to the user who logged in
     */
    public LoginResponse(String authToken, String userName, String personID){
        super(true);
        this.authToken = authToken;
        this.userName = userName;
        this.personID = personID;
    }
}
