package requests;

/**
 * Represents a requests made to the /user/login endpoint
 */
public class LoginRequest implements IRequest {

    /**
     * Non-empty string
     */
    private String userName;

    /**
     * Non-empty string
     */
    private String password;

    /**
     * Creates a LoginRequest
     * @param userName the userName of the user logging in
     * @param password the password of the user logging in
     */
    public LoginRequest(String userName, String password){
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
