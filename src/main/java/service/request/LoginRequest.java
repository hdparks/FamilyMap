package service.request;

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
}
