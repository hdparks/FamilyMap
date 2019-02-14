package service.request;

/**
 * Represents a request made to the /user/register endpoint
 */
public class RegisterRequest implements Request{
    /**
     * Non-empty string
     */
    String userName;

    /**
     * Non-empty string
     */
    String password;

    /**
     * Non-empty string
     */
    String email;

    /**
     * Non-empty string
     */
    String firstName;

    /**
     * Non-empty string
     */
    String lastName;

    /**
     * "f" or "m"
     */
    String gender;
}
