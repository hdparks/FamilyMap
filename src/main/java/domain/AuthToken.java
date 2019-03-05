package domain;

/**
 * Represents an authorization token
 */
public class AuthToken {
    /**
     * The generated authorization token for the associated user
     */
    public String authToken;

    /**
     * The username the token is assigned to
     */
    public String userName;


    /**
     * Randomly generates a new unique authToken string
     * @return authTokenString a unique string representing a new authToken
     */
    public static String generateAuthToken() {
        return null;
    }

    /**
     * Generates a new AuthToken for the given user
     * @param user the User object for which an authToken is being generated
     */
    public AuthToken(User user){
        this.userName = user.userName;
        this.authToken = AuthToken.generateAuthToken();
    }

    /**
     * Generates an authToken object with the given auth string.
     * This object can then be used by AuthTokenDao to access the associated userName
     * @param authToken the authorization token, passed in via IRequest object
     */
    public AuthToken(String authToken){
        this.authToken = authToken;
    }
}
