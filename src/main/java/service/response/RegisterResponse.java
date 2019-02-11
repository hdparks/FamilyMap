package service.response;

public class RegisterResponse extends Response {
    public String authToken;
    public String userName;
    public String personID;

    /**
     * Create a RegisterResponse object with an error message if RegisterService fails
     * @param errorMessage a description of how the RegisterService failed
     */
    public RegisterResponse(String errorMessage){
        super(errorMessage,false);
    }

    /**
     * Create a successful RegisterResponse object with necessary information
     * @param authToken the generated authToken
     * @param userName the userName which was passed in
     * @param personID the personID related to the newly generated Person object
     */
    public RegisterResponse(String authToken, String userName, String personID){
        super(true);
        this.authToken = authToken;
        this.userName = userName;
        this.personID = personID;
    }
}
