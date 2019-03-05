package requests;

/**
 * Represents a requests made to the /user/register endpoint
 */
public class RegisterRequest implements IRequest {
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

    RegisterRequest(String userName, String password, String email, String firstName, String lastName, String gender){
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }
}
