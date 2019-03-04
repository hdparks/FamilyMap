package services.request;

/**
 * Represents a request made to the /user/login endpoint
 */
public class LoginRequest implements Request{

    /**
     * Non-empty string
     */
    String userName;

    /**
     * Non-empty string
     */
    String password;

    /**
     * Creates a LoginRequest
     * @param userName the userName of the user logging in
     * @param password the password of the user logging in
     */
    LoginRequest(String userName, String password){
        this.userName = userName;
        this.password = password;
    }
}
