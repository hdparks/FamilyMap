package domain;

import java.util.Objects;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthToken authToken1 = (AuthToken) o;
        return authToken.equals(authToken1.authToken) &&
                userName.equals(authToken1.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authToken, userName);
    }
}
