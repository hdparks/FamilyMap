package domain;

import java.util.UUID;

/**
 * Represents an authorization token
 */
public class AuthToken {

    public static String generateToken(){
        return UUID.randomUUID().toString();
    }

    /**
     * The generated authorization token for the associated user
     */
    public String authToken;

    /**
     * The username the token is assigned to
     */
    public String userName;


    public AuthToken(String userName){
        this.authToken = generateToken();
        this.userName = userName;
    }

    public AuthToken(String authToken,String userName){
        this.authToken = authToken;
        this.userName = userName;
    }

    /**
     * Generates a new AuthToken for the given user
     * @param user the User object for which an authToken is being generated
     */
    public AuthToken(User user){
        this.userName = user.userName;
        this.authToken = AuthToken.generateToken();
    }

}
